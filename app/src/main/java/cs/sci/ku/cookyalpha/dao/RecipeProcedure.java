package cs.sci.ku.cookyalpha.dao;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by MegapiesPT on 31/10/2560.
 */
@IgnoreExtraProperties
public class RecipeProcedure {
    @PropertyName("img")            public String imgUrl;
    @PropertyName("description")    public String description;

    public RecipeProcedure() {
    }

    public RecipeProcedure(String imgUrl, String description) {
        this.imgUrl = imgUrl;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Procedure{ imgUrl:" + imgUrl + ", description:" + description + "}";
    }
}
