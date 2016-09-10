
package com.mikkoville.githubviewer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Committer implements Parcelable {

    private String name;
    private String email;
    private String date;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.date);
    }

    public Committer() {
    }

    protected Committer(Parcel in) {
        this.name = in.readString();
        this.email = in.readString();
        this.date = in.readString();
    }

    public static final Parcelable.Creator<Committer> CREATOR = new Parcelable.Creator<Committer>() {
        @Override
        public Committer createFromParcel(Parcel source) {
            return new Committer(source);
        }

        @Override
        public Committer[] newArray(int size) {
            return new Committer[size];
        }
    };
}
