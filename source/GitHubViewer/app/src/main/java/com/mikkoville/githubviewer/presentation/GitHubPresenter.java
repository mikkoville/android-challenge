package com.mikkoville.githubviewer.presentation;

import com.mikkoville.githubviewer.view.activity.CommitsListView;

/**
 * Created by mikkoville on 7.9.2016.
 */
public interface GitHubPresenter extends LifecyclePresenter {
    void loadCommitsFromGithub(String owner, String repo);
    void setView(CommitsListView view);
    void loadCommitsUntilDate(String owner, String repo, String date);
    
}
