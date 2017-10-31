package cs.sci.ku.cookyalpha.views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import cs.sci.ku.cookyalpha.R;

/**
 * Created by MegapiesPT on 31/10/2560.
 */

public class RecipeItemView extends FrameLayout {
    public RecipeItemView(@NonNull Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public RecipeItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public RecipeItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RecipeItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }

    private void initInflate(){
        inflate(getContext(), R.layout.view_recipe_item, this);
    }

    private void initInstance(){

    }

}
