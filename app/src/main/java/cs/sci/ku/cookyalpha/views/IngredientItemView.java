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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.Ingredient;
import cs.sci.ku.cookyalpha.utils.Contextor;

/**
 * Created by MegapiesPT on 10/11/2560.
 */

public class IngredientItemView extends FrameLayout {
    private TextView nameTextView;
    private TextView amountTextView;
    private Ingredient ingredient;

    public IngredientItemView(@NonNull Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public IngredientItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public IngredientItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public IngredientItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }

    private void initInflate() {
        inflate(getContext(), R.layout.view_ingredient_item, this);
    }

    private void initInstance() {
        nameTextView = findViewById(R.id.tv_name);
        amountTextView = findViewById(R.id.tv_amt);
    }

    public void setIngredient(Ingredient ingredient){
        this.ingredient = ingredient;
        Log.d("nameTextView", nameTextView + "");
        nameTextView.setText(ingredient.name);
        amountTextView.setText(ingredient.amount);
    }

    public void enableTopMargin(){
        @SuppressLint("WrongViewCast") LinearLayout rootLayout = findViewById(R.id.root);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, Contextor.getInstance().getContext().getResources().getDimensionPixelSize(R.dimen.spacing), 0, 0);
        rootLayout.setLayoutParams(layoutParams);
    }

    public void disableTopMargin(){
        @SuppressLint("WrongViewCast") LinearLayout rootLayout = findViewById(R.id.root);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, 0);
        rootLayout.setLayoutParams(layoutParams);
    }

    public void enableBottomMargin(){
        LinearLayout rootLayout = findViewById(R.id.root);
        FrameLayout.LayoutParams layoutParams = new  FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, 16);
        rootLayout.setLayoutParams(layoutParams);
    }
    public void disableBottomMargin(){
        LinearLayout rootLayout = findViewById(R.id.root);
        FrameLayout.LayoutParams layoutParams = new  FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, 0);
        rootLayout.setLayoutParams(layoutParams);
    }
}
