package com.github.jpohlmeyer.databuckets.controller.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.jpohlmeyer.databuckets.controller.StorageManager;
import com.github.jpohlmeyer.databuckets.databinding.ActivityAddBucketBinding;
import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;
import com.github.jpohlmeyer.databuckets.model.DataBuckets;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddBucketActivity extends AppCompatActivity {

    @Inject
    DataBuckets dataBuckets;
    @Inject
    StorageManager storageManager;

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
        dataBuckets.getBucketList().add(dataBucket);
        storageManager.saveToFile();
        finish();
    }
}