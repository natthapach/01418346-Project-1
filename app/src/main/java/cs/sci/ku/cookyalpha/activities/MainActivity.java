package cs.sci.ku.cookyalpha.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.callbacks.OnResult;
import cs.sci.ku.cookyalpha.callbacks.ProfileCarrierListener;
import cs.sci.ku.cookyalpha.dao.User;
import cs.sci.ku.cookyalpha.fragments.FollowingRecipeListFragment;
import cs.sci.ku.cookyalpha.fragments.GlobalRecipeListFragment;
import cs.sci.ku.cookyalpha.utils.UserProfileCarrier;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawetLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolBar;
    private LinearLayout createMenu;
    private ImageView profileImageView;
    private TextView userNameTextView;
    private TextView emailTextView;
    private ViewPager viewPager;
    private LinearLayout logOutMenu;
    private LinearLayout bookmarkMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RecipeManager recipeManager = FirebaseRecipeManager.getInstance();
//
//        recipeManager.loadGlobalRecipe();
//        if (savedInstanceState == null){
//            getSupportFragmentManager().beginTransaction()
//                                        .add(R.id.container_frame, GlobalRecipeListFragment.newInstance())
//                                        .commit();
//        }
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

        createMenu = findViewById(R.id.ll_menu_create);
        createMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawetLayout.closeDrawer(Gravity.START);
                Intent intent = new Intent(MainActivity.this, EditRecipeActivity.class);
                startActivity(intent);
            }
        });

        logOutMenu = findViewById(R.id.ll_menu_log_out);
        logOutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bookmarkMenu = findViewById(R.id.ll_menu_bookmark);
        bookmarkMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
                startActivity(intent);
            }
        });




        profileImageView = findViewById(R.id.iv_profile);
        userNameTextView = findViewById(R.id.tv_user_name);
        emailTextView = findViewById(R.id.tv_email);

        if (UserProfileCarrier.getInstance().getUser() != null)
            initUserProfileDrawer();
        else
            UserProfileCarrier.getInstance().setListener(new ProfileCarrierListener() {
                @Override
                public void onProfileChange() {
                    // **FIXED**
                    // Provide exception "you cannot start a load for a destroyed activity"
                    initUserProfileDrawer();
                }
            });

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                // TODO use string resource
                switch (position){
                    case 0:
                        return "Following";
                    case 1:
                        return "Global";
                    default:
                        return null;
                }
            }

            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return FollowingRecipeListFragment.newInstance();
                    case 1:
                        return GlobalRecipeListFragment.newInstance();
                    default:
                        return null;
                }
            }
        });
    }

    private void initUserProfileDrawer(){
        Log.d("MainActivity", "initUserProfile " + userNameTextView + " " + profileImageView);
        Log.d("MainActivity", "initUserProfile " + this);
        User user = UserProfileCarrier.getInstance().getUser();
        if (userNameTextView != null)
            userNameTextView.setText(user.getName());
        if (profileImageView != null)
            Glide.with(this)
                .load(user.getImgProfile())
                .apply(new RequestOptions().circleCrop())
                .into(profileImageView);
        if (emailTextView != null)
            emailTextView.setText(user.getEmail());
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserProfile();
            }
        });
        userNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserProfile();
            }
        });
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserProfile();
            }
        });
    }

    private void openUserProfile(){
        drawetLayout.closeDrawer(Gravity.START);
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
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

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("OnPause", "MainActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "MainActivity");
    }
}
