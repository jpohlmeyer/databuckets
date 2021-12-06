package com.github.jpohlmeyer.databuckets.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.github.jpohlmeyer.databuckets.DataBucketsApplication;
import com.github.jpohlmeyer.databuckets.R;
import com.github.jpohlmeyer.databuckets.databinding.ActivityAddBucketEntryBinding;
import com.github.jpohlmeyer.databuckets.databinding.ActivityBucketBinding;
import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;
import com.github.jpohlmeyer.databuckets.model.EntryItem;
import com.github.jpohlmeyer.databuckets.model.ItemType;

import java.util.ArrayList;
import java.util.List;

public class AddBucketEntryActivity extends AppCompatActivity {

    private ActivityAddBucketEntryBinding binding;
    private DataBucketsApplication dataBucketsApplication;

    private DataBucket dataBucket;

    private List<EditText> entryItemsEditTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBucketEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataBucketsApplication = (DataBucketsApplication) getApplication();
        int index = (int) this.getIntent().getExtras().get("index");
        dataBucket = dataBucketsApplication.getDataBuckets().getBucketList().get(index);

        entryItemsEditTexts = new ArrayList<>();

        int i = 1;
        for (EntryItem item: dataBucket.getEntryTemplate().getEntryItems()) {
            EditText editText = new EditText(this);
            editText.setHint(item.getItemDescription());
            editText.setInputType(itemTypeToInputType(item.getItemType()));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginStart(this.getResources().getDimensionPixelSize(R.dimen.databuckets_list_button_margin));
            params.setMarginEnd(this.getResources().getDimensionPixelSize(R.dimen.databuckets_list_button_margin));
            editText.setLayoutParams(params);
            entryItemsEditTexts.add(editText);
            binding.addBucketEntryListView.addView(editText, i);
            i++;
        }

        binding.addNewBucketEntryButton.setOnClickListener(view -> addEntry());
    }

    private int itemTypeToInputType(ItemType itemType) {
        if (itemType == ItemType.NUMBER) {
            return InputType.TYPE_CLASS_NUMBER;
        }
        return InputType.TYPE_CLASS_TEXT;
    }

    private void addEntry() {
        BucketEntry newEntry = dataBucket.constructEntry();
        for (int i = 0; i < newEntry.getEntryItems().size(); i++) {
            newEntry.getEntryItems().get(i).setItemValue(entryItemsEditTexts.get(i).getText().toString());
        }
        dataBucket.addEntry(newEntry);
        finish();
    }
}