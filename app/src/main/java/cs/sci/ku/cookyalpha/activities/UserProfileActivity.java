package cs.sci.ku.cookyalpha.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.callbacks.OnResult;
import cs.sci.ku.cookyalpha.dao.User;
import cs.sci.ku.cookyalpha.fragments.RecipeListFragment;
import cs.sci.ku.cookyalpha.fragments.UserListActivity;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;
import cs.sci.ku.cookyalpha.managers.ProfileManager;
import cs.sci.ku.cookyalpha.utils.RecipesCarrier;
import cs.sci.ku.cookyalpha.utils.UserProfileCarrier;

public class UserProfileActivity extends AppCompatActivity {
    private static boolean isActive;
    private static boolean isChange;

    private ImageView profileImageView;
    private TextView nameTextView;
    private Button followingButton;
    private Button followerButton;
    private FrameLayout containerFrame;
    private User user;
    private ProfileManager.ProfileListener listener = new ProfileManager.ProfileListener() {
        @Override
        public String getIdListener() {
            return user.getId();
        }

        @Override
        public void onProfileChange(User user) {
            UserProfileActivity.this.user = user;
            if (isActive)
                initInfo();
            isChange = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_user_profile);

        initInstance();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isActive = true;
        initInfo();
        isChange = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActive = false;
    }

    private void initData() {
        Intent intent = getIntent();
        String uid = intent.getStringExtra("uid");
        if (uid == null || uid.equals(UserProfileCarrier.getInstance().getUser().getId())){
            this.user = UserProfileCarrier.getInstance().getUser();
            regisListener();
            initInfo();
        }else{
            ProfileManager.getInstance().loadUser(uid, new OnResult<User>() {
                @Override
                public void onResult(User user) {
                    UserProfileActivity.this.user = user;
                    regisListener();
                    initInfo();
                }
            });
        }
    }

    private void regisListener(){
        ProfileManager.getInstance().regisListener(listener);
    }

    private void initInfo(){
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
        String followerTxt = getResources().getString(R.string.follower);
        String followingTxt = getResources().getString(R.string.following);
        int followerAmt = user.countFollower();
        int followingAmt = user.countFollowing();
        followerButton.setText(followerAmt + " " + followerTxt);
        followingButton.setText(followingAmt + " " + followingTxt);

        followerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, UserListActivity.class);
                String[] ids = (user.getFollowers()==null)?(new String[0]):user.getFollowers().values().toArray(new String[0]);
                intent.putExtra("userIds", ids);
                startActivity(intent);
            }
        });
        followingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, UserListActivity.class);
                String[] ids = (user.getFollowings()==null)?(new String[0]):user.getFollowings().values().toArray(new String[0]);
                intent.putExtra("userIds", ids);
                startActivity(intent);
            }
        });
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
        if (user.getId().equals(UserProfileCarrier.getInstance().getUser().getId()))
            return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_other_user, menu);
//        MenuItem followItem = menu.findItem(R.id.menu_follow);
//        followItem.setIcon(getResources().getDrawable(R.drawable.like_icon));
        MenuItem followMenu = menu.findItem(R.id.menu_follow);
        View followLayout = followMenu.getActionView();
        CheckBox followCheckBox = followLayout.findViewById(R.id.cb_follow);
        followCheckBox.setChecked(user.isFollowBy(UserProfileCarrier.getInstance().getUser().getId()));
        followCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("Follow check change", b+"");
                if (b)
                    ProfileManager
                            .getInstance()
                            .follow(
                                    UserProfileCarrier.getInstance().getUser().getId(),
                                    user.getId()
                            );
                else
                    ProfileManager
                            .getInstance()
                            .unfollow(
                                    UserProfileCarrier.getInstance().getUser().getId(),
                                    user.getId()
                            );

            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (!user.getId().equals(UserProfileCarrier.getInstance().getUser().getId()))
        ProfileManager.getInstance().removeListener(user.getId());
    }
}
