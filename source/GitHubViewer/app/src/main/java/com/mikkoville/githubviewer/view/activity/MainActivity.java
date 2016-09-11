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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;



public class MainActivity extends AppCompatActivity implements CommitsListView {

    public static final String RECYCLERVIEW_STATE = "recyclerViewState";

    @Inject GitHubPresenter gitHubPresenter;

    @BindView(R.id.commits_recyclerView) RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private CommitsAdapter adapter;
    private List<Commit> commitList;


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

        if(savedInstanceState != null){
            Timber.d("Restore recyclerview state");
            commitList = savedInstanceState.getParcelableArrayList(RECYCLERVIEW_STATE);
        }else {
            commitList = new ArrayList<>();
        }
        initRecyclerView();

    }

    private void initRecyclerView(){
        adapter = new CommitsAdapter(commitList, new CommitsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Commit commit) {
                startCommitDetailsActivity(commit);
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new EndOfRecyclerViewlistener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                String date = adapter.getLastCommitDate();
                Timber.d("End of list. Load more until date: %s", date);
                gitHubPresenter.loadCommitsUntilDate("android", "platform_build", date);
            }
        });
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
        if (id == R.id.action_refresh) {
            if(!adapter.getCommitList().isEmpty()){
                commitList = new ArrayList<>();
                initRecyclerView();
            }
            gitHubPresenter.resume();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if list is empty get commits
        if(commitList.isEmpty()){
            Timber.d("Resume githubPresenter");
            gitHubPresenter.resume();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Timber.d("Save recyclerview state onSaveInstanceState");
        outState.putParcelableArrayList(RECYCLERVIEW_STATE, new ArrayList<>(commitList));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void addCommitsToRecyclerView(List<Commit> commits) {
        int currentSize = adapter.getItemCount();

        //todo remove this hacky way of removing duplicates
        //remove first item from new commits if it is already on the old list
        if(!adapter.getCommitList().isEmpty() && commits.get(0).equals(adapter.getCommitList().get(adapter.getCommitList().size() - 1))){
            Timber.d("Remove duplicate commit");
            commits.remove(0);
        }

        adapter.getCommitList().addAll(commits);
        adapter.notifyItemRangeChanged(currentSize, adapter.getCommitList().size() - 1);
        Timber.d("Added commits to list. List size: %d", adapter.getCommitList().size());
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
    public void showToastWithText(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

}
