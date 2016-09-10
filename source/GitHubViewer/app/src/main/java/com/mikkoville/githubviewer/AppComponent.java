package com.mikkoville.githubviewer;

import com.mikkoville.githubviewer.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mikkoville on 8.9.2016.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(MainActivity target);
}
