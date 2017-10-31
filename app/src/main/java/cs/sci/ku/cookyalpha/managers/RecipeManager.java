package cs.sci.ku.cookyalpha.managers;

import java.util.List;

import cs.sci.ku.cookyalpha.dao.Recipe;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

public interface RecipeManager {
    List<Recipe> loadGlobalRecipe();
}
