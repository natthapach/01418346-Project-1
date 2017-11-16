package cs.sci.ku.cookyalpha.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.fragments.recipe.editor.EditRecipeIngredientsFragment;
import cs.sci.ku.cookyalpha.fragments.recipe.editor.EditRecipePreviewFragment;
import cs.sci.ku.cookyalpha.fragments.recipe.editor.EditRecipeProceduresFragment;

/**
 * Created by MegapiesPT on 14/11/2560.
 */

public class EditRecipeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private EditRecipePreviewFragment editPreviewFragment;
    private EditRecipeIngredientsFragment editIngredientsFragment;
    private EditRecipeProceduresFragment editRecipeProceduresFragment;

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
    }


}
