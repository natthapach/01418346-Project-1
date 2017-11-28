package cs.sci.ku.cookyalpha.utils;

import java.util.List;

import cs.sci.ku.cookyalpha.dao.Ingredient;
import cs.sci.ku.cookyalpha.dao.Recipe;

/**
 * Created by MegapiesPT on 17/11/2560.
 */

public class RecipeEditorCarrier {
    private static RecipeEditorCarrier instance;
    private Recipe recipe;

    public static RecipeEditorCarrier getInstance(){
        if (instance == null)
            instance = new RecipeEditorCarrier();
        return instance;
    }

    public void init(){
        recipe = new Recipe();
    }

    public Recipe getRecipe(){
        return recipe;
    }
}
