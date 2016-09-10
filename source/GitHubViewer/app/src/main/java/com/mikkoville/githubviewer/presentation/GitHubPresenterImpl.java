package com.mikkoville.githubviewer.presentation;

import com.mikkoville.githubviewer.Constants;
import com.mikkoville.githubviewer.data.GitHubApi;
import com.mikkoville.githubviewer.model.Commit;
import com.mikkoville.githubviewer.view.activity.CommitsListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by mikkoville on 8.9.2016.
 */
public class GitHubPresenterImpl implements GitHubPresenter {

    private CommitsListView view;
    private GitHubApi gitHubApi;

    public GitHubPresenterImpl(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public void loadCommitsFromGithub(String owner, String repo) {
        Timber.d("Loading commits from GitHub");
        gitHubApi.getCommits(owner, repo).enqueue(new Callback<List<Commit>>() {
            @Override
            public void onResponse(Call<List<Commit>> call, Response<List<Commit>> response) {
                List<Commit> commits = response.body();
                view.populateRecyclerView(commits);
            }

            @Override
            public void onFailure(Call<List<Commit>> call, Throwable t) {
                view.showNetworkError();
            }
        });
    }

    @Override
    public void setView(CommitsListView view) {
        this.view = view;
    }

    @Override
    public void loadMoreCommitsSinceDate(String owner, String repo, String date) {
        Timber.d("Load more commit since %s", date);
        gitHubApi.getCommitsUntil(owner, repo, date).enqueue(new Callback<List<Commit>>() {
            @Override
            public void onResponse(Call<List<Commit>> call, Response<List<Commit>> response) {
                List<Commit> commits = response.body();
                view.addMoreCommitsToRecyclerView(commits);
            }

            @Override
            public void onFailure(Call<List<Commit>> call, Throwable t) {
                view.showNetworkError();
            }
        });
    }

    //repo and owner hardcoded
    @Override
    public void resume() {
        loadCommitsFromGithub(Constants.REPO_OWNER, Constants.ANDROID_REPO);
    }

    @Override
    public void pause() {

    }
}
