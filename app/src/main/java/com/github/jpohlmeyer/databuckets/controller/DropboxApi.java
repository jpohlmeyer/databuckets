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

    private DbxClientV2 dropboxClient;

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

    public FileMetadata saveFile(String content) {
        try (InputStream in = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
            return dropboxClient.files().uploadBuilder("/"+filename)
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(in);
        } catch (DbxException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFile() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            dropboxClient.files().download("/"+filename).download(byteArrayOutputStream);
            return byteArrayOutputStream.toString(String.valueOf(StandardCharsets.UTF_8));
        } catch (DbxException | IOException e) {
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
}
