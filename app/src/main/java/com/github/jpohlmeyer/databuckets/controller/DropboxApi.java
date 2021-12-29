package com.github.jpohlmeyer.databuckets.controller;

import android.content.Context;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.android.Auth;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.users.FullAccount;
import com.github.jpohlmeyer.databuckets.BuildConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DropboxApi {

    private static final String APP_KEY = BuildConfig.DROPBOX_KEY;
    private static final String CLIENT_ID = "DataBuckets/1.0.0";
    private final String filename = "savefile.json";

    private final DbxClientV2 dropboxClient;

    public DropboxApi(DbxCredential dropboxCredential) {
        DbxRequestConfig requestConfig = new DbxRequestConfig(CLIENT_ID);
        this.dropboxClient = new DbxClientV2(requestConfig, dropboxCredential);
    }

    public FullAccount getAccountInfo() {
        try {
            return dropboxClient.users().getCurrentAccount();
        } catch (DbxException e) {
            return null;
        }
    }

    public FileMetadata saveFile(String content, String rev) {
        try (InputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
            FileMetadata fileMetadata;
            if (rev == null) {
                fileMetadata = dropboxClient.files().uploadBuilder("/"+filename)
                        .withMode(WriteMode.OVERWRITE)
                        .uploadAndFinish(in);
            } else {
                fileMetadata = dropboxClient.files().uploadBuilder("/"+filename)
                        .withMode(WriteMode.update(rev))
                        .withAutorename(true)
                        .uploadAndFinish(in);
            }
            return fileMetadata;
        } catch (DbxException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FileMetadata saveFile(String content) {
        return saveFile(content, null);
    }

    public String getFile() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dropboxClient.files().download("/"+filename).download(byteArrayOutputStream);
            FileMetadata fileMetadata = (FileMetadata) dropboxClient.files().getMetadata("/"+filename);
            return byteArrayOutputStream.toString(String.valueOf(StandardCharsets.UTF_8));
        } catch (DbxException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFileRev() {
        try {
            return ((FileMetadata) dropboxClient.files().getMetadata("/"+filename)).getRev();
        } catch (DbxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void startDropboxAuthorization(Context context) {
        DbxRequestConfig requestConfig = new DbxRequestConfig(CLIENT_ID);
        List<String> scopes = new ArrayList<>();
        scopes.add("account_info.read");
        scopes.add("files.content.read");
        scopes.add("files.content.write");
        Auth.startOAuth2PKCE(context, APP_KEY, requestConfig, scopes);
    }

    public void revokeToken() {
        try {
            dropboxClient.auth().tokenRevoke();
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }
}
