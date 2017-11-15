package cs.sci.ku.cookyalpha.dao;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

@IgnoreExtraProperties
public class RecipePreview {
    @PropertyName("img") public String imgUrl;
    public byte[] datas;

    public RecipePreview(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public RecipePreview() {

    }

    @Override
    public String toString() {
        return "Preview{ imgUrl:" + imgUrl + "}";
    }
}
