package cs.sci.ku.cookyalpha.activities;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.fragments.GlobalRecipeListFragment;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;
import cs.sci.ku.cookyalpha.managers.RecipeManager;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawetLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RecipeManager recipeManager = FirebaseRecipeManager.getInstance();
//
//        recipeManager.loadGlobalRecipe();
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                                        .add(R.id.container_frame, GlobalRecipeListFragment.newInstance())
                                        .commit();
        }
        initInstance();
    }

    private void initInstance() {
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        drawetLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawetLayout, R.string.open_drawer, R.string.close_drawer);
        drawetLayout.setDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }
}
