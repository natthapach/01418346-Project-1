package cs.sci.ku.cookyalpha.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.callbacks.UploadRecipeCallback;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.fragments.recipe.editor.EditRecipeIngredientsFragment;
import cs.sci.ku.cookyalpha.fragments.recipe.editor.EditRecipePreviewFragment;
import cs.sci.ku.cookyalpha.fragments.recipe.editor.EditRecipeProceduresFragment;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;
import cs.sci.ku.cookyalpha.utils.RecipeEditorCarrier;

/**
 * Created by MegapiesPT on 14/11/2560.
 */

public class EditRecipeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private EditRecipePreviewFragment editPreviewFragment;
    private EditRecipeIngredientsFragment editIngredientsFragment;
    private EditRecipeProceduresFragment editRecipeProceduresFragment;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

//        if (savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container_frame, EditRecipeIngredientsFragment.newInstance())
//                    .commit();
//        }
        initInstance();
    }

    private void initInstance() {
        editPreviewFragment = EditRecipePreviewFragment.newInstance();
        editIngredientsFragment = EditRecipeIngredientsFragment.newInstance();
        editRecipeProceduresFragment = EditRecipeProceduresFragment.newInstance();
        postButton = findViewById(R.id.btn_post);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(EditRecipeActivity.this, RecipeActivity.class);
//                intent.putExtra("recipe", RecipeEditorCarrier.getInstance().getRecipe());
//                startActivity(intent);
                editRecipeProceduresFragment.onConfirm();
                editIngredientsFragment.onConfirm();
                editPreviewFragment.onConfirm();
                Log.d("recipe", RecipeEditorCarrier.getInstance().getRecipe().toString());
                FirebaseRecipeManager.getInstance().uploadRecipe(RecipeEditorCarrier.getInstance().getRecipe(), new UploadRecipeCallback() {
                    @Override
                    public void onComplete(String recipeId) {
                        Toast.makeText(EditRecipeActivity.this, "Upload Recipe Complete", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                finish();
            }
        });

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0 :
                        return editPreviewFragment;
                    case 1 :
                        return editIngredientsFragment;
                    case 2 :
                        return editRecipeProceduresFragment;
                }
                return null;
            }
        });

        RecipeEditorCarrier.getInstance().init();
    }


}
