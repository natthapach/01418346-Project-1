package cs.sci.ku.cookyalpha.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.List;

import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.utils.Contextor;
import cs.sci.ku.cookyalpha.views.RecipeItemView;

/**
 * Created by MegapiesPT on 12/12/2560.
 */

public class RecipesAdapter extends BaseAdapter {
    private List<Recipe> recipes;

    public RecipesAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = new RecipeItemView(Contextor.getInstance().getContext());
        RecipeItemView rview = (RecipeItemView) view;
        Recipe recipe = recipes.get(i);
        rview.setRecipe(recipe);

        if (i==0){
//                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(0, Contextor.getInstance().getContext().getResources().getDimensionPixelSize(R.dimen.spacing), 0, 0);
//                rview.setLayoutParams(layoutParams);
            rview.enableTopMargin();
        }
        return rview;
    }
}
