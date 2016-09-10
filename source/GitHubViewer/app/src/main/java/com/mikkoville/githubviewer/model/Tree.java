
package com.mikkoville.githubviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tree implements Parcelable {

    private String sha;
    private String url;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sha);
        dest.writeString(this.url);
    }

    public Tree() {
    }

    protected Tree(Parcel in) {
        this.sha = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Tree> CREATOR = new Parcelable.Creator<Tree>() {
        @Override
        public Tree createFromParcel(Parcel source) {
            return new Tree(source);
        }

        @Override
        public Tree[] newArray(int size) {
            return new Tree[size];
        }
    };
}
