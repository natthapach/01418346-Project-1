package cs.sci.ku.cookyalpha.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.fragments.EditRecipePreviewFragment;
import cs.sci.ku.cookyalpha.fragments.GlobalRecipeListFragment;

/**
 * Created by MegapiesPT on 14/11/2560.
 */

public class EditRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_frame, EditRecipePreviewFragment.newInstance())
                    .commit();
        }
        initInstance();
    }

    private void initInstance() {

    }


}
