package cs.sci.ku.cookyalpha.fragments.recipe.editor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.Ingredient;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.utils.RecipeEditorCarrier;
import cs.sci.ku.cookyalpha.views.IngredientItemView;

/**
 * Created by MegapiesPT on 15/11/2560.
 */

public class EditRecipeIngredientsFragment extends Fragment implements OnConfirmEditor {

    private FloatingActionButton newButton;
    private ListView ingredientsListView;
    private ArrayList<Ingredient> ingredients;
    private BaseAdapter ingredientsAdapter;

    public static EditRecipeIngredientsFragment newInstance(){
        EditRecipeIngredientsFragment instance = new EditRecipeIngredientsFragment();

        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_ingredients, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        ingredients = new ArrayList<>();
        newButton = rootView.findViewById(R.id.btn_new);
        ingredientsListView = rootView.findViewById(R.id.lv_ingredients);

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View dialogView = getLayoutInflater().inflate(R.layout.view_ingredient_property, null);
                new AlertDialog.Builder(getContext())
                            .setView(dialogView)
                            .setPositiveButton(R.string.ingredient_submit, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    EditText nameEditText = dialogView.findViewById(R.id.et_name);
                                    EditText amtEditText = dialogView.findViewById(R.id.et_amt);
                                    Ingredient ingredient = new Ingredient(nameEditText.getText().toString(), amtEditText.getText().toString());
                                    ingredients.add(ingredient);
                                    ingredientsAdapter.notifyDataSetChanged();
                                }
                            })
                            .create()
                            .show();
            }
        });
        ingredientsAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return ingredients.size();
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
                    view = new IngredientItemView(getContext());
                IngredientItemView iview = (IngredientItemView) view;
                iview.setIngredient(ingredients.get(i));
                return iview;
            }
        };
        ingredientsListView.setAdapter(ingredientsAdapter);
        ingredientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,final int i, long l) {
                View dialog = getLayoutInflater().inflate(R.layout.view_ingredient_property, null);
                final EditText nameEditText = dialog.findViewById(R.id.et_name);
                final EditText amtEditText = dialog.findViewById(R.id.et_amt);
                final Ingredient ingredient = ingredients.get(i);
                nameEditText.setText(ingredient.name);
                amtEditText.setText(ingredient.amount);

                new AlertDialog.Builder(getContext())
                        .setView(dialog)
                        .setPositiveButton(R.string.ingredient_submit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                ingredients.get(i).name = nameEditText.getText().toString();
                                ingredients.get(i).amount = amtEditText.getText().toString();
                                ingredientsAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.ingredient_delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                ingredients.remove(i);
                                ingredientsAdapter.notifyDataSetChanged();
                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    public List<Ingredient> getIngredients(){
        return ingredients;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("ingredients", ingredients);
        setIngredientsToRecipe();
        super.onSaveInstanceState(outState);
    }

    private void setIngredientsToRecipe(){
        RecipeEditorCarrier.getInstance().getRecipe().setIngredientsList(this.ingredients);
    }

    @Override
    public void onConfirm() {
        setIngredientsToRecipe();
    }
}
