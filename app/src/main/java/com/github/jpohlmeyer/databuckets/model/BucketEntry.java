package com.github.jpohlmeyer.databuckets.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class BucketEntry {

    private List<EntryItem> entryItems;

    private LocalDateTime timestamp;

    public BucketEntry(LocalDateTime timestamp, List<EntryItem> entryItems) {
        this.timestamp = timestamp;
        this.entryItems = entryItems;
    }

    public BucketEntry(LocalDateTime timestamp, EntryItem... entryItems) {
        this.timestamp = timestamp;
        this.entryItems = Arrays.asList(entryItems);
    }

    public BucketEntry(EntryItem... entryItems) {
        this(LocalDateTime.now(), entryItems);
    }

    public BucketEntry copy() {
        EntryItem[] items = new EntryItem[entryItems.size()];
        for (int i = 0; i < entryItems.size(); i++) {
            items[i] = new EntryItem(entryItems.get(i).getItemType(), entryItems.get(i).getItemDescription(), entryItems.get(i).getItemValue());
        }
        return new BucketEntry(items);
    }

    public List<EntryItem> getEntryItems() {
        return entryItems;
    }

    public void setEntryItems(List<EntryItem> entryItems) {
        this.entryItems = entryItems;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        sb.append("\n");
        for (EntryItem item: entryItems) {
            sb.append(item.getItemDescription());
            sb.append(": ");
            sb.append(item.getItemValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
