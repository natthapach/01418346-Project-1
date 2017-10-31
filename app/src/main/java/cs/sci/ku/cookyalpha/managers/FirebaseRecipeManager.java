package cs.sci.ku.cookyalpha.managers;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Map;

import cs.sci.ku.cookyalpha.dao.Recipe;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

public class FirebaseRecipeManager implements RecipeManager{
    private static FirebaseRecipeManager instance;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    public static FirebaseRecipeManager getInstance(){
        if (instance==null)
            instance = new FirebaseRecipeManager();
        return instance;
    }
    private FirebaseRecipeManager(){
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("recipe");
    }
    @Override
    public List<Recipe> loadGlobalRecipe() {
        Log.d("My Application", "loadGlobalRecipe");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("My Application", "FirebaseRecipeManager/onDataChange:dataSnapshot=" + dataSnapshot);
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    Recipe recipe = child.getValue(Recipe.class);
                    Log.d("My Application", "FirebaseRecipeManager/onDataChange:recipe=" + recipe);
                    Log.d("My Application", "FirebaseRecipeManager/onDataChange:child=" + child);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return null;
    }
}
