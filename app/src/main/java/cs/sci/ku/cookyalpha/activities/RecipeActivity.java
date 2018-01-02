package cs.sci.ku.cookyalpha.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.fragments.recipe.display.IngredientListFragment;
import cs.sci.ku.cookyalpha.fragments.recipe.display.PreviewRecipeFragment;
import cs.sci.ku.cookyalpha.fragments.recipe.display.ProcedureListFragment;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;
import cs.sci.ku.cookyalpha.utils.UserProfileCarrier;

public class RecipeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private String recipeId;
    private Recipe recipe;
    private CheckBox likeCheckBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        recipeId = intent.getStringExtra("recipeId");
//        if (savedInstanceState == null && recipeId != null){
//            getSupportFragmentManager().beginTransaction()
//                                        .add(R.id.container_frame, ProcedureListFragment.newInstance(recipeId))
//                                        .commit();
//        }
        initInstance();
    }

    private void initInstance() {
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                // TODO use string resource
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
        likeCheckBox = findViewById(R.id.cb_like);
        recipe = FirebaseRecipeManager.getInstance().getRecipe(recipeId);
        Log.d("isLike", recipe.isLike(UserProfileCarrier.getInstance().getUser().getId()) + "");
        likeCheckBox.setChecked(recipe.isLike(UserProfileCarrier.getInstance().getUser().getId()));
//        likeCheckBox.setChecked(true);
        Log.d("isChecked", likeCheckBox.isChecked() + "");
        likeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("onCheckedChanged", b + " ");
                Log.d("isChecked", likeCheckBox.isChecked() + "");
                if (b)
                    FirebaseRecipeManager.getInstance().likeRecipe(recipe);
                else
                    FirebaseRecipeManager.getInstance().unlikeRecipe(recipe);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (UserProfileCarrier.getInstance().getUser().getId().equals(recipe.getOwnerId())){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_owner_recipe, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                Intent intent = new Intent(this, EditRecipeActivity.class);
                intent.putExtra("recipe", recipe);
                startActivity(intent);
                return true;
            case R.id.menu_delete:
                FirebaseRecipeManager.getInstance().deleteRecipe(recipe.id);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
