package cs.sci.ku.cookyalpha.dao;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

@IgnoreExtraProperties
public class Recipe {
    public String description;
    public int like;
    public String name;
    public String ownerId;
    public String createdTime;
    public List<Ingredient> ingredients;
    public List<RecipeProcedure> procedures;
    public RecipePreview preview;

    public Recipe() {
    }

    public Recipe(String description, int like, String name, String ownerId, String createdTime, List<Ingredient> ingredients, List<RecipeProcedure> procedures, RecipePreview preview) {
        this.description = description;
        this.like = like;
        this.name = name;
        this.ownerId = ownerId;
        this.createdTime = createdTime;
        this.ingredients = ingredients;
        this.procedures = procedures;
        this.preview = preview;
    }
}
