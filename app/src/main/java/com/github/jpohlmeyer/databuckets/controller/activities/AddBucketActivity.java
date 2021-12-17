package com.github.jpohlmeyer.databuckets.controller.activities;

import android.os.Bundle;

import com.dropbox.core.oauth.DbxCredential;
import com.github.jpohlmeyer.databuckets.controller.DropboxApi;
import com.github.jpohlmeyer.databuckets.databinding.ActivityAddBucketBinding;
import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;
import com.github.jpohlmeyer.databuckets.model.DataBuckets;

import java.util.ArrayList;

public class AddBucketActivity extends DataBucketsBaseActivity {

    private ActivityAddBucketBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBucketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addNewBucketButton.setOnClickListener(view -> addBucket());
    }

    private void addBucket() {
        BucketEntry template = binding.entryItemList.getBucketEntry();
        DataBucket dataBucket = new DataBucket(binding.entryItemList.getName(), template, new ArrayList<>());
        DataBuckets dataBuckets = this.getDataBucketsApplication().getDataBuckets();
        dataBuckets.getBucketList().add(dataBucket);
        this.getDataBucketsApplication().getStorageManager().saveToFile(dataBuckets);
        finish();
    }
}