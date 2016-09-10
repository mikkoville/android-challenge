package com.mikkoville.githubviewer.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikkoville.githubviewer.Constants;
import com.mikkoville.githubviewer.GitHubApplication;
import com.mikkoville.githubviewer.R;
import com.mikkoville.githubviewer.adapters.CommitsAdapter;
import com.mikkoville.githubviewer.adapters.EndOfRecyclerViewlistener;
import com.mikkoville.githubviewer.model.Commit;
import com.mikkoville.githubviewer.presentation.GitHubPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements CommitsListView {

    @Inject GitHubPresenter gitHubPresenter;

    @BindView(R.id.commits_recyclerView) RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private CommitsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((GitHubApplication)getApplication()).getAppComponent().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gitHubPresenter.setView(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gitHubPresenter.resume();
    }

    @Override
    public void populateRecyclerView(List<Commit> commits) {
        adapter = new CommitsAdapter(commits, new CommitsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Commit commit) {
                startCommitDetailsActivity(commit);
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndOfRecyclerViewlistener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Timber.d("End of list");
                String date = adapter.getLastCommitDate();
                gitHubPresenter.loadMoreCommitsSinceDate("android", "platform_build", date);
            }
        });
    }

    @Override
    public void showNetworkError() {
        Toast.makeText(MainActivity.this, "Error getting commits", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startCommitDetailsActivity(Commit commit) {
        Intent i = new Intent();
        Bundle b = new Bundle();
        b.putParcelable(Constants.DETAILS_COMMIT, commit);
        i.putExtras(b);
        i.setClass(this, CommitActivity.class);
        startActivity(i);
    }

    @Override
    public void addMoreCommitsToRecyclerView(List<Commit> commits) {
        int currentSize = adapter.getItemCount();
        adapter.getCommitList().addAll(commits);
        adapter.notifyItemRangeChanged(currentSize, adapter.getCommitList().size() - 1);
    }

}
