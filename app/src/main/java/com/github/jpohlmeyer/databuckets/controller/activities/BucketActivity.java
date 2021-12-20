package com.github.jpohlmeyer.databuckets.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.jpohlmeyer.databuckets.databinding.ActivityBucketBinding;
import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;
import com.github.jpohlmeyer.databuckets.model.DataBuckets;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BucketActivity extends AppCompatActivity {

    @Inject
    DataBuckets dataBuckets;
    @Inject
    @Named("logTag")
    String logTag;

    private ActivityBucketBinding binding;
    private DataBucket dataBucket;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBucketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        index = (int) this.getIntent().getExtras().get("index");
        dataBucket = dataBuckets.getBucketList().get(index);

        binding.title.setText(dataBucket.getName());

        binding.addNewEntryFab.setOnClickListener(view -> navToAddBucketEntryActivity());
        binding.showEntriesButton.setOnClickListener(view -> onClickShowEntries());
        binding.dataAnalysisButton.setOnClickListener(view -> onClickDataAnalysis());
        binding.saveToDropboxButton.setOnClickListener(view -> onClickExportCSV());
    }

    @Override
    protected void onStart() {
        super.onStart();

        for (BucketEntry entry: dataBucket.getEntries()) {
            Log.i(logTag, entry.toString());
        }
    }

    private void navToAddBucketEntryActivity() {
        Intent intent = new Intent(this, AddBucketEntryActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    private void onClickShowEntries() {
        Intent intent = new Intent(this, ShowBucketEntriesActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }

    private void onClickDataAnalysis() {
        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
    }

    private void onClickExportCSV() {
        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
    }
}