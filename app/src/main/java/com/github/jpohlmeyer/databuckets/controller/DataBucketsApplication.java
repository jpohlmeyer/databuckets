package com.github.jpohlmeyer.databuckets.controller;

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
    private StorageManager storageManager;

    private final String logTag = "DataBuckets";

    public DataBucketsApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.storageManager = new StorageManager(this, logTag);
        DataBuckets loadedBuckets = this.storageManager.loadFromFile();
        if (loadedBuckets == null) {
            dataBuckets = new DataBuckets();
        } else {
            dataBuckets = loadedBuckets;
        }
    }

    public StorageManager getStorageManager() {
        return storageManager;
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
