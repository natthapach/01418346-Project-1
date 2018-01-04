package cs.sci.ku.cookyalpha.fragments.recipe.editor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.RecipePreview;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;
import cs.sci.ku.cookyalpha.utils.RecipeEditorCarrier;

/**
 * Created by MegapiesPT on 14/11/2560.
 */

public class EditRecipePreviewFragment extends Fragment implements OnConfirmEditor {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private Spinner categorySpinner;
    private Button selectImgButton;
    private int selectImageRequestCode = 100;
    private ImageView previewImageView;
    private Button uploadButton;
    private RecipePreview preview;
    private LinearLayout imageContainer;

    public static EditRecipePreviewFragment newInstance(){
        EditRecipePreviewFragment instance = new EditRecipePreviewFragment();

        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_preview, container, false);
        if (savedInstanceState != null) {
            Log.d("onCreateView", "preview" + savedInstanceState.getParcelable("preview"));
            preview = savedInstanceState.getParcelable("preview");
            Log.d("preview datas", new ArrayList<>(Arrays.asList(preview.getDatas())).toString());
        }else{
            preview = RecipeEditorCarrier.getInstance().getRecipe().getPreview();
        }
        initInstance(rootView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (preview.getDatas() != null)
            Glide.with(getContext()).load(preview.getDatas()).into(previewImageView);
        else if (preview.getUri() != null)
            previewImageView.setImageURI(preview.getUri());
        else if (preview.getImgUrl() != null)
            Glide.with(getContext()).load(preview.getImgUrl()).into(previewImageView);

        nameEditText.setText(RecipeEditorCarrier.getInstance().getRecipe().getName());
        descriptionEditText.setText(RecipeEditorCarrier.getInstance().getRecipe().getDescription());
    }

    private void initInstance(View rootView) {
        if (preview == null) {
            preview = new RecipePreview();
        }

        nameEditText = rootView.findViewById(R.id.et_name);
        descriptionEditText = rootView.findViewById(R.id.et_description);
        categorySpinner = rootView.findViewById(R.id.spin_category);
        selectImgButton = rootView.findViewById(R.id.btn_select);
        previewImageView = rootView.findViewById(R.id.iv_preview);

        categorySpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, FirebaseRecipeManager.getInstance().getCategories()));
        selectImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, selectImageRequestCode);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == selectImageRequestCode && data != null){
            Uri uri = data.getData();
            previewImageView.setImageURI(uri);
            preview.setUri(uri);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("preview", preview);
        setPreviewToRecipe();
        super.onSaveInstanceState(outState);
    }

    public RecipePreview getPreview(){
        return preview;
    }

    private void setPreviewToRecipe(){
        RecipeEditorCarrier.getInstance().getRecipe().setPreview(preview);
        RecipeEditorCarrier.getInstance().getRecipe().setName(nameEditText.getText().toString());
        RecipeEditorCarrier.getInstance().getRecipe().setDescription(descriptionEditText.getText().toString());
    }

    @Override
    public void onConfirm() {
        setPreviewToRecipe();
    }
}
