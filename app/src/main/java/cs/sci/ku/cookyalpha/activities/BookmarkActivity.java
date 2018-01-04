package cs.sci.ku.cookyalpha.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;
import cs.sci.ku.cookyalpha.views.RecipeItemView;

public class BookmarkActivity extends AppCompatActivity {

    private ListView recipesListView;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe_list);

        initInstance();
    }

    private void initInstance() {
        recipesListView = findViewById(R.id.lv_recipes);
        adapter = new BaseAdapter(){

            @Override
            public int getCount() {
                return FirebaseRecipeManager.getInstance().getBookmarkIds().size();
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
                    view = new RecipeItemView(BookmarkActivity.this);
                RecipeItemView rview = (RecipeItemView) view;
                Recipe recipe = FirebaseRecipeManager
                                    .getInstance()
                                    .getRecipe(
                                            FirebaseRecipeManager
                                                .getInstance()
                                                .getBookmarkIds()
                                                .get(i)
                                    );
                rview.setRecipe(recipe);
                return rview;
            }
        };
        recipesListView.setAdapter(adapter);
    }
}
