package com.github.jpohlmeyer.databuckets.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.jpohlmeyer.databuckets.DataBucketsApplication;
import com.github.jpohlmeyer.databuckets.R;
import com.github.jpohlmeyer.databuckets.databinding.ActivityMainBinding;
import com.github.jpohlmeyer.databuckets.model.DataBucket;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends DataBucketsBaseActivity {

    private ActivityMainBinding binding;

    private List<Button> bucketButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addNewBucketFab.setOnClickListener(view -> navToAddBucketActivity());

        bucketButtons = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (Button button: bucketButtons) {
            binding.BucketlistView.removeView(button);
        }
        bucketButtons.clear();
        int i = 0;
        for (DataBucket bucket: this.getDataBucketsApplication().getDataBuckets().getBucketList()) {
            Button button = new Button(this);
            button.setText(bucket.getName());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMarginStart(this.getResources().getDimensionPixelSize(R.dimen.margin_40dp));
            params.setMarginEnd(this.getResources().getDimensionPixelSize(R.dimen.margin_40dp));
            button.setLayoutParams(params);
            bucketButtons.add(button);
            final int finalI = i;
            button.setOnClickListener( view -> navToBucketActivity(finalI));
            binding.BucketlistView.addView(button);
            i++;
        }
    }

    private void navToAddBucketActivity() {
        Intent intent = new Intent(this, AddBucketActivity.class);
        startActivity(intent);
    }

    private void navToBucketActivity(int index) {
        Intent intent = new Intent(this, BucketActivity.class);
        intent.putExtra("index", index);
        startActivity(intent);
    }
}