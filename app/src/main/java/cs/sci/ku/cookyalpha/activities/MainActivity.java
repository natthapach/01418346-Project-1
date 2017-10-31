package cs.sci.ku.cookyalpha.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;
import cs.sci.ku.cookyalpha.managers.RecipeManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeManager recipeManager = FirebaseRecipeManager.getInstance();

        recipeManager.loadGlobalRecipe();
    }
}
