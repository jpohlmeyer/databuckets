package com.github.jpohlmeyer.databuckets.model;

import java.util.List;

public class DataBucket {

    private String name;
    private BucketEntry entryTemplate;
    private List<BucketEntry> entries;

    public DataBucket(String name, BucketEntry entryTemplate, List<BucketEntry> entries) {
        this.name = name;
        this.entryTemplate = entryTemplate;
        this.entries = entries;
    }

    public BucketEntry constructEntry() {
        return entryTemplate.copy();
    }

    public String getName() {
        return name;
    }

    public BucketEntry getEntryTemplate() {
        return entryTemplate;
    }

    public void setEntryTemplate(BucketEntry entryTemplate) {
        this.entryTemplate = entryTemplate;
    }

    public List<BucketEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<BucketEntry> entries) {
        this.entries = entries;
    }

    public void addEntry(BucketEntry bucketEntry) {
        this.entries.add(bucketEntry);
    }
}
