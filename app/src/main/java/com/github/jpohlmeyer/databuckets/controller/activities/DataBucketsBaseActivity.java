package com.github.jpohlmeyer.databuckets.controller.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.jpohlmeyer.databuckets.controller.DataBucketsApplication;

public class DataBucketsBaseActivity extends AppCompatActivity {

    private DataBucketsApplication dataBucketsApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dataBucketsApplication = (DataBucketsApplication) getApplication();
    }

    protected DataBucketsApplication getDataBucketsApplication() {
        return dataBucketsApplication;
    }
}
