package com.github.jpohlmeyer.databuckets.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.jpohlmeyer.databuckets.DataBucketsApplication;
import com.github.jpohlmeyer.databuckets.R;
import com.github.jpohlmeyer.databuckets.databinding.ActivityMainBinding;
import com.github.jpohlmeyer.databuckets.model.DataBucket;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends DataBucketsBaseActivity {

    private ActivityMainBinding binding;

    private List<Button> bucketButtons;

    private final ActivityResultLauncher<String[]> importLauncher = registerForActivityResult(
            new ActivityResultContracts.OpenDocument(),
            uri -> {
                if (uri != null) {
                    importDataFromUri(uri);
                }
            }
    );

    private final ActivityResultLauncher<String> exportLauncher = registerForActivityResult(
            new ActivityResultContracts.CreateDocument(),
            uri -> {
                if (uri != null) {
                    saveDataToUri(uri);
                }
            }
    );

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_import) {
            importLauncher.launch(new String[]{"application/json", "text/*"});
            return true;
        } else if (id == R.id.action_export) {
            exportData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void importDataFromUri(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
            Scanner s = new Scanner(inputStream, StandardCharsets.UTF_8.name()).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            getDataBucketsApplication().importFromJson(result);
            recreate(); // Refresh UI
            Toast.makeText(this, "Import successful", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Import failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void exportData() {
        exportLauncher.launch("databuckets_"+ LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)+".json");
    }

    private void saveDataToUri(Uri uri) {
        try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
            if (outputStream != null) {
                String json = getDataBucketsApplication().exportToJson();
                outputStream.write(json.getBytes(StandardCharsets.UTF_8));
                Toast.makeText(this, "Export successful", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Export failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
