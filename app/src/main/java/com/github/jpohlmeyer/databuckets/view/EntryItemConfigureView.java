package com.github.jpohlmeyer.databuckets.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.github.jpohlmeyer.databuckets.R;

public class EntryItemConfigureView extends ConstraintLayout {

    private EditText description;
    private Spinner itemType;
    private ImageButton deleteButton;

    public EntryItemConfigureView(@NonNull Context context, OnClickListener deleteListener) {
        super(context);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMarginStart(this.getResources().getDimensionPixelSize(R.dimen.margin_60dp));
        params.setMarginEnd(this.getResources().getDimensionPixelSize(R.dimen.margin_60dp));
        params.setMargins(this.getResources().getDimensionPixelSize(R.dimen.margin_60dp), 0,
                this.getResources().getDimensionPixelSize(R.dimen.margin_60dp),
                this.getResources().getDimensionPixelSize(R.dimen.margin_10dp));
        this.setLayoutParams(params);
        this.setBackgroundResource(R.drawable.customshape);
        initView(context, deleteListener);
    }

    private void initView(Context context, OnClickListener deleteListener) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);

        description = new EditText(context);
        description.setId(View.generateViewId());
        description.setHint(R.string.description);
        description.setLayoutParams(params);
        this.addView(description);

        itemType = new Spinner(context);
        itemType.setId(View.generateViewId());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.item_types, android.R.layout.simple_spinner_item);
        itemType.setAdapter(adapter);
        itemType.setLayoutParams(params);
        this.addView(itemType);

        deleteButton = new ImageButton(context);
        deleteButton.setId(View.generateViewId());
        deleteButton.setImageResource(android.R.drawable.ic_menu_delete);
        deleteButton.setLayoutParams(params);
        deleteButton.setOnClickListener(deleteListener);
        this.addView(deleteButton);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);

        constraintSet.connect(description.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(description.getId(), ConstraintSet.END, deleteButton.getId(), ConstraintSet.START);
        constraintSet.connect(description.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(description.getId(), ConstraintSet.BOTTOM, itemType.getId(), ConstraintSet.TOP);

        constraintSet.connect(itemType.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(itemType.getId(), ConstraintSet.TOP, description.getId(), ConstraintSet.BOTTOM);
        constraintSet.connect(itemType.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

        constraintSet.connect(deleteButton.getId(), ConstraintSet.START, description.getId(), ConstraintSet.END);
        constraintSet.connect(deleteButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(deleteButton.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(deleteButton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

        int[] chainIds = {description.getId(), deleteButton.getId()};
        float[] weights = { 8, 2};
        constraintSet.createHorizontalChain(ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
                chainIds, weights, ConstraintSet.CHAIN_SPREAD);


        constraintSet.applyTo(this);
    }

    public EditText getDescription() {
        return description;
    }

    public void setDescription(EditText description) {
        this.description = description;
    }

    public Spinner getItemType() {
        return itemType;
    }

    public void setItemType(Spinner itemType) {
        this.itemType = itemType;
    }

    public ImageButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(ImageButton deleteButton) {
        this.deleteButton = deleteButton;
    }
}
