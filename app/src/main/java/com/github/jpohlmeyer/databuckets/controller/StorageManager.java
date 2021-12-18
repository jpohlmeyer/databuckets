package com.github.jpohlmeyer.databuckets.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.dropbox.core.json.JsonReadException;
import com.dropbox.core.oauth.DbxCredential;
import com.github.jpohlmeyer.databuckets.model.DataBuckets;
import com.github.jpohlmeyer.databuckets.util.LocalDateTimeJsonAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class StorageManager {

    private final String storageTag = "databuckets";
    private final String filename = "savefile.json";

    private String logTag;
    private Gson gson;
    private Context context;

    public StorageManager(Context context, String logTag) {
        this.context = context;
        this.logTag = logTag;
        GsonBuilder gsonBuilder = new GsonBuilder();
        LocalDateTimeJsonAdapter localDateTimeJsonAdapter = new LocalDateTimeJsonAdapter();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, localDateTimeJsonAdapter);
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, localDateTimeJsonAdapter);
        gson = gsonBuilder.setPrettyPrinting().create();
    }

    public DataBuckets loadFromFile() {
        FileInputStream fis;
        try {
            fis = context.openFileInput(filename);
        } catch (FileNotFoundException e) {
            Log.e(logTag, "Read file failed. Init new.");
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
            Log.e(logTag, "Read file failed. Init new.");
            return null;
        }
    }

    public void saveToFile(DataBuckets dataBuckets) {
        String json = gson.toJson(dataBuckets);
        try (FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            Log.e(logTag, "SAVE FAILED");
        }
        DbxCredential localCredential = getLocalCredential();
        if (localCredential != null) {
            new Thread(() -> {
                DropboxApi dropboxApi = new DropboxApi(localCredential);
                dropboxApi.saveFile(json);
            }).start();
        }
    }

    public void storeCredentialLocally(DbxCredential dbxCredential) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(storageTag, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("credential", dbxCredential.toString()).apply();
    }

    public void removeCredentialLocally() {
        context.deleteSharedPreferences(storageTag);
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
