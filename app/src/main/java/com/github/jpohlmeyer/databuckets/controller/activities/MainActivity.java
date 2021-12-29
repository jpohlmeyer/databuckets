package com.github.jpohlmeyer.databuckets.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.github.jpohlmeyer.databuckets.R;
import com.github.jpohlmeyer.databuckets.controller.MessageEvent;
import com.github.jpohlmeyer.databuckets.controller.StorageManager;
import com.github.jpohlmeyer.databuckets.databinding.ActivityMainBinding;
import com.github.jpohlmeyer.databuckets.model.DataBucket;
import com.github.jpohlmeyer.databuckets.model.DataBuckets;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<Button> bucketButtons;

    @Inject
    DataBuckets dataBuckets;
    @Inject
    StorageManager storageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addNewBucketFab.setOnClickListener(view -> navToAddBucketActivity());

        bucketButtons = new ArrayList<>();
        EventBus.getDefault().register(this);
        binding.spinningOrb.setVisibility(View.VISIBLE);
        storageManager.loadSavedBuckets();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        binding.spinningOrb.setVisibility(View.GONE);
        this.refreshBuckets();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void refreshBuckets() {
        for (Button button: bucketButtons) {
            binding.BucketlistView.removeView(button);
        }
        bucketButtons.clear();
        int i = 0;
        for (DataBucket bucket: dataBuckets.getBucketList()) {
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

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        storageManager.savePersistent();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            navToSettingsActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void navToSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
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