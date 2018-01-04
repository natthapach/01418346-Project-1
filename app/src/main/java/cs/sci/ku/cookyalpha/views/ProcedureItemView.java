package cs.sci.ku.cookyalpha.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.RecipeProcedure;
import cs.sci.ku.cookyalpha.utils.Contextor;

/**
 * Created by MegapiesPT on 10/11/2560.
 */

public class ProcedureItemView extends FrameLayout {
    private ImageView imageImageView;
    private TextView descriptionTextView;
    private RecipeProcedure procedure;

    public ProcedureItemView(@NonNull Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public ProcedureItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public ProcedureItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProcedureItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }

    private void initInflate(){
        inflate(getContext(), R.layout.view_procedure_item, this);
    }
    private void initInstance(){
        imageImageView = findViewById(R.id.iv_procedure);
        descriptionTextView = findViewById(R.id.tv_description);
    }

    public void setProcedure(RecipeProcedure procedure){
        this.procedure = procedure;
        descriptionTextView.setText(procedure.getDescription());
        //TODO unuse datas
        if (procedure.getDatas() != null)
            Glide.with(getContext())
                    .asBitmap()
                    .load(procedure.getDatas())
                    .apply(new RequestOptions().centerCrop())
                    .apply(new RequestOptions().placeholder(R.drawable.placholder_w).centerCrop())
                    .into(imageImageView);
        else if (procedure.getImgUri() != null)
            Glide.with(getContext())
                    .load(Uri.parse(procedure.getImgUri()))
                    .apply(new RequestOptions().centerCrop())
                    .apply(new RequestOptions().placeholder(R.drawable.placholder_w).centerCrop())
                    .into(imageImageView);
        else if (procedure.getImgUrl() != null)
            Glide.with(getContext())
                    .load(procedure.getImgUrl())
                    .apply(new RequestOptions().centerCrop())
                    .apply(new RequestOptions().placeholder(R.drawable.placholder_w).centerCrop())
                    .into(imageImageView);



    }
    public void enableTopMargin(){
        @SuppressLint("WrongViewCast") LinearLayout rootLayout = findViewById(R.id.root);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, Contextor.getInstance().getContext().getResources().getDimensionPixelSize(R.dimen.spacing), 0, 0);
        rootLayout.setLayoutParams(layoutParams);
    }
}
