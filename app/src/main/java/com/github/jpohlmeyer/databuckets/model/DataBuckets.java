package com.github.jpohlmeyer.databuckets.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataBuckets {

    private List<DataBucket> bucketList;

    @Inject
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
