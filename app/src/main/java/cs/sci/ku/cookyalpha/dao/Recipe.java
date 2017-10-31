package cs.sci.ku.cookyalpha.dao;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.List;
import java.util.Map;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

@IgnoreExtraProperties
public class Recipe {
    public String id;
    @PropertyName("description")    public String description;
    @PropertyName("like")           public int like;
    @PropertyName("name")           public String name;
    @PropertyName("owner")          public String ownerId;
    @PropertyName("time")           public String createdTime;
    @PropertyName("ingredient")     public Map<String, Ingredient> ingredients;
    @PropertyName("procedure")      public Map<String, RecipeProcedure> procedures;
    @PropertyName("preview")        public RecipePreview preview;

    public Recipe() {
    }

    public Recipe(String description, int like, String name, String ownerId, String createdTime, Map<String, Ingredient> ingredients, Map<String, RecipeProcedure> procedures, RecipePreview preview) {
        this.description = description;
        this.like = like;
        this.name = name;
        this.ownerId = ownerId;
        this.createdTime = createdTime;
        this.ingredients = ingredients;
        this.procedures = procedures;
        this.preview = preview;
    }

    @Override
    public String toString() {
        return String.format("Recipe { id:%s, name:%s, description:%s, like:%d, owner:%s, time:%s, ingredients:%s, procedures:%s, preview:%s}", id, name, description, like, ownerId, createdTime, ingredients, procedures, preview);
    }
}
