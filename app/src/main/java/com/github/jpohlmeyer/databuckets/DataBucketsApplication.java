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
        stromList.add(gasEntry1);
        stromList.add(gasEntry2);
        stromList.add(gasEntry3);
        DataBucket gas =
                new DataBucket("Gas 654321", stromTemplate, stromList);
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
