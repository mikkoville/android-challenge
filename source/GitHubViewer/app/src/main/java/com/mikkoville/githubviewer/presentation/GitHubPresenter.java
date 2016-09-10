package com.mikkoville.githubviewer.presentation;

import com.mikkoville.githubviewer.view.activity.CommitsListView;

/**
 * Created by ukkeli on 7.9.2016.
 */
public interface GitHubPresenter extends LifecyclePresenter {
    void loadCommitsFromGithub(String owner, String repo);
    void setView(CommitsListView view);
    
}
