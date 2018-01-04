package cs.sci.ku.cookyalpha.managers;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cs.sci.ku.cookyalpha.callbacks.OnResult;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.dao.User;
import cs.sci.ku.cookyalpha.utils.Contextor;
import cs.sci.ku.cookyalpha.utils.UserProfileCarrier;
import cs.sci.ku.cookyalpha.views.RecipeItemView;

public class FollowingRecipeCollector implements FirebaseRecipeManager.RecipeObserver{
    private static FollowingRecipeCollector instance;
    private List<Recipe> recipes;
    private FollowingRecipeAdapter adapter;

    public static FollowingRecipeCollector getInstance(){
        if (instance == null)
            instance = new FollowingRecipeCollector();
        return instance;
    }

    private FollowingRecipeCollector(){
        recipes = new ArrayList<>();
        List<Recipe> recipes = FirebaseRecipeManager.getInstance().addObserver(this);
        // TODO optimize with following user buffer
        for (final Recipe recipe : recipes){
            ProfileManager.getInstance()
                    .loadUser(recipe.getOwnerId(), new OnResult<User>() {
                        @Override
                        public void onResult(User owner) {
                            if (owner.isFollowBy(UserProfileCarrier.getInstance().getUser().getId())) {
                                Log.d("FollowingCollector", "add recipe " + recipe);
                                FollowingRecipeCollector.this.recipes.add(recipe);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

        adapter = new FollowingRecipeAdapter(this.recipes);
    }


    public BaseAdapter getAdapter(){
        return adapter;
    }

    @Override
    public void onRecipeAdd(final Recipe recipe) {
        Log.d("My App", "Following receive " + recipe);
        ProfileManager
            .getInstance()
            .loadUser(recipe.getOwnerId(), new OnResult<User>() {
                @Override
                public void onResult(User owner) {
                    if (owner.isFollowBy(UserProfileCarrier.getInstance().getUser().getId())){
                        recipes.add(recipe);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
    }

    @Override
    public void onRecipeChange(Recipe recipe) {
        for (int i=0; i<recipes.size(); i++)
            if (recipes.get(i).getId().equals(recipe.getId())){
                recipes.set(i, recipe);
                adapter.notifyDataSetChanged();
                break;
            }
    }

    @Override
    public void onRecipeRemove(Recipe recipe) {
        for (int i=0; i<recipes.size(); i++)
            if (recipes.get(i).getId().equals(recipe.getId())){
                recipes.remove(i);
                adapter.notifyDataSetChanged();
                break;
            }
    }

    private class FollowingRecipeAdapter extends BaseAdapter {
        private List<Recipe> recipes;

        public FollowingRecipeAdapter(List<Recipe> recipes) {
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
