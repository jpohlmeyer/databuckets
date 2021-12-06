package com.github.jpohlmeyer.databuckets.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.jpohlmeyer.databuckets.DataBucketsApplication;
import com.github.jpohlmeyer.databuckets.databinding.ActivityAddBucketBinding;
import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;

import java.util.ArrayList;

public class AddBucketActivity extends AppCompatActivity {

    private ActivityAddBucketBinding binding;
    private DataBucketsApplication dataBucketsApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBucketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataBucketsApplication = (DataBucketsApplication) getApplication();

        binding.addNewBucketButton.setOnClickListener(view -> addBucket());
    }

    private void addBucket() {
        BucketEntry template = binding.entryItemList.getBucketEntry();
        DataBucket dataBucket = new DataBucket(binding.entryItemList.getName(), template, new ArrayList<>());
        dataBucketsApplication.getDataBuckets().getBucketList().add(dataBucket);
        finish();
    }
}