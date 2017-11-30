package cs.sci.ku.cookyalpha.callbacks;

/**
 * Created by MegapiesPT on 29/11/2560.
 */

public interface UploadRecipeCallback {
    void onComplete(String recipeId);
    void onFailure();
}
