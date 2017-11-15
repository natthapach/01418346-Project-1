package cs.sci.ku.cookyalpha.fragments.recipe.editor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.managers.FirebaseRecipeManager;

/**
 * Created by MegapiesPT on 14/11/2560.
 */

public class EditRecipePreviewFragment extends Fragment {

    private EditText nameEditText;
    private EditText descriptionEditText;
    private Spinner categorySpinner;
    private Button selectImgButton;
    private int selectImageRequestCode = 100;
    private ImageView previewImageView;
    private Button uploadButton;

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
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {
        nameEditText = rootView.findViewById(R.id.et_name);
        descriptionEditText = rootView.findViewById(R.id.et_description);
        categorySpinner = rootView.findViewById(R.id.spin_category);
        selectImgButton = rootView.findViewById(R.id.btn_select);
        previewImageView = rootView.findViewById(R.id.iv_preview);
        uploadButton = rootView.findViewById(R.id.btn_up);

        categorySpinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, FirebaseRecipeManager.getInstance().getCategories()));
        selectImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, selectImageRequestCode);
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable) previewImageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();
                FirebaseRecipeManager.getInstance().uploadPreview(imageInByte);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == selectImageRequestCode && data != null){
            Uri uri = data.getData();
            previewImageView.setImageURI(uri);
        }
    }
}
