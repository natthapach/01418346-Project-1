package cs.sci.ku.cookyalpha.dao;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

@IgnoreExtraProperties
public class Recipe implements Parcelable{
    public String id;
    @PropertyName("description")    public String description;
    @PropertyName("like")           public Map<String, Like> like;
    @PropertyName("name")           public String name;
    @PropertyName("owner")          public String ownerId;
    @PropertyName("time")           public String createdTime;
    @PropertyName("ingredient")     public Map<String, Ingredient> ingredients;
    @PropertyName("procedure")      public Map<String, RecipeProcedure> procedures;
    @PropertyName("preview")        public RecipePreview preview;

    public Recipe() {
        ingredients = new HashMap<>();
        procedures = new HashMap<>();
    }

    public Recipe(String id, String description, Map<String, Like> like, String name, String ownerId, String createdTime, Map<String, Ingredient> ingredients, Map<String, RecipeProcedure> procedures, RecipePreview preview) {
        this.id = id;
        this.description = description;
        this.like = like;
        this.name = name;
        this.ownerId = ownerId;
        this.createdTime = createdTime;
        this.ingredients = ingredients;
        this.procedures = procedures;
        this.preview = preview;
    }

    protected Recipe(Parcel in) {
        id = in.readString();
        description = in.readString();
        name = in.readString();
        ownerId = in.readString();
        createdTime = in.readString();
        preview = in.readParcelable(RecipePreview.class.getClassLoader());
        in.readMap(this.procedures, RecipeProcedure.class.getClassLoader());
        in.readMap(this.ingredients, Ingredient.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public String toString() {
        return String.format("Recipe { id:%s, name:%s, description:%s, like:%s, owner:%s, time:%s, ingredients:%s, procedures:%s, preview:%s}", id, name, description, like, ownerId, createdTime, ingredients, procedures, preview);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(description);
        parcel.writeString(name);
        parcel.writeString(ownerId);
        parcel.writeString(createdTime);
        parcel.writeParcelable(preview, i);
        // TODO write parcel map (Like, Ingredient, procedure)
        parcel.writeMap(procedures);
        parcel.writeMap(ingredients);
    }

    public void setIngredientsList(List<Ingredient> ingredientsList){
        ingredients.clear();
        for (int i=0; i<ingredientsList.size(); i++)
            ingredients.put(i+"", ingredientsList.get(i));
    }
    public void setProceduresList(List<RecipeProcedure> proceduresList){
        procedures.clear();
        for(int i=0; i<proceduresList.size(); i++)
            procedures.put(i + "", proceduresList.get(i));
    }
    public void setPreview(RecipePreview preview){
        this.preview = preview;
    }
}
