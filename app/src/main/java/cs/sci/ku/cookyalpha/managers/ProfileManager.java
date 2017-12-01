package cs.sci.ku.cookyalpha.managers;

import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cs.sci.ku.cookyalpha.callbacks.OnResult;
import cs.sci.ku.cookyalpha.dao.User;

/**
 * Created by MegapiesPT on 30/11/2560.
 */

public class ProfileManager {
    private static ProfileManager instance;
    private Map<String, User> usersBuffer = new HashMap<>();

    public static ProfileManager getInstance(){
        if (instance == null)
            instance = new ProfileManager();
        return instance;
    }

    public void loadUser(final String uid, final OnResult<User> onResult){
        Log.d("loadUser", "uid=" + uid);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("data snapshot", dataSnapshot + "");
                if (dataSnapshot.getValue() == null){
                    uploadNewProfile(uid, new OnResult() {
                        @Override
                        public void onResult(Object obj) {
                            loadUser(uid, onResult);
                        }
                    });
                }else {
                    User user = dataSnapshot.getValue(User.class);
                    onResult.onResult(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("data error", databaseError + "");
            }
        });
    }

    private void uploadNewProfile(final String uid, final OnResult onResult){
        getFacebookProfile(new OnResult<User>() {
            @Override
            public void onResult(User obj) {
                obj.setId(uid);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child(uid);
                ref.setValue(obj)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                onResult.onResult(null);
                            }
                        });
            }
        });
    }

    private void getFacebookProfile(final OnResult<User> onResult){
        Log.d("get profile", "Token user id " + AccessToken.getCurrentAccessToken().getUserId());
        Bundle params = new Bundle();
        params.putString("fields", "id,name,email,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d("get profile", "profile response " + response);
                        try{
                            if(response != null){
                            /* handle the result */
                                JSONObject data = response.getJSONObject();
                                Log.d("get profile", "Json data " + data);
                                String profileUrl = null;
                                String name = null;
                                String email = null;
                                if(data.has("picture"))
                                    profileUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                if (data.has("name"))
                                    name = data.getString("name");
                                if (data.has("email"))
                                    email = data.getString("email");

                                User user = new User(name, null, profileUrl, email);
                                onResult.onResult(user);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();
    }

}
