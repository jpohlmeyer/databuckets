package com.github.jpohlmeyer.databuckets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.jpohlmeyer.databuckets.R;
import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.EntryItem;
import com.github.jpohlmeyer.databuckets.model.ItemType;

import java.util.ArrayList;

public class EntryItemConfigureListView extends LinearLayout {

    private EditText name;

    private ImageButton addItemButton;

    private ArrayList<EntryItemConfigureItemView> entryItems;

    public EntryItemConfigureListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);

        name = new EditText(context);
        name.setHint(R.string.name);
        LinearLayout.LayoutParams nameParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nameParams.setMargins(this.getResources().getDimensionPixelSize(R.dimen.margin_60dp), this.getResources().getDimensionPixelSize(R.dimen.margin_10dp), this.getResources().getDimensionPixelSize(R.dimen.margin_60dp), this.getResources().getDimensionPixelSize(R.dimen.margin_10dp));
        name.setLayoutParams(nameParams);
        this.addView(name);

        entryItems = new ArrayList<>();

        addItemButton = new ImageButton(context);
        addItemButton.setImageResource(R.drawable.ic_baseline_add_48_white);
        LinearLayout.LayoutParams addItemParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addItemParams.setMargins(this.getResources().getDimensionPixelSize(R.dimen.margin_60dp), this.getResources().getDimensionPixelSize(R.dimen.margin_10dp), this.getResources().getDimensionPixelSize(R.dimen.margin_60dp), this.getResources().getDimensionPixelSize(R.dimen.margin_10dp));
        addItemButton.setLayoutParams(addItemParams);
        this.addView(addItemButton);
        addItemButton.setOnClickListener(view -> addItem());

        this.addItem();
    }

    private void deleteItem(View view) {
        if (this.getChildCount() > 3) {
            this.removeView((EntryItemConfigureItemView) view.getParent());
            this.entryItems.remove((EntryItemConfigureItemView) view.getParent());
        } else {
            Toast.makeText(this.getContext(), getResources().getString(R.string.last_item_remove), Toast.LENGTH_SHORT).show();
        }
    }

    private void addItem() {
        EntryItemConfigureItemView entryItem = new EntryItemConfigureItemView(this.getContext(), this::deleteItem);
        entryItems.add(entryItem);
        this.addView(entryItem, this.getChildCount() - 1);
    }

    public BucketEntry getBucketEntry() {
        EntryItem[] entryItemArray = new EntryItem[entryItems.size()];
        for (int i = 0; i < entryItemArray.length; i++) {
            String description = entryItems.get(i).getDescription().getText().toString();
            ItemType itemType = ItemType.toItemType(((TextView) entryItems.get(i).getItemType().getSelectedView()).getText().toString());
            EntryItem entryItem = new EntryItem(itemType, description, null);
            entryItemArray[i] = entryItem;
        }
        return new BucketEntry(entryItemArray);
    }

    public String getName() {
        return name.getText().toString();
    }
}
