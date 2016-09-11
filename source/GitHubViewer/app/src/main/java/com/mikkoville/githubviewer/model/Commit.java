
package com.mikkoville.githubviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Commit implements Parcelable {

    private String sha;
    private Commit_ commit;
    private String url;
    private String html_url;
    private String comments_url;
    private Author_ author;
    private List<Parent> parents = new ArrayList<Parent>();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Commit)) {
            return false;
        }

        Commit commit1 = (Commit) o;

        if (!sha.equals(commit1.sha)) return false;
        return html_url.equals(commit1.html_url);

    }

    @Override
    public int hashCode() {
        int result = sha.hashCode();
        result = 31 * result + commit.hashCode();
        return result;
    }

    /**
     * 
     * @return
     *     The sha
     */
    public String getSha() {
        return sha;
    }

    /**
     * 
     * @param sha
     *     The sha
     */
    public void setSha(String sha) {
        this.sha = sha;
    }

    /**
     * 
     * @return
     *     The commit
     */
    public Commit_ getCommit() {
        return commit;
    }

    /**
     * 
     * @param commit
     *     The commit
     */
    public void setCommit(Commit_ commit) {
        this.commit = commit;
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
     *     The html_url
     */
    public String getHtml_url() {
        return html_url;
    }

    /**
     * 
     * @param html_url
     *     The html_url
     */
    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    /**
     * 
     * @return
     *     The comments_url
     */
    public String getComments_url() {
        return comments_url;
    }

    /**
     * 
     * @param comments_url
     *     The comments_url
     */
    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    /**
     * 
     * @return
     *     The author
     */
    public Author_ getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(Author_ author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The parents
     */
    public List<Parent> getParents() {
        return parents;
    }

    /**
     * 
     * @param parents
     *     The parents
     */
    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sha);
        dest.writeParcelable(this.commit, flags);
        dest.writeString(this.url);
        dest.writeString(this.html_url);
        dest.writeString(this.comments_url);
        dest.writeParcelable(this.author, flags);
        dest.writeList(this.parents);
    }

    public Commit() {
    }

    protected Commit(Parcel in) {
        this.sha = in.readString();
        this.commit = in.readParcelable(Commit_.class.getClassLoader());
        this.url = in.readString();
        this.html_url = in.readString();
        this.comments_url = in.readString();
        this.author = in.readParcelable(Author_.class.getClassLoader());
        this.parents = new ArrayList<Parent>();
        in.readList(this.parents, Parent.class.getClassLoader());
    }

    public static final Parcelable.Creator<Commit> CREATOR = new Parcelable.Creator<Commit>() {
        @Override
        public Commit createFromParcel(Parcel source) {
            return new Commit(source);
        }

        @Override
        public Commit[] newArray(int size) {
            return new Commit[size];
        }
    };
}
