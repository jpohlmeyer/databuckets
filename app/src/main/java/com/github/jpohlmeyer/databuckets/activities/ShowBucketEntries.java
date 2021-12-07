package com.github.jpohlmeyer.databuckets.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.jpohlmeyer.databuckets.databinding.ActivityShowBucketEntriesBinding;

public class ShowBucketEntries extends AppCompatActivity {

    private ActivityShowBucketEntriesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowBucketEntriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO
    }
}