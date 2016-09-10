package com.mikkoville.githubviewer.view.activity;

import com.mikkoville.githubviewer.model.Commit;

import java.util.List;

/**
 * Created by ukkeli on 7.9.2016.
 */
public interface CommitsListView {

    void populateRecyclerView(List<Commit> commits);
    void showNetworkError();
    void startCommitDetailsActivity(Commit commit);
    void addMoreCommitsToRecyclerView(List<Commit> commits);
}
