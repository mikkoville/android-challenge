package com.mikkoville.githubviewer.view.activity;

import com.mikkoville.githubviewer.model.Commit;

import java.util.List;

/**
 * Created by mikkoville on 7.9.2016.
 */
public interface CommitsListView {

    void addCommitsToRecyclerView(List<Commit> commits);
    void showNetworkError();
    void startCommitDetailsActivity(Commit commit);
    void showToastWithText(String text);
}
