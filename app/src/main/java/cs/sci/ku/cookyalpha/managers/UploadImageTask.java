package cs.sci.ku.cookyalpha.managers;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cs.sci.ku.cookyalpha.callbacks.ImageUrlSettable;
import cs.sci.ku.cookyalpha.callbacks.UploadImageCallback;
import cs.sci.ku.cookyalpha.callbacks.UploadRecipeCallback;

/**
 * Created by MegapiesPT on 30/11/2560.
 */

public class UploadImageTask {
    private Uri uri;
    private byte[] data;
    private String name;
    private ImageUrlSettable settable;

    public UploadImageTask(byte[] data, String name, ImageUrlSettable settable) {
        this.data = data;
        this.name = name;
        this.settable = settable;
    }

    public UploadImageTask(Uri uri, String name, ImageUrlSettable settable) {
        this.uri = uri;
        this.name = name;
        this.settable = settable;
    }

    public void upload(final UploadImageCallback callback){
        if (uri != null){
            uploadImage(uri, name, callback);
        }else {
            uploadImage(data, name, callback);
        }
    }

    public void uploadImage(byte[] data, String name, final UploadImageCallback callback){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child(name);
        UploadTask uploadTask = imgRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uri = taskSnapshot.getDownloadUrl();
                Log.d("upload complete", uri + "");
                settable.setImageUrl(uri + "");
                callback.onComplete();
            }
        });
    }
    public void uploadImage(Uri uri, String name, final UploadImageCallback callback){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child(name);
        UploadTask uploadTask = imgRef.putFile(uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailure();
            }
        })
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uri = taskSnapshot.getDownloadUrl();
                Log.d("upload complete", uri + "");
                settable.setImageUrl(uri + "");
                callback.onComplete();
            }
        });
    }
}
