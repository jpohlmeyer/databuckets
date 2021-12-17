package com.github.jpohlmeyer.databuckets.controller.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.jpohlmeyer.databuckets.R;
import com.github.jpohlmeyer.databuckets.databinding.ActivityShowBucketEntriesBinding;
import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;
import com.github.jpohlmeyer.databuckets.model.EntryItem;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class ShowBucketEntriesActivity extends DataBucketsBaseActivity {

    private ActivityShowBucketEntriesBinding binding;

    private int index;
    private DataBucket dataBucket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowBucketEntriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        index = (int) this.getIntent().getExtras().get("index");
        dataBucket = this.getDataBucketsApplication().getDataBuckets().getBucketList().get(index);

        binding.title.setText(dataBucket.getName());
        initTable();
    }

    private void initTable() {
        TableRow headrow = new TableRow(this);

        TextView timestampTextView = new TextView(this);
        SpannableString timestampName = new SpannableString(getString(R.string.timestamp));
        timestampName.setSpan(new StyleSpan(Typeface.BOLD), 0, timestampName.length(), 0);
        timestampTextView.setText(timestampName);
        timestampTextView.setPadding(this.getResources().getDimensionPixelSize(R.dimen.margin_10dp),0, this.getResources().getDimensionPixelSize(R.dimen.margin_10dp), 0);
        timestampTextView.setBackgroundResource(R.drawable.customshape2);

        headrow.addView(timestampTextView);

        for (EntryItem entryItem: dataBucket.getEntryTemplate().getEntryItems()) {
            TextView entryTextView = new TextView(this);
            SpannableString name = new SpannableString(entryItem.getItemDescription());
            name.setSpan(new StyleSpan(Typeface.BOLD), 0, name.length(), 0);
            entryTextView.setText(name);
            entryTextView.setPadding(this.getResources().getDimensionPixelSize(R.dimen.margin_10dp),0, this.getResources().getDimensionPixelSize(R.dimen.margin_10dp), 0);
            entryTextView.setBackgroundResource(R.drawable.customshape2);

            headrow.addView(entryTextView);
        }
        binding.entryTable.addView(headrow);


        ArrayList<BucketEntry> entryClone = new ArrayList<>(dataBucket.getEntries());
        Collections.reverse(entryClone);
        for (BucketEntry bucketEntry: entryClone) {
            TableRow row = new TableRow(this);

            TextView entryTimestampTextView = new TextView(this);
            entryTimestampTextView.setText(bucketEntry.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            entryTimestampTextView.setPadding(this.getResources().getDimensionPixelSize(R.dimen.margin_10dp),0, this.getResources().getDimensionPixelSize(R.dimen.margin_10dp), 0);
            entryTimestampTextView.setBackgroundResource(R.drawable.customshape2);

            row.addView(entryTimestampTextView);

            for (EntryItem entryItem: bucketEntry.getEntryItems()) {
                TextView entryTextView = new TextView(this);
                entryTextView.setText(entryItem.getItemValue());
                entryTextView.setPadding(this.getResources().getDimensionPixelSize(R.dimen.margin_10dp),0, this.getResources().getDimensionPixelSize(R.dimen.margin_10dp), 0);
                entryTextView.setBackgroundResource(R.drawable.customshape2);

                //entryTextView.setLayoutParams(params);
                row.addView(entryTextView);
            }

            binding.entryTable.addView(row);
        }
    }
}