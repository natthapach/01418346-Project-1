package cs.sci.ku.cookyalpha.activities;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.User;
import cs.sci.ku.cookyalpha.fragments.RecipeListFragment;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;
import cs.sci.ku.cookyalpha.utils.RecipesCarrier;
import cs.sci.ku.cookyalpha.utils.UserProfileCarrier;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView nameTextView;
    private Button followingButton;
    private Button followerButton;
    private FrameLayout containerFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_user_profile);

        initInstance();
        initData();
    }

    private void initData() {
        User user = UserProfileCarrier.getInstance().getUser();
        Glide.with(this)
                .load(user.getImgProfile())
                .apply(new RequestOptions().circleCrop())
                .into(profileImageView);
        nameTextView.setText(user.getName());
        RecipesCarrier.getInstance().setRecipes(FirebaseRecipeManager.getInstance().getUserRecipes(user.getId()));
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_frame, RecipeListFragment.newInstance())
                .commit();
    }

    private void initInstance() {
        profileImageView = findViewById(R.id.iv_profile);
        nameTextView = findViewById(R.id.tv_user_name);
        followingButton = findViewById(R.id.btn_following);
        followerButton = findViewById(R.id.btn_follower);
        containerFrame = findViewById(R.id.container_frame);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("onOptionItemSelected", R.id.menu_follow + "/" + item.getItemId());
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_other_user, menu);
//        MenuItem followItem = menu.findItem(R.id.menu_follow);
//        followItem.setIcon(getResources().getDrawable(R.drawable.like_icon));
        MenuItem followMenu = menu.findItem(R.id.menu_follow);
        View followLayout = followMenu.getActionView();
        CheckBox followCheckBox = followLayout.findViewById(R.id.cb_follow);
        followCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("Follow check change", b+"");
            }
        });
        return true;
    }
}
