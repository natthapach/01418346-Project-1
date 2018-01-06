package cs.sci.ku.cookyalpha.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.Comment;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;
import cs.sci.ku.cookyalpha.utils.UserProfileCarrier;
import cs.sci.ku.cookyalpha.views.CommentItemView;

public class CommentActivity extends AppCompatActivity implements FirebaseRecipeManager.RecipeObserver {

    private ListView commentsListView;
    private EditText commentEditText;
    private Button sendButton;
    private String rid;
    private Recipe recipe;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecipeManager.getInstance().addObserver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseRecipeManager.getInstance().removeObserver(this);
    }

    private void initInstance() {
        commentsListView = findViewById(R.id.lv_comments);
        commentEditText = findViewById(R.id.et_comment);
        sendButton = findViewById(R.id.btn_send);

        rid = getIntent().getStringExtra("rid");
        recipe = FirebaseRecipeManager.getInstance().getRecipe(rid);

        adapter = new BaseAdapter(){

            @Override
            public int getCount() {
                return recipe.getComments().size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null)
                    view = new CommentItemView(CommentActivity.this);
                CommentItemView cview = (CommentItemView) view;
                String key = recipe.getComments().keySet().toArray(new String[0])[i];
                Comment comment = recipe.getComments().get(key);
                cview.setComment(comment);
                return cview;
            }
        };

        commentsListView.setAdapter(adapter);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!commentEditText.getText().toString().equals("")){
                    Comment comment = new Comment(
                            commentEditText.getText().toString(),
                            UserProfileCarrier.getInstance()
                                    .getUser()
                                    .getId()
                    );
                    FirebaseRecipeManager.getInstance().addComment(recipe.getId(), comment);
                    commentEditText.setText("");
                }
            }
        });
    }

    @Override
    public void onRecipeAdd(Recipe recipe) {

    }

    @Override
    public void onRecipeChange(Recipe recipe) {
        if (recipe.getId().equals(this.recipe.getId())) {
            this.recipe = recipe;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRecipeRemove(Recipe recipe) {

    }
}
