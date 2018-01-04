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
public class RecipePreview implements Parcelable, ImageUrlSettable {
    @PropertyName("img") private String imgUrl;
    @Exclude private byte[] datas;
    @Exclude private Uri uri;

    public RecipePreview(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public RecipePreview() {

    }


    protected RecipePreview(Parcel in) {
        imgUrl = in.readString();
        datas = in.createByteArray();
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<RecipePreview> CREATOR = new Creator<RecipePreview>() {
        @Override
        public RecipePreview createFromParcel(Parcel in) {
            return new RecipePreview(in);
        }

        @Override
        public RecipePreview[] newArray(int size) {
            return new RecipePreview[size];
        }
    };

    @Override
    public String toString() {
        return "RecipePreview{" +
                "imgUrl='" + imgUrl + '\'' +
                ", datas=" + Arrays.toString(datas) +
                ", uri=" + uri +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imgUrl);
        parcel.writeByteArray(datas);
        parcel.writeParcelable(uri, i);
    }

    @Override
    public void setImageUrl(String url) {
        this.imgUrl = url;
    }

    @PropertyName("img")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Exclude
    public byte[] getDatas() {
        return datas;
    }

    public void setDatas(byte[] datas) {
        this.datas = datas;
    }

    @Exclude
    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
