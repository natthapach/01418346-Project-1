package cs.sci.ku.cookyalpha.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


public class RatioImageView extends android.support.v7.widget.AppCompatImageView {
    public RatioImageView(Context context) {
        super(context);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int newHeight = width * 2 / 3;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(newHeight, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        setMeasuredDimension(width, newHeight);
    }
}
