package cs.sci.ku.cookyalpha.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.activities.RecipeActivity;
import cs.sci.ku.cookyalpha.adapters.RecipesAdapter;
import cs.sci.ku.cookyalpha.managers.GlobalRecipeCollector;
import cs.sci.ku.cookyalpha.utils.RecipesCarrier;
import cs.sci.ku.cookyalpha.views.RecipeItemView;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

public class RecipeListFragment extends Fragment {


    private ListView recipeListView;
    private RecipesAdapter adapter;

    public static RecipeListFragment newInstance(){
        RecipeListFragment instance = new RecipeListFragment();

        Bundle arg = new Bundle();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        recipeListView = rootView.findViewById(R.id.lv_recipes);
        adapter = new RecipesAdapter(RecipesCarrier.getInstance().getRecipes());
        recipeListView.setAdapter(adapter);
//        recipeListView.setAdapter(GlobalRecipeCollector.getInstance().getAdapter());
        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("onClickRecipe Item", view.toString());
                if (view instanceof RecipeItemView) {
                    RecipeItemView recipeItemView = (RecipeItemView) view;
                    String recipeId = recipeItemView.getRecipe().id;
                    Intent intent = new Intent(getContext(), RecipeActivity.class);
                    intent.putExtra("recipeId", recipeId);
                    startActivity(intent);
                }
            }
        });
    }
}
