package cs.sci.ku.cookyalpha.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by MegapiesPT on 31/10/2560.
 */
@IgnoreExtraProperties
public class Ingredient implements Parcelable{
    @PropertyName("name") public String name;
    @PropertyName("amt") public String amount;

    public Ingredient() {
    }

    public Ingredient(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
        amount = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(amount);
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }
}
