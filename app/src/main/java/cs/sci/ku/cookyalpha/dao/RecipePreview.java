package cs.sci.ku.cookyalpha.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

@IgnoreExtraProperties
public class RecipePreview implements Parcelable {
    @PropertyName("img") public String imgUrl;
    public byte[] datas;

    public RecipePreview(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public RecipePreview() {

    }

    protected RecipePreview(Parcel in) {
        imgUrl = in.readString();
        datas = in.createByteArray();
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
        return "Preview{ imgUrl:" + imgUrl + "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imgUrl);
        parcel.writeByteArray(datas);
    }
}
