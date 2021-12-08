package com.github.jpohlmeyer.databuckets.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.jpohlmeyer.databuckets.DataBucketsApplication;
import com.github.jpohlmeyer.databuckets.R;
import com.github.jpohlmeyer.databuckets.databinding.ActivityShowBucketEntriesBinding;
import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;
import com.github.jpohlmeyer.databuckets.model.EntryItem;

public class ShowBucketEntriesActivity extends AppCompatActivity {

    private ActivityShowBucketEntriesBinding binding;
    private DataBucketsApplication dataBucketsApplication;

    private int index;
    private DataBucket dataBucket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowBucketEntriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataBucketsApplication = (DataBucketsApplication) getApplication();
        index = (int) this.getIntent().getExtras().get("index");
        dataBucket = dataBucketsApplication.getDataBuckets().getBucketList().get(index);

        binding.title.setText(dataBucket.getName());
        initTable();
    }

    private void initTable() {
        TableRow headrow = new TableRow(this);
        for (EntryItem entryItem: dataBucket.getEntryTemplate().getEntryItems()) {
            TextView entryTextView = new TextView(this);
            SpannableString name = new SpannableString(entryItem.getItemDescription());
            name.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), 0);
            entryTextView.setText(name);
            entryTextView.setPadding(this.getResources().getDimensionPixelSize(R.dimen.margin_10dp),0, this.getResources().getDimensionPixelSize(R.dimen.margin_40dp), 0);
            entryTextView.setBackgroundResource(R.drawable.customshape2);

            TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.column = 1;
            entryTextView.setLayoutParams(params);
            headrow.addView(entryTextView);
        }
        binding.entryTable.addView(headrow);

        for (BucketEntry bucketEntry: dataBucket.getEntries()) {
            TableRow row = new TableRow(this);

            for (EntryItem entryItem: bucketEntry.getEntryItems()) {
                TextView entryTextView = new TextView(this);
                entryTextView.setText(entryItem.getItemValue());
                entryTextView.setPadding(this.getResources().getDimensionPixelSize(R.dimen.margin_10dp),0, this.getResources().getDimensionPixelSize(R.dimen.margin_40dp), 0);
                entryTextView.setBackgroundResource(R.drawable.customshape2);

                TableRow.LayoutParams params = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.column = 1;
                entryTextView.setLayoutParams(params);
                row.addView(entryTextView);
            }

            binding.entryTable.addView(row);
        }
    }
}