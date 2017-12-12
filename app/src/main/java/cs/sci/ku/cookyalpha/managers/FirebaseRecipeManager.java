package cs.sci.ku.cookyalpha.managers;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs.sci.ku.cookyalpha.callbacks.UploadRecipeCallback;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.utils.UserProfileCarrier;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

public class FirebaseRecipeManager{
    private static FirebaseRecipeManager instance;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private Set<RecipeObserver> observers;
    private List<Recipe> recipes;
    private Map<String, Recipe> recipeMap;

    public static FirebaseRecipeManager getInstance(){
        if (instance==null)
            instance = new FirebaseRecipeManager();
        return instance;
    }
    private FirebaseRecipeManager(){
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("recipe");
        observers = new HashSet<>();
        recipes = new ArrayList<>();
        recipeMap = new HashMap<>();

        initCallBack();
    }

    private void notifyObserversOnAdd(Recipe recipe){
        for (RecipeObserver observer : observers)
            observer.onRecipeAdd(recipe);
    }
    private void initCallBack() {
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Recipe recipe = dataSnapshot.getValue(Recipe.class);
                if (recipe != null){
                    recipe.id = dataSnapshot.getKey();
                    recipes.add(recipe);
                    recipeMap.put(recipe.id, recipe);
                    Log.d("My App", recipe.toString());
                    notifyObserversOnAdd(recipe);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public Recipe getRecipe(String id){
        return recipeMap.get(id);
    }
    public List<String> getCategories(){
        List<String> categories = new ArrayList<>();
        categories.add("Breakfast");
        categories.add("Lunch");
        categories.add("Dinner");
        return categories;
    }
    public void uploadRecipe(final Recipe recipe, final UploadRecipeCallback callback){
        recipe.ownerId = UserProfileCarrier.getInstance().getUser().getId();
        Log.d("uploadRecipe", "in uploadRecipe " + recipe);
        RecipeUploader uploader = new RecipeUploader(recipe, new UploadRecipeCallback() {
            @Override
            public void onComplete(String recipeId) {
                Log.d("upload complete", "recipe id " + recipeId);
                callback.onComplete(recipeId);
            }

            @Override
            public void onFailure() {

            }
        });
        uploader.upload();
    }

    public List<Recipe> getUserRecipes(@NonNull String userId){
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : this.recipes)
            if (userId.equals(recipe.getOwnerId()))
                recipes.add(recipe);
        return recipes;
    }

    public List<Recipe> addObserver(RecipeObserver observer){
        observers.add(observer);
        return recipes;
    }
    public void removeObserver(RecipeObserver observer){
        observers.remove(observer);
    }
    public interface RecipeObserver{
        void onRecipeAdd(Recipe recipe);
        void onRecipeChange(Recipe recipe);
        void onRecipeRemove(Recipe recipe);
    }
}
