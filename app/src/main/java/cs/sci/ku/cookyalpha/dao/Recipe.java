package cs.sci.ku.cookyalpha.dao;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

@IgnoreExtraProperties
public class Recipe implements Parcelable{
    public String id;
    @PropertyName("description")    private String description;
    @PropertyName("like")           private Map<String, Like> like;
    @PropertyName("name")           private String name;
    @PropertyName("ownerId")        private String ownerId;
    @PropertyName("time")           private String createdTime;
    @PropertyName("ingredient")     private Map<String, Ingredient> ingredients;
    @PropertyName("procedure")      private Map<String, RecipeProcedure> procedures;
    @PropertyName("preview")        private RecipePreview preview;

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
        ingredients = new HashMap<>();
        procedures = new HashMap<>();
        like = new HashMap<>();
        in.readMap(this.ingredients, Ingredient.class.getClassLoader());
        in.readMap(this.procedures, RecipeProcedure.class.getClassLoader());
        in.readMap(this.like, Like.class.getClassLoader());
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
        parcel.writeMap(ingredients);
        parcel.writeMap(procedures);
        parcel.writeMap(like);
    }

    public void setIngredientsList(List<Ingredient> ingredientsList){
        ingredients.clear();
        for (int i=0; i<ingredientsList.size(); i++)
            ingredients.put("I"+i, ingredientsList.get(i));
    }
    public void setProceduresList(List<RecipeProcedure> proceduresList){
        if (proceduresList == null)
            return;
        procedures.clear();
        for(int i=0; i<proceduresList.size(); i++)
            procedures.put("P"+i, proceduresList.get(i));
    }
    public void setPreview(RecipePreview preview){
        this.preview = preview;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Like> getLike() {
        return like;
    }

    public String getName() {
        return name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public Map<String, Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<RecipeProcedure> getProceduresList(){
        ArrayList<RecipeProcedure> proceduresList = new ArrayList<>();
        for (Map.Entry<String, RecipeProcedure> entry : procedures.entrySet()){
            proceduresList.add(entry.getValue());
        }
        return proceduresList;
    }

    public ArrayList<Ingredient> getIngredientsList(){
        ArrayList<Ingredient> ingredientsList = new ArrayList<>();
        for (Map.Entry<String, Ingredient> entry : ingredients.entrySet()){
            ingredientsList.add(entry.getValue());
        }
        return ingredientsList;
    }

    public Map<String, RecipeProcedure> getProcedures() {
        return procedures;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setIngredients(Map<String, Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setProcedures(Map<String, RecipeProcedure> procedures) {
        this.procedures = procedures;
    }

    public RecipePreview getPreview() {
        return preview;
    }

    public boolean isLike(String userId){
        if (like == null)
            return false;
        return like.keySet().contains(userId);
    }

    public int countLike(){
        if (like == null)
            return 0;
        return like.size();
    }
}
