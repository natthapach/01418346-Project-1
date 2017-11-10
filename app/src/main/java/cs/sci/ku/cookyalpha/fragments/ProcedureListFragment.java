package cs.sci.ku.cookyalpha.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.dao.RecipeProcedure;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;
import cs.sci.ku.cookyalpha.utils.Contextor;
import cs.sci.ku.cookyalpha.views.IngredientItemView;
import cs.sci.ku.cookyalpha.views.ProcedureItemView;

/**
 * Created by MegapiesPT on 10/11/2560.
 */

public class ProcedureListFragment extends Fragment {

    private Recipe recipe;
    private ListView procedureListView;
    private ArrayList<RecipeProcedure> procedures;

    public static ProcedureListFragment newInstance(String recipeId){
        ProcedureListFragment instance = new ProcedureListFragment();
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
        Map<String, RecipeProcedure> sortedProcedure = new TreeMap<>(recipe.procedures);
        procedures = new ArrayList<>();
//        for (RecipeProcedure procedure : sortedProcedure.values())
//            procedures.add(procedure);
        procedures.addAll(sortedProcedure.values());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_procedure_list, container, false);
        initInstance(rootView);
        return  rootView;
    }

    private void initInstance(View rootView) {
        procedureListView = rootView.findViewById(R.id.lv_procedure);
        procedureListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return recipe.procedures.size();
            }

            @Override
            public Object getItem(int i) {
                return procedures.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null)
                    view = new ProcedureItemView(Contextor.getInstance().getContext());
                ProcedureItemView pview = (ProcedureItemView) view;
                pview.setProcedure(procedures.get(i));
                if (i == 0)
                    pview.enableTopMargin();
                return pview;
            }
        });
    }
}
