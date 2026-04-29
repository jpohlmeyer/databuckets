package com.github.jpohlmeyer.databuckets.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.jpohlmeyer.databuckets.R;

import com.github.jpohlmeyer.databuckets.DataBucketsApplication;
import com.github.jpohlmeyer.databuckets.databinding.ActivityBucketBinding;
import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;

public class BucketActivity extends DataBucketsBaseActivity {

    private ActivityBucketBinding binding;

    private DataBucket dataBucket;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBucketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        index = (int) this.getIntent().getExtras().get("index");
        dataBucket = this.getDataBucketsApplication().getDataBuckets().getBucketList().get(index);

        binding.title.setText(dataBucket.getName());

        binding.addNewEntryFab.setOnClickListener(view -> navToAddBucketEntryActivity());
        binding.showEntriesButton.setOnClickListener(view -> onClickShowEntries());
    }

    @Override
    protected void onStart() {
        super.onStart();

        for (BucketEntry entry: dataBucket.getEntries()) {
            Log.i(this.getDataBucketsApplication().getLogTag(), entry.toString());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bucket_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete_bucket) {
            confirmDeleteBucket();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmDeleteBucket() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.action_delete_bucket)
                .setMessage(R.string.delete_bucket_confirmation)
                .setPositiveButton(R.string.delete, (dialog, which) -> deleteBucket())
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void deleteBucket() {
        this.getDataBucketsApplication().getDataBuckets().getBucketList().remove(index);
        this.getDataBucketsApplication().saveToFile();
        Toast.makeText(this, "Bucket deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}