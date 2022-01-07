package com.github.jpohlmeyer.databuckets.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.files.FileMetadata;
import com.github.jpohlmeyer.databuckets.model.DataBuckets;
import com.github.jpohlmeyer.databuckets.util.LocalDateTimeJsonAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Reusable;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Reusable
public class StorageManager {

    private final String storageTag = "databuckets";
    private final String filename = "savefile.json";
    private final Gson gson;
    private final Context context;
    private final String logTag;
    private final DataBuckets dataBuckets;

    @Inject
    public StorageManager(@ApplicationContext Context context, @Named("logTag") String logTag, DataBuckets dataBuckets) {
        this.context = context;
        this.logTag = logTag;
        this.dataBuckets = dataBuckets;
        GsonBuilder gsonBuilder = new GsonBuilder();
        LocalDateTimeJsonAdapter localDateTimeJsonAdapter = new LocalDateTimeJsonAdapter();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, localDateTimeJsonAdapter);
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, localDateTimeJsonAdapter);
        gson = gsonBuilder.setPrettyPrinting().create();
    }

    public void loadSavedBuckets() {
        DbxCredential localCredential = getLocalCredential();
        String localRev = getLocalDropboxRev();
        if (localRev != null && localCredential != null) {
            new Thread(() -> {
                DropboxApi dropboxApi = new DropboxApi(localCredential);
                String content = dropboxApi.getFile();
                String rev = dropboxApi.getFileRev();
                storeDropboxRevLocally(rev);
                DataBuckets newBuckets = gson.fromJson(content, DataBuckets.class);
                if (newBuckets != null) {
                    dataBuckets.setBucketList(newBuckets.getBucketList());
                } else {
                    newBuckets = loadFromLocalStorage();
                    if (newBuckets != null) {
                        dataBuckets.setBucketList(newBuckets.getBucketList());
                    }
                }
                EventBus.getDefault().post(new DropboxActionDoneEvent());
            }).start();
        } else {
            DataBuckets newBuckets = loadFromLocalStorage();
            if (newBuckets != null) {
                dataBuckets.setBucketList(newBuckets.getBucketList());
            }
            EventBus.getDefault().post(new DropboxActionDoneEvent());
        }
    }

    private DataBuckets loadFromLocalStorage() {
        FileInputStream fis;
        try {
            fis = context.openFileInput(filename);
        } catch (FileNotFoundException e) {
            return null;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
            return gson.fromJson(stringBuilder.toString(), DataBuckets.class);
        } catch (IOException e) {
            return null;
        }
    }

    public void savePersistent() {
        String json = gson.toJson(dataBuckets);
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            Log.e(logTag, "SAVE FAILED");
        }
        saveToDropbox(json);
    }

    private void saveToDropbox(String json) {
        DbxCredential localCredential = getLocalCredential();
        if (localCredential != null) {
            new Thread(() -> {
                DropboxApi dropboxApi = new DropboxApi(localCredential);
                FileMetadata fileMetadata;
                String rev = getLocalDropboxRev();
                if (rev == null) {
                    fileMetadata = dropboxApi.saveFile(json);
                } else {
                    fileMetadata = dropboxApi.saveFile(json, rev);
                }
                if (fileMetadata == null) {
                    Log.e(logTag, "SAVE FAILED");
                    storeDropboxRevLocally(null);
                } else {
                    Log.i(logTag, fileMetadata.getRev());
                    storeDropboxRevLocally(fileMetadata.getRev());
                }
            }).start();
        } else {
            storeDropboxRevLocally(null);
        }
    }

    public void storeCredentialLocally(DbxCredential dbxCredential) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(storageTag, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("credential", dbxCredential.toString()).apply();
    }

    private void storeDropboxRevLocally(String rev) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(storageTag, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("dropbox_rev", rev).apply();
    }

    private String getLocalDropboxRev() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(storageTag, Context.MODE_PRIVATE);
        return sharedPreferences.getString("dropbox_rev", null);
    }

    public void removeCredentialLocally() {
        context.deleteSharedPreferences(storageTag);
        new Thread(() -> {
            DbxCredential localCredential = getLocalCredential();
            if (localCredential != null) {
                DropboxApi dropboxApi = new DropboxApi(localCredential);
                dropboxApi.revokeToken();
            }
        }).start();
    }

    public DbxCredential getLocalCredential() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(storageTag, Context.MODE_PRIVATE);
        String serializedCredential = sharedPreferences.getString("credential", null);
        if (serializedCredential == null) {
            return null;
        } else {
            try {
                return DbxCredential.Reader.readFully(serializedCredential);
            } catch (JsonReadException e) {
                return null;
            }
        }
    }
}
