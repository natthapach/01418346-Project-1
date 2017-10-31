package cs.sci.ku.cookyalpha.dao;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

@IgnoreExtraProperties
public class RecipePreview {
    public String imgUrl;

    public RecipePreview(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public RecipePreview() {

    }
}
