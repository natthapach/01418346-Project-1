package cs.sci.ku.cookyalpha.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.RecipeProcedure;
import cs.sci.ku.cookyalpha.utils.Contextor;

/**
 * Created by MegapiesPT on 10/11/2560.
 */

public class ProcedurePropertyView extends FrameLayout {
    private ImageView imageImageView;
    private EditText descriptionEditText;
    private RecipeProcedure procedure;
    private Button selectButton;
    private OnClickSelectImage onClickSelectImageListener;

    public ProcedurePropertyView(@NonNull Context context) {
        super(context);
        initInflate();
        initInstance();
    }

    public ProcedurePropertyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstance();
    }

    public ProcedurePropertyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProcedurePropertyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstance();
    }

    private void initInflate(){
        inflate(getContext(), R.layout.view_procedure_property, this);
    }
    private void initInstance(){
        if (procedure == null)
            procedure = new RecipeProcedure();
        imageImageView = findViewById(R.id.iv_procedure);
        descriptionEditText = findViewById(R.id.et_description);
        selectButton = findViewById(R.id.btn_select);
        selectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSelectImageListener.perform();
            }
        });
    }

    public void setOnClickSelectImageListener(OnClickSelectImage listener){
        onClickSelectImageListener = listener;
    }
    public void setProcedure(RecipeProcedure procedure){
        this.procedure = procedure;
        if (procedure.imgUrl != null)
            Glide.with(getContext())
                .load(procedure.imgUrl)
                .apply(new RequestOptions().placeholder(R.drawable.placholder_w))
                .into(imageImageView);
        else if (procedure.datas != null)
            Glide.with(getContext())
                .load(procedure.datas)
                .apply(new RequestOptions().placeholder(R.drawable.placholder_w))
                .into(imageImageView);
        else if (procedure.imgUri != null)
            Glide.with(getContext())
                    .load(Uri.parse(procedure.imgUri))
                    .apply(new RequestOptions().placeholder(R.drawable.placholder_w))
                    .into(imageImageView);
        descriptionEditText.setText(procedure.description);
    }
    public RecipeProcedure getProcedure(){
        procedure.description = descriptionEditText.getText().toString();
        // TODO unuse datas
//        Bitmap bitmap = ((BitmapDrawable) imageImageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        procedure.datas = baos.toByteArray();
        return procedure;
    }
    public void enableTopMargin(){
        @SuppressLint("WrongViewCast") LinearLayout rootLayout = findViewById(R.id.root);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, Contextor.getInstance().getContext().getResources().getDimensionPixelSize(R.dimen.spacing), 0, 0);
        rootLayout.setLayoutParams(layoutParams);
    }

    public void setImageUri(Uri uri){
        imageImageView.setImageURI(uri);
        procedure.imgUri = uri.toString();
    }
    public interface OnClickSelectImage{
        void perform();
    }
}
