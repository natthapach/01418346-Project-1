package cs.sci.ku.cookyalpha.dao;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by MegapiesPT on 31/10/2560.
 */
@IgnoreExtraProperties
public class Ingredient {
    @PropertyName("name") public String name;
    @PropertyName("amt") public int amount;

    public Ingredient() {
    }

    public Ingredient(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Ingredient {name:" + name + ", amount:" + amount + "}";
    }
}
