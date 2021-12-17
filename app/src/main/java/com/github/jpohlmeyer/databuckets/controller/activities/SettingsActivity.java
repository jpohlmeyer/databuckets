package com.github.jpohlmeyer.databuckets.controller.activities;

import android.os.Bundle;
import android.view.View;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.android.Auth;
import com.dropbox.core.oauth.DbxCredential;
import com.github.jpohlmeyer.databuckets.controller.DropboxApi;
import com.github.jpohlmeyer.databuckets.databinding.ActivitySettingsBinding;

public class SettingsActivity extends DataBucketsBaseActivity {

    private ActivitySettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signInDropbox.setOnClickListener(view -> DropboxApi.startDropboxAuthorization(this));
        binding.syncDropbox.setOnClickListener(view -> this.getDataBucketsApplication().getStorageManager().saveToFile(this.getDataBucketsApplication().getDataBuckets()));
        binding.stopDropbox.setOnClickListener(view -> this.getDataBucketsApplication().getStorageManager().removeCredentialLocally());
    }

    @Override
    protected void onResume() {
        super.onResume();

        DbxCredential localCredential = this.getDataBucketsApplication().getStorageManager().getLocalCredential();
        DbxCredential credential;
        if (localCredential == null) {
            credential = Auth.getDbxCredential();
            if (credential != null) {
                this.getDataBucketsApplication().getStorageManager().storeCredentialLocally(credential);
            }
        } else {
            credential = localCredential;
        }

        if (credential == null) {
            binding.signInDropbox.setVisibility(View.VISIBLE);
            binding.stopDropbox.setVisibility(View.GONE);
            binding.syncDropbox.setVisibility(View.GONE);
        } else {
            binding.signInDropbox.setVisibility(View.GONE);
            binding.stopDropbox.setVisibility(View.VISIBLE);
            binding.syncDropbox.setVisibility(View.VISIBLE);
        }
    }

}