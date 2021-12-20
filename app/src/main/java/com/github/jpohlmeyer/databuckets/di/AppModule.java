package com.github.jpohlmeyer.databuckets.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Reusable
    @Provides
    @Named("logTag")
    static String provideLogTag() {
        return "DataBuckets";
    }
}
