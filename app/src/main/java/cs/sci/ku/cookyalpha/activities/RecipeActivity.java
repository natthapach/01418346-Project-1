package cs.sci.ku.cookyalpha.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.fragments.recipe.display.IngredientListFragment;
import cs.sci.ku.cookyalpha.fragments.recipe.display.PreviewRecipeFragment;
import cs.sci.ku.cookyalpha.fragments.recipe.display.ProcedureListFragment;

public class RecipeActivity extends AppCompatActivity {

    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        final String recipeId = intent.getStringExtra("recipeId");
//        if (savedInstanceState == null && recipeId != null){
//            getSupportFragmentManager().beginTransaction()
//                                        .add(R.id.container_frame, ProcedureListFragment.newInstance(recipeId))
//                                        .commit();
//        }
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                switch (position){
                    case 0 :
                        return "Preview";
                    case 1 :
                        return "Ingredients";
                    case 2 :
                        return "Procedure";
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0 :
                        return PreviewRecipeFragment.newInstance(recipeId);
                    case 1 :
                        return IngredientListFragment.newInstance(recipeId);
                    case 2 :
                        return ProcedureListFragment.newInstance(recipeId);
                    default:
                        return null;
                }
            }
        });
    }
}
