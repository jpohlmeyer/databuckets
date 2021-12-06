package com.github.jpohlmeyer.databuckets;

import android.app.Application;

import com.github.jpohlmeyer.databuckets.model.BucketEntry;
import com.github.jpohlmeyer.databuckets.model.DataBucket;
import com.github.jpohlmeyer.databuckets.model.DataBuckets;
import com.github.jpohlmeyer.databuckets.model.EntryItem;
import com.github.jpohlmeyer.databuckets.model.ItemType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBucketsApplication extends Application {

    private DataBuckets dataBuckets;

    private final String logTag = "DataBuckets";

    public DataBucketsApplication() {
        this.dataBuckets = new DataBuckets();
        constructTestData();
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
                new EntryItem(ItemType.NUMBER, "Strom", null),
                new EntryItem(ItemType.NUMBER, "Gas", null)
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
                new DataBucket("Strom und Gas", stromTemplate, stromList);
        this.dataBuckets.getBucketList().add(strom);
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
