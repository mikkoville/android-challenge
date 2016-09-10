
package com.mikkoville.githubviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Commit_ implements Parcelable {

    private Author author;
    private Committer committer;
    private String message;
    private Tree tree;
    private String url;
    private Integer comment_count;

    /**
     * 
     * @return
     *     The author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The committer
     */
    public Committer getCommitter() {
        return committer;
    }

    /**
     * 
     * @param committer
     *     The committer
     */
    public void setCommitter(Committer committer) {
        this.committer = committer;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The tree
     */
    public Tree getTree() {
        return tree;
    }

    /**
     * 
     * @param tree
     *     The tree
     */
    public void setTree(Tree tree) {
        this.tree = tree;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The comment_count
     */
    public Integer getComment_count() {
        return comment_count;
    }

    /**
     * 
     * @param comment_count
     *     The comment_count
     */
    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.author, flags);
        dest.writeParcelable(this.committer, flags);
        dest.writeString(this.message);
        dest.writeParcelable(this.tree, flags);
        dest.writeString(this.url);
        dest.writeValue(this.comment_count);
    }

    public Commit_() {
    }

    protected Commit_(Parcel in) {
        this.author = in.readParcelable(Author.class.getClassLoader());
        this.committer = in.readParcelable(Committer.class.getClassLoader());
        this.message = in.readString();
        this.tree = in.readParcelable(Tree.class.getClassLoader());
        this.url = in.readString();
        this.comment_count = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Commit_> CREATOR = new Parcelable.Creator<Commit_>() {
        @Override
        public Commit_ createFromParcel(Parcel source) {
            return new Commit_(source);
        }

        @Override
        public Commit_[] newArray(int size) {
            return new Commit_[size];
        }
    };
}
