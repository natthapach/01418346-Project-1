package cs.sci.ku.cookyalpha.managers;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.utils.Contextor;
import cs.sci.ku.cookyalpha.views.RecipeItemView;

/**
 * Created by MegapiesPT on 1/11/2560.
 */

public class GlobalRecipeCollector implements FirebaseRecipeManager.RecipeObserver{
    private static GlobalRecipeCollector instance;
    private List<Recipe> recipes;
    private GlobalRecipeAdapter adapter;

    public static GlobalRecipeCollector getInstance(){
        if (instance == null)
            instance = new GlobalRecipeCollector();
        return instance;
    }

    private GlobalRecipeCollector(){
        recipes = new ArrayList<>();
        List<Recipe> recipes = FirebaseRecipeManager.getInstance().addObserver(this);
        this.recipes.addAll(recipes);

        adapter = new GlobalRecipeAdapter(recipes);
    }

    public BaseAdapter getAdapter(){
        return adapter;
    }

    @Override
    public void onRecipeAdd(Recipe recipe) {
        Log.d("My App", "Global receive " + recipe);
        recipes.add(recipe);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRecipeChange(Recipe recipe) {

    }

    @Override
    public void onRecipeRemove(Recipe recipe) {

    }

    private class GlobalRecipeAdapter extends BaseAdapter {
        private List<Recipe> recipes;

        public GlobalRecipeAdapter(List<Recipe> recipes) {
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
}
