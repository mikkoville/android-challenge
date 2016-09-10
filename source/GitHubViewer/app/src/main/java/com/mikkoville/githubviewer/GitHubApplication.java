package com.mikkoville.githubviewer;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by mikkoville on 8.9.2016.
 */
public class GitHubApplication extends Application{

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }

    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
