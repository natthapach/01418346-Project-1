package cs.sci.ku.cookyalpha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.fragments.IngredientListFragment;
import cs.sci.ku.cookyalpha.fragments.PreviewRecipeFragment;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        String recipeId = intent.getStringExtra("recipeId");
        if (savedInstanceState == null && recipeId != null){
            getSupportFragmentManager().beginTransaction()
                                        .add(R.id.container_frame, IngredientListFragment.newInstance(recipeId))
                                        .commit();
        }
    }
}
