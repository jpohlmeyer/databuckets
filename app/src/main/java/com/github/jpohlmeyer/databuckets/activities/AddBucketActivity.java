package com.github.jpohlmeyer.databuckets.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.jpohlmeyer.databuckets.DataBucketsApplication;
import com.github.jpohlmeyer.databuckets.databinding.ActivityAddBucketBinding;
import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;
import com.github.jpohlmeyer.databuckets.model.EntryItem;
import com.github.jpohlmeyer.databuckets.model.ItemType;
import com.github.jpohlmeyer.databuckets.view.EntryItemConfigureView;

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
        ArrayList<EntryItemConfigureView> entryItemConfigureView = binding.entryItemList.getEntryItems();
        EntryItem[] entryItemArray = new EntryItem[entryItemConfigureView.size()];
        for (int i = 0; i < entryItemArray.length; i++) {
            String description = entryItemConfigureView.get(i).getDescription().getText().toString();
            ItemType itemType = ItemType.toItemType(((TextView) entryItemConfigureView.get(i).getItemType().getSelectedView()).getText().toString());
            EntryItem entryItem = new EntryItem(itemType, description, null);
            entryItemArray[i] = entryItem;
        }
        BucketEntry template = new BucketEntry(entryItemArray);
        DataBucket dataBucket = new DataBucket(binding.entryItemList.getName().getText().toString(), template, new ArrayList<>());
        dataBucketsApplication.getDataBuckets().getBucketList().add(dataBucket);
        finish();
    }
}