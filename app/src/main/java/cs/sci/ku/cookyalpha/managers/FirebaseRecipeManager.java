package cs.sci.ku.cookyalpha.managers;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs.sci.ku.cookyalpha.dao.Recipe;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

public class FirebaseRecipeManager implements RecipeManager{
    private static FirebaseRecipeManager instance;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private Set<RecipeObserver> observers;
    private List<Recipe> recipes;

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

    @Override
    public List<Recipe> loadGlobalRecipe() {
        Log.d("My Application", "loadGlobalRecipe");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("My Application", "FirebaseRecipeManager/onDataChange:dataSnapshot=" + dataSnapshot);
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    Log.d("My Application", "FirebaseRecipeManager/onDataChange:dataSnapshot.getValue()=" + dataSnapshot.getValue());
                    Recipe recipe = child.getValue(Recipe.class);
//                    Log.d("My Application", "FirebaseRecipeManager/onDataChange:recipe=" + recipe);
//                    Log.d("My Application", "FirebaseRecipeManager/onDataChange:child=" + child);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return null;
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
