package cs.sci.ku.cookyalpha.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;

/**
 * Created by MegapiesPT on 9/11/2560.
 */

public class Like implements Parcelable{
    @PropertyName("user-id")    public String userId;

    public Like() {}

    public Like(String userId) {
        this.userId = userId;
    }

    protected Like(Parcel in) {
        userId = in.readString();
    }

    public static final Creator<Like> CREATOR = new Creator<Like>() {
        @Override
        public Like createFromParcel(Parcel in) {
            return new Like(in);
        }

        @Override
        public Like[] newArray(int size) {
            return new Like[size];
        }
    };

    @Override
    public String toString() {
        return "Like("+userId+")";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userId);
    }

    @PropertyName("user-id")
    public String getUserId() {
        return userId;
    }
}
