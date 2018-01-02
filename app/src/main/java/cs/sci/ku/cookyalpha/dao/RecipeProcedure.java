package cs.sci.ku.cookyalpha.dao;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Arrays;

import cs.sci.ku.cookyalpha.callbacks.ImageUrlSettable;

/**
 * Created by MegapiesPT on 31/10/2560.
 */
@IgnoreExtraProperties
public class RecipeProcedure implements Parcelable, ImageUrlSettable{
    @PropertyName("img")            public String imgUrl;
    @PropertyName("description")    public String description;
    @Exclude public byte[] datas;
    @Exclude public String imgUri;

    public RecipeProcedure() {
    }

    public RecipeProcedure(String imgUrl, String description) {
        this.imgUrl = imgUrl;
        this.description = description;
    }

    protected RecipeProcedure(Parcel in) {
        imgUrl = in.readString();
        description = in.readString();
        datas = in.createByteArray();
        imgUri = in.readString();
    }

    public static final Creator<RecipeProcedure> CREATOR = new Creator<RecipeProcedure>() {
        @Override
        public RecipeProcedure createFromParcel(Parcel in) {
            return new RecipeProcedure(in);
        }

        @Override
        public RecipeProcedure[] newArray(int size) {
            return new RecipeProcedure[size];
        }
    };

    @Override
    public String toString() {
        return "RecipeProcedure{" +
                "imgUrl='" + imgUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imgUrl);
        parcel.writeString(description);
        parcel.writeByteArray(datas);
        parcel.writeString(imgUri);
    }

    @Override
    public void setImageUrl(String url) {
        this.imgUrl = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDescription() {
        return description;
    }
}
