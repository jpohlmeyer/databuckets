package com.github.jpohlmeyer.databuckets;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;
import com.github.jpohlmeyer.databuckets.model.DataBuckets;
import com.github.jpohlmeyer.databuckets.model.EntryItem;
import com.github.jpohlmeyer.databuckets.model.ItemType;
import com.github.jpohlmeyer.databuckets.util.LocalDateTimeJsonAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataBucketsApplication extends Application {

    private DataBuckets dataBuckets;

    private final String logTag = "DataBuckets";
    private final String filename = "savefile.json";

    private Gson gson;

    public DataBucketsApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.dataBuckets = new DataBuckets();
        GsonBuilder gsonBuilder = new GsonBuilder();
        LocalDateTimeJsonAdapter localDateTimeJsonAdapter = new LocalDateTimeJsonAdapter();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, localDateTimeJsonAdapter);
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, localDateTimeJsonAdapter);
        gson = gsonBuilder.setPrettyPrinting().create();

        loadFromFile();
    }

    private void loadFromFile() {
        FileInputStream fis;
        try {
            fis = this.openFileInput(filename);
        } catch (FileNotFoundException e) {
            Log.e(logTag, "Read file failed. Init new.");
            return;
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
            dataBuckets = gson.fromJson(stringBuilder.toString(), DataBuckets.class);
        } catch (IOException e) {
            Log.e(logTag, "Read file failed. Init new.");
        }
    }

    public void saveToFile() {
        Log.d(logTag, "SAVE");
        String json = gson.toJson(dataBuckets);
        try (FileOutputStream fos = this.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(json.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            Log.e(logTag, "SAVE FAILED");
            //e.printStackTrace();
        }
    }

    private void constructTestData() {
        BucketEntry bloodpressureTemplate = new BucketEntry(
                new EntryItem(ItemType.NUMBER, "SYS", null),
                new EntryItem(ItemType.NUMBER, "DIA", null),
                new EntryItem(ItemType.NUMBER, "PUL", null)
        );
        BucketEntry bloodpressureEntry1 = bloodpressureTemplate.copy();
        for (EntryItem entryItem: bloodpressureEntry1.getEntryItems()) {
            entryItem.setItemValue("100");
        }
        Log.d(logTag, bloodpressureEntry1.toString());
        BucketEntry bloodpressureEntry2 = bloodpressureTemplate.copy();
        for (EntryItem entryItem: bloodpressureEntry2.getEntryItems()) {
            entryItem.setItemValue("100");
        }
        BucketEntry bloodpressureEntry3 = bloodpressureTemplate.copy();
        for (EntryItem entryItem: bloodpressureEntry3.getEntryItems()) {
            entryItem.setItemValue("100");
        }
        List<BucketEntry> bloodpressureList = new ArrayList<>();
        bloodpressureList.add(bloodpressureEntry1);
        bloodpressureList.add(bloodpressureEntry2);
        bloodpressureList.add(bloodpressureEntry3);
        DataBucket bloodpressure =
                new DataBucket("Bloodpressure", bloodpressureTemplate, bloodpressureList);
        this.dataBuckets.getBucketList().add(bloodpressure);

        BucketEntry stromTemplate = new BucketEntry(
                new EntryItem(ItemType.NUMBER, "123456", null)
        );
        BucketEntry stromEntry1 = stromTemplate.copy();
        for (EntryItem entryItem: stromEntry1.getEntryItems()) {
            entryItem.setItemValue("100");
        }
        BucketEntry stromEntry2 = stromTemplate.copy();
        for (EntryItem entryItem: stromEntry2.getEntryItems()) {
            entryItem.setItemValue("100");
        }
        BucketEntry stromEntry3 = stromTemplate.copy();
        for (EntryItem entryItem: stromEntry3.getEntryItems()) {
            entryItem.setItemValue("100");
        }
        List<BucketEntry> stromList = new ArrayList<>();
        stromList.add(stromEntry1);
        stromList.add(stromEntry2);
        stromList.add(stromEntry3);
        DataBucket strom =
                new DataBucket("Strom 123456", stromTemplate, stromList);
        this.dataBuckets.getBucketList().add(strom);

        BucketEntry gasTemplate = new BucketEntry(
                new EntryItem(ItemType.NUMBER, "654321", null)
        );
        BucketEntry gasEntry1 = gasTemplate.copy();
        for (EntryItem entryItem: gasEntry1.getEntryItems()) {
            entryItem.setItemValue("100");
        }
        BucketEntry gasEntry2 = gasTemplate.copy();
        for (EntryItem entryItem: gasEntry2.getEntryItems()) {
            entryItem.setItemValue("100");
        }
        BucketEntry gasEntry3 = gasTemplate.copy();
        for (EntryItem entryItem: gasEntry3.getEntryItems()) {
            entryItem.setItemValue("100");
        }
        List<BucketEntry> gasList = new ArrayList<>();
        gasList.add(gasEntry1);
        gasList.add(gasEntry2);
        gasList.add(gasEntry3);
        DataBucket gas =
                new DataBucket("Gas 654321", gasTemplate, gasList);
        this.dataBuckets.getBucketList().add(gas);
    }

    public DataBuckets getDataBuckets() {
        return dataBuckets;
    }

    public void setDataBuckets(DataBuckets dataBuckets) {
        this.dataBuckets = dataBuckets;
    }

    public String getLogTag() {
        return logTag;
    }
}
