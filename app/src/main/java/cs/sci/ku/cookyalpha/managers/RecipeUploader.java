package cs.sci.ku.cookyalpha.managers;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cs.sci.ku.cookyalpha.callbacks.UploadImageCallback;
import cs.sci.ku.cookyalpha.callbacks.UploadRecipeCallback;
import cs.sci.ku.cookyalpha.dao.Recipe;
import cs.sci.ku.cookyalpha.dao.RecipeProcedure;
import cs.sci.ku.cookyalpha.utils.UserProfileCarrier;


public class RecipeUploader {

    private LinkedList<UploadImageTask> taskQueue = new LinkedList<>();
    private int processCount = 0;
    private int completeCount = 0;
    private int failureCount = 0;
    private Recipe recipe;
    private OnFailureListener onFailureListener;
    private OnCompleteListener onCompleteListener;
    private UploadRecipeCallback callback;

    public RecipeUploader(Recipe recipe, UploadRecipeCallback callback) {
        this.recipe = recipe;
        this.callback = callback;

    }

    public void upload(){
        DatabaseReference rref = null;
        if (recipe.getId() == null) {
            rref = FirebaseDatabase.getInstance().getReference("recipe").push();
            String id = rref.getKey();
            recipe.setId(id);
        }


        recipe.setOwnerId(UserProfileCarrier.getInstance().getUser().getId());

        if (recipe.getPreview().getUri() != null){
            UploadImageTask task = new UploadImageTask(recipe.getPreview().getUri(), "recipe/"+recipe.getId()+"-preview.jpg", recipe.getPreview());
            taskQueue.add(task);
        }else if (recipe.getPreview().getDatas() != null){
            UploadImageTask task = new UploadImageTask(recipe.getPreview().getDatas(), "recipe/"+recipe.getId()+"-preview.jpg", recipe.getPreview());
            taskQueue.add(task);
        }

        for (Map.Entry<String, RecipeProcedure> entry: recipe.getProcedures().entrySet()){
            RecipeProcedure procedure = entry.getValue();
            if (procedure.getDatas() != null){
                UploadImageTask task = new UploadImageTask(procedure.getDatas(), "recipe/"+recipe.getId()+"-procedure-"+entry.getKey()+".jpg", procedure);
                taskQueue.add(task);
            }
            else if (procedure.getImgUri() != null)
            {
                UploadImageTask task = new UploadImageTask(Uri.parse(procedure.getImgUri()), "recipe/"+recipe.getId()+"-procedure-"+entry.getKey()+".jpg", procedure);
                taskQueue.add(task);
            }
        }
        nextTask();

    }

    private void nextTask(){
        if (taskQueue.size() > 0){
            UploadImageTask task = taskQueue.removeFirst();
            task.upload(new UploadImageCallback() {
                @Override
                public void onComplete() {
                    nextTask();
                }

                @Override
                public void onFailure() {
                    nextTask();
                }
            });
        } else{
//            callback.onComplete(recipe.id);

            uploadData();
        }
    }

    private void uploadData(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("recipe").child(recipe.getId());
        ref.setValue(recipe)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onComplete(recipe.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure();
                    }
                });
    }
}
