package cs.sci.ku.cookyalpha.managers;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import cs.sci.ku.cookyalpha.dao.Recipe;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

public class FirebaseRecipeManager implements RecipeManager{
    private static FirebaseRecipeManager instance;
    private FirebaseDatabase database;
    public static FirebaseRecipeManager getInstance(){
        if (instance==null)
            instance = new FirebaseRecipeManager();
        return instance;
    }
    private FirebaseRecipeManager(){}
    @Override
    public List<Recipe> loadGlobalRecipe() {

        return null;
    }
}
