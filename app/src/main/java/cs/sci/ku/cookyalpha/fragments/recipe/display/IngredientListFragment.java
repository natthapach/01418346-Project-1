package cs.sci.ku.cookyalpha.fragments.recipe.display;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.Ingredient;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;
import cs.sci.ku.cookyalpha.utils.Contextor;
import cs.sci.ku.cookyalpha.views.IngredientItemView;

/**
 * Created by MegapiesPT on 10/11/2560.
 */

public class IngredientListFragment extends Fragment {

    private Recipe recipe;
    private ListView ingredientListView;
    private ArrayList<Ingredient> ingredients;

    public static IngredientListFragment newInstance(String recipeId){
        IngredientListFragment instance = new IngredientListFragment();
        /*
            set parameter to argument
         */
        Bundle bundle = new Bundle();
        // put parameter to bundle
        bundle.putString("recipeId", recipeId);
        // set bundle to argument
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
            get value from argument
         */
        String recipeId = getArguments().getString("recipeId");
        recipe = FirebaseRecipeManager.getInstance().getRecipe(recipeId);
        ingredients = new ArrayList<>(recipe.getIngredients().values());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        initInstance(rootView);
        return  rootView;
    }

    private void initInstance(View rootView) {
        ingredientListView = rootView.findViewById(R.id.lv_ingredients);
        ingredientListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return recipe.getIngredients().size();
            }

            @Override
            public Object getItem(int i) {
                return ingredients.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null)
                    view = new IngredientItemView(Contextor.getInstance().getContext());
                IngredientItemView iview = (IngredientItemView) view;
                iview.setIngredient(ingredients.get(i));

                iview.disableTopMargin();
                iview.disableBottomMargin();
                if (i == 0)
                    iview.enableTopMargin();
                else if (i == getCount()-1)
                    iview.enableBottomMargin();
                return iview;
            }
        });
    }
}
