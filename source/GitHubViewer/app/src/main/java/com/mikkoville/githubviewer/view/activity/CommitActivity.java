package com.mikkoville.githubviewer.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mikkoville.githubviewer.Constants;
import com.mikkoville.githubviewer.R;
import com.mikkoville.githubviewer.model.Commit;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by mikkoville on 10.9.2016.
 */
public class CommitActivity extends AppCompatActivity implements CommitDetailsView{

    public static final String COMMIT = "commit";

    @BindView(R.id.details_author) TextView authorTextViev;
    @BindView(R.id.details_sha) TextView shaTextViev;
    @BindView(R.id.details_message) TextView messageTextViev;
    @BindView(R.id.details_date) TextView dateTextViev;
    @BindView(R.id.details_html_link) TextView linkTextViev;

    private Commit commit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);
        ButterKnife.bind(this);

        if(savedInstanceState != null){
            Timber.d("Get commit from savedInstanceState");
            commit = savedInstanceState.getParcelable(COMMIT);
        }else {
            Timber.d("Get new commit from bundle");
            Bundle b = this.getIntent().getExtras();
            if (b != null){
                commit = b.getParcelable(Constants.DETAILS_COMMIT);
            }
        }
        if(commit != null){
            showCommitDetails();
        }
    }

    @Override
    public void showCommitDetails() {
        authorTextViev.setText(getString(R.string.details_author, commit.getCommit().getAuthor().getName()));
        dateTextViev.setText(getString(R.string.details_date, commit.getCommit().getAuthor().getDate()));
        shaTextViev.setText(getString(R.string.details_sha, commit.getSha()));
        messageTextViev.setText(getString(R.string.details_message, commit.getCommit().getMessage()));
        linkTextViev.setText(commit.getHtml_url());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Timber.d("Save commit onSaveInstanceState");
        outState.putParcelable(COMMIT, commit);
        super.onSaveInstanceState(outState);
    }
}
