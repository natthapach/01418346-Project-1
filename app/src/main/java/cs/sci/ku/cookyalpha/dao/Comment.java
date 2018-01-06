package cs.sci.ku.cookyalpha.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MegapiesPT on 6/1/2561.
 */

public class Comment implements Parcelable{
    private String text;
    private String uid;

    public Comment() {
    }

    public Comment(String text, String uid) {
        this.text = text;
        this.uid = uid;
    }

    protected Comment(Parcel in) {
        text = in.readString();
        uid = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    public String getText() {
        return text;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeString(uid);
    }
}
