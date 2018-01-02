package cs.sci.ku.cookyalpha.fragments.recipe.editor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.dao.Ingredient;
import cs.sci.ku.cookyalpha.dao.RecipeProcedure;
import cs.sci.ku.cookyalpha.utils.RecipeEditorCarrier;
import cs.sci.ku.cookyalpha.views.IngredientItemView;
import cs.sci.ku.cookyalpha.views.ProcedureItemView;
import cs.sci.ku.cookyalpha.views.ProcedurePropertyView;

public class EditRecipeProceduresFragment extends Fragment implements OnConfirmEditor{

    private FloatingActionButton newButton;
    private ListView proceduresListView;
    private ArrayList<RecipeProcedure> procedures;
    private BaseAdapter proceduresAdapter;
    private ProcedurePropertyView procedurePropertyView;
    private int selectImageRequestCode = 100;

    public static EditRecipeProceduresFragment newInstance(){
        EditRecipeProceduresFragment instance = new EditRecipeProceduresFragment();

        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_proceures, container, false);
        if (savedInstanceState != null)
            procedures = savedInstanceState.getParcelableArrayList("procedures");
        else {
            procedures = RecipeEditorCarrier.getInstance().getRecipe().getProceduresList();
            Log.d("EditRecipeProcedures", "procedure from carrier "+procedures);
        }
        initInstance(rootView);
        Log.d("edit procedure fr", "onCreateView");
        return rootView;
    }

    private void initInstance(View rootView) {
        if (procedures == null)
            procedures = new ArrayList<>();
        proceduresListView = rootView.findViewById(R.id.lv_procedures);

        newButton = rootView.findViewById(R.id.btn_new);

        proceduresAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return procedures.size();
            }

            @Override
            public Object getItem(int i) {
                return procedures.get(i);
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null)
                    view = new ProcedureItemView(getContext());
                ProcedureItemView pview = (ProcedureItemView) view;
                pview.setProcedure(procedures.get(i));
                return pview;
            }
        };
        proceduresListView.setAdapter(proceduresAdapter);
        proceduresListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Log.d("EditRecipeProcedures", "onProcedure selected i="+i);
                procedurePropertyView = new ProcedurePropertyView(getContext());
                procedurePropertyView.setProcedure(procedures.get(i));
                procedurePropertyView.setOnClickSelectImageListener(new ProcedurePropertyView.OnClickSelectImage() {
                    @Override
                    public void perform() {
                        onClickSelectButton();
                    }
                });
                // TODO use string resource for procedure (not for ingredient)
                new AlertDialog.Builder(getContext())
                        .setView(procedurePropertyView)
                        .setPositiveButton(R.string.ingredient_submit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                procedures.set(i, procedurePropertyView.getProcedure());
                                proceduresAdapter.notifyDataSetChanged();
                                Log.d("procedures size", procedures.size() + "");
                                Log.d("procedues", procedures.toString());
                            }
                        })
                        .setNegativeButton(R.string.ingredient_delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                procedures.remove(i);
                                proceduresAdapter.notifyDataSetChanged();
                            }
                        })
                        .create()
                        .show();
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                procedurePropertyView = new ProcedurePropertyView(getContext());
                procedurePropertyView.setOnClickSelectImageListener(new ProcedurePropertyView.OnClickSelectImage() {
                    @Override
                    public void perform() {
                        onClickSelectButton();
                    }
                });
                new AlertDialog.Builder(getContext())
                            .setView(procedurePropertyView)
                            .setPositiveButton(R.string.ingredient_submit, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                        procedures.add(procedurePropertyView.getProcedure());
                                        proceduresAdapter.notifyDataSetChanged();
                                        Log.d("procedures size", procedures.size() + "");
                                        Log.d("procedues", procedures.toString());
                                }
                            })
                            .create()
                            .show();
            }
        });
    }

    private void onClickSelectButton(){
        Log.d("onClick", "select procedure image");
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, selectImageRequestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == selectImageRequestCode && data != null){
            procedurePropertyView.setImageUri(data.getData());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("procedures", procedures);
        setProceduresToRecipe();
        super.onSaveInstanceState(outState);
        Log.d("edit procedure fr", "onSaveInstanceStage");
    }

    public List<RecipeProcedure> getProcedures(){
        return procedures;
    }

    private void setProceduresToRecipe(){
        RecipeEditorCarrier.getInstance().getRecipe().setProceduresList(procedures);
    }

    @Override
    public void onConfirm() {
        setProceduresToRecipe();
    }
}
