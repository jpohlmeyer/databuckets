package com.github.jpohlmeyer.databuckets.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.github.jpohlmeyer.databuckets.R;

import java.util.ArrayList;

public class EntryItemConfigureListView extends LinearLayout {

    private EditText name;

    private ImageButton addItemButton;

    private ArrayList<EntryItemConfigureView> entryItems;

    public EntryItemConfigureListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);

        name = new EditText(context);
        name.setHint(R.string.name);
        LinearLayout.LayoutParams nameParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        nameParams.setMarginStart(this.getResources().getDimensionPixelSize(R.dimen.margin_60dp));
        nameParams.setMarginEnd(this.getResources().getDimensionPixelSize(R.dimen.margin_60dp));
        name.setLayoutParams(nameParams);
        this.addView(name);

        entryItems = new ArrayList<>();

        addItemButton = new ImageButton(context);
        addItemButton.setImageResource(android.R.drawable.ic_menu_add);
        LinearLayout.LayoutParams addItemParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        addItemParams.setMarginStart(this.getResources().getDimensionPixelSize(R.dimen.margin_60dp));
        addItemParams.setMarginEnd(this.getResources().getDimensionPixelSize(R.dimen.margin_60dp));
        addItemButton.setLayoutParams(addItemParams);
        this.addView(addItemButton);
        addItemButton.setOnClickListener(view -> addItem());

        this.addItem();
    }

    private void deleteItem(View view) {
        if (this.getChildCount() > 3) {
            this.removeView((EntryItemConfigureView) view.getParent());
            this.entryItems.remove((EntryItemConfigureView) view.getParent());
        } else {
            Toast.makeText(this.getContext(), getResources().getString(R.string.last_item_remove), Toast.LENGTH_SHORT).show();
        }
    }

    private void addItem() {
        EntryItemConfigureView entryItem = new EntryItemConfigureView(this.getContext(), this::deleteItem);
        entryItems.add(entryItem);
        this.addView(entryItem, this.getChildCount() - 1);
    }

    public ArrayList<EntryItemConfigureView> getEntryItems() {
        return entryItems;
    }

    public void setEntryItems(ArrayList<EntryItemConfigureView> entryItems) {
        this.entryItems = entryItems;
    }

    public EditText getName() {
        return name;
    }
}
