
package com.mikkoville.githubviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parent implements Parcelable {

    private String sha;
    private String url;
    private String html_url;

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sha);
        dest.writeString(this.url);
        dest.writeString(this.html_url);
    }

    public Parent() {
    }

    protected Parent(Parcel in) {
        this.sha = in.readString();
        this.url = in.readString();
        this.html_url = in.readString();
    }

    public static final Parcelable.Creator<Parent> CREATOR = new Parcelable.Creator<Parent>() {
        @Override
        public Parent createFromParcel(Parcel source) {
            return new Parent(source);
        }

        @Override
        public Parent[] newArray(int size) {
            return new Parent[size];
        }
    };
}
