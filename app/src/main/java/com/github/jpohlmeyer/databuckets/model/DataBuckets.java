package com.github.jpohlmeyer.databuckets.model;

import java.util.ArrayList;
import java.util.List;


public class DataBuckets {

    private List<DataBucket> bucketList;

    public DataBuckets() {
        bucketList = new ArrayList<>();
    }

    public List<DataBucket> getBucketList() {
        return bucketList;
    }

    public void setBucketList(List<DataBucket> bucketList) {
        this.bucketList = bucketList;
    }

}
