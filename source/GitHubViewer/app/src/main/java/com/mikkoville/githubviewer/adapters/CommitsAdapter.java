package com.mikkoville.githubviewer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikkoville.githubviewer.R;
import com.mikkoville.githubviewer.model.Commit;
import com.squareup.picasso.Picasso;


import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Recyclerview adapter
 * Created by mikkoville on 9.9.2016.
 */
public class CommitsAdapter extends RecyclerView.Adapter<CommitsAdapter.ViewHolder> {

    private List<Commit> commitList;
    private final OnItemClickListener listener;


    public interface OnItemClickListener{
        void onItemClick(Commit commit);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.comment_author) TextView authorTextView;
        @BindView(R.id.comment_text) TextView commentTextView;
        @BindView(R.id.comment_date) TextView commentDateTextView;
        @BindView(R.id.avatar_icon) ImageView avatarImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindItem(final Commit commit, final OnItemClickListener listener){
            authorTextView.setText(commit.getCommit().getAuthor().getName());
            commentTextView.setText(commit.getCommit().getMessage());
            commentDateTextView.setText(commit.getCommit().getAuthor().getDate());

            Context localContext = itemView.getContext();
            if(localContext != null && commit.getAuthor() != null ){
                Picasso.with(localContext)
                        .load(commit.getAuthor().getAvatar_url())
                        .resize(80,100)
                        .centerInside()
                        .into(avatarImageView);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(commit);
                }
            });
        }
    }

    public CommitsAdapter(List<Commit> commitList, OnItemClickListener listener) {
        this.commitList = commitList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commit_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Timber.d("Row %d set on viewHolder", position);
        holder.bindItem(commitList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return commitList.size();
    }
}
