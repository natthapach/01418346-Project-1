package cs.sci.ku.cookyalpha.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.activities.UserProfileActivity;
import cs.sci.ku.cookyalpha.dao.Ingredient;
import cs.sci.ku.cookyalpha.dao.User;
import cs.sci.ku.cookyalpha.managers.ProfileManager;
import cs.sci.ku.cookyalpha.utils.Contextor;
import cs.sci.ku.cookyalpha.utils.UserProfileCarrier;

public class UserItemView extends FrameLayout {
    public User user;
    private ImageView profileImageView;
    private TextView nameTextView;
    private CheckBox followCheckBox;


    public UserItemView(@NonNull Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public UserItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public UserItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public UserItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }

    private void initInflate() {
        inflate(getContext(), R.layout.view_user_item, this);
    }

    private void initInstance() {
        profileImageView = findViewById(R.id.iv_profile);
        nameTextView = findViewById(R.id.tv_user_name);
        followCheckBox = findViewById(R.id.cb_follow);
        nameTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserActivity();
            }
        });
        profileImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserActivity();
            }
        });
    }

    public void setUser(final User user){
        this.user = user;
        Glide.with(getContext())
                .load(user.getImgProfile())
                .apply(new RequestOptions().circleCrop())
                .into(profileImageView);

        nameTextView.setText(user.getName());
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
                                    UserItemView.this.user.getId()
                            );
                else
                    ProfileManager
                            .getInstance()
                            .unfollow(
                                    UserProfileCarrier.getInstance().getUser().getId(),
                                    UserItemView.this.user.getId()
                            );
            }
        });
        if (user.getId().equals(UserProfileCarrier.getInstance().getUser().getId()))
            followCheckBox.setVisibility(View.INVISIBLE);
    }

    private void openUserActivity(){
        Intent intent = new Intent(getContext(), UserProfileActivity.class);
        intent.putExtra("uid", UserItemView.this.user.getId());
        getContext().startActivity(intent);
    }

    public void enableTopMargin(){
        @SuppressLint("WrongViewCast") LinearLayout rootLayout = findViewById(R.id.root);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, Contextor.getInstance().getContext().getResources().getDimensionPixelSize(R.dimen.spacing), 0, 0);
        rootLayout.setLayoutParams(layoutParams);
    }

    public void disableTopMargin(){
        @SuppressLint("WrongViewCast") LinearLayout rootLayout = findViewById(R.id.root);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, 0);
        rootLayout.setLayoutParams(layoutParams);
    }

    public void enableBottomMargin(){
        LinearLayout rootLayout = findViewById(R.id.root);
        LayoutParams layoutParams = new  LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, 16);
        rootLayout.setLayoutParams(layoutParams);
    }
    public void disableBottomMargin(){
        LinearLayout rootLayout = findViewById(R.id.root);
        LayoutParams layoutParams = new  LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, 0);
        rootLayout.setLayoutParams(layoutParams);
    }
}
