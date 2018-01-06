package cs.sci.ku.cookyalpha.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.callbacks.OnResult;
import cs.sci.ku.cookyalpha.dao.Comment;
import cs.sci.ku.cookyalpha.dao.Ingredient;
import cs.sci.ku.cookyalpha.dao.User;
import cs.sci.ku.cookyalpha.managers.ProfileManager;
import cs.sci.ku.cookyalpha.utils.Contextor;


public class CommentItemView extends FrameLayout {
    private Comment comment;
    private ImageView profileImageView;
    private TextView userNameTextView;
    private TextView textTextView;

    public CommentItemView(@NonNull Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public CommentItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public CommentItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CommentItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }

    private void initInflate() {
        inflate(getContext(), R.layout.view_comment_item, this);
    }

    private void initInstance() {
        profileImageView = findViewById(R.id.iv_profile);
        userNameTextView = findViewById(R.id.tv_user_name);
        textTextView = findViewById(R.id.tv_text);
    }

    public void setComment(Comment comment){
        this.comment = comment;
        ProfileManager.getInstance()
                    .loadUser(comment.getUid(), new OnResult<User>() {
                        @Override
                        public void onResult(User obj) {
                            userNameTextView.setText(obj.getName());
                            Glide.with(getContext())
                                    .load(obj.getImgProfile())
                                    .apply(new RequestOptions().circleCrop())
                                    .into(profileImageView);
                        }
                    });
        textTextView.setText(comment.getText());
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
