package cs.sci.ku.cookyalpha.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.managers.GlobalRecipeCollector;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

public class GlobalRecipeListFragment extends Fragment {


    private ListView recipeListView;

    public static GlobalRecipeListFragment newInstance(){
        GlobalRecipeListFragment instance = new GlobalRecipeListFragment();

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
        recipeListView.setAdapter(GlobalRecipeCollector.getInstance().getAdapter());
    }
}
