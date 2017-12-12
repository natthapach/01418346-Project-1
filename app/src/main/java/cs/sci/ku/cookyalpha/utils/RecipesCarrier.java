package cs.sci.ku.cookyalpha.utils;

import java.util.List;

import cs.sci.ku.cookyalpha.dao.Recipe;

/**
 * Created by MegapiesPT on 12/12/2560.
 */

public class RecipesCarrier {
    private static RecipesCarrier instance;
    private List<Recipe> recipes;

    public static RecipesCarrier getInstance() {
        if (instance == null)
            instance = new RecipesCarrier();
        return instance;
    }

    private RecipesCarrier() {

    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
