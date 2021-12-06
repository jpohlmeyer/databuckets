package com.github.jpohlmeyer.databuckets.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.github.jpohlmeyer.databuckets.DataBucketsApplication;
import com.github.jpohlmeyer.databuckets.databinding.ActivityBucketBinding;
import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;
import com.github.jpohlmeyer.databuckets.model.DataBuckets;
import com.github.jpohlmeyer.databuckets.model.EntryItem;

public class BucketActivity extends AppCompatActivity {

    private ActivityBucketBinding binding;
    private DataBucketsApplication dataBucketsApplication;

    private DataBucket dataBucket;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBucketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataBucketsApplication = (DataBucketsApplication) getApplication();
        index = (int) this.getIntent().getExtras().get("index");
        dataBucket = dataBucketsApplication.getDataBuckets().getBucketList().get(index);

        binding.title.setText(dataBucket.getName());

        binding.addNewEntryFab.setOnClickListener(view -> navToAddBucketEntryActivity());
    }

    @Override
    protected void onStart() {
        super.onStart();

        for (BucketEntry entry: dataBucket.getEntries()) {
            Log.i(dataBucketsApplication.getLogTag(), entry.toString());
        }
    }

    private void navToAddBucketEntryActivity() {
        Intent intent = new Intent(this, AddBucketEntryActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }
}