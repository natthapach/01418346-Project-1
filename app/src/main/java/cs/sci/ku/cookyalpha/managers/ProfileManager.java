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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cs.sci.ku.cookyalpha.callbacks.OnResult;
import cs.sci.ku.cookyalpha.dao.User;
import cs.sci.ku.cookyalpha.utils.UserProfileCarrier;

public class ProfileManager {
    private static ProfileManager instance;
    private final DatabaseReference ref;
    private Map<String, User> usersBuffer = new HashMap<>();
    private Map<String, ProfileListener> listeners = new HashMap<>();
    private Set<String> listened = new HashSet<>();
    private User currentUser;

    public static ProfileManager getInstance(){
        if (instance == null)
            instance = new ProfileManager();
        return instance;
    }

    public ProfileManager() {
        ref = FirebaseDatabase.getInstance().getReference("user");
    }

    public void loadUser(final String uid, final OnResult<User> onResult){
        if (usersBuffer.get(uid) != null){
            onResult.onResult(usersBuffer.get(uid));
            Log.d("ProfileManager", "reuse user");
            return;
        }
        Log.d("loadUser", "uid=" + uid);
        DatabaseReference uref = ref.child(uid);
        uref.addListenerForSingleValueEvent(new ValueEventListener() {
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
                    usersBuffer.put(user.getId(), user);
                    initUserListener(user.getId());
                    onResult.onResult(user);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("data error", databaseError + "");
            }
        });
    }

    private void initUserListener(String id) {
        if (listened.contains(id))
            return;
        Log.d("ProfileManager", "initUserListener " + id + " " + usersBuffer);
        listened.add(id);
        ref.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("ProfileManager", "dataChange " + dataSnapshot);
                User user = dataSnapshot.getValue(User.class);
                Log.d("ProfileManager", "user " + user);
                Log.d("ProfileManager", "listener = " + listeners.get(user.getId()));
                usersBuffer.put(user.getId(), user);
                if (listeners.get(user.getId()) != null)
                    listeners.get(user.getId()).onProfileChange(user);
                if (UserProfileCarrier.getInstance().getUser().getId().equals(user.getId()))
                    UserProfileCarrier.getInstance().setUser(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void uploadNewProfile(final String uid, final OnResult onResult){
        getFacebookProfile(new OnResult<User>() {
            @Override
            public void onResult(User obj) {
                obj.setId(uid);
                DatabaseReference uref = ref.child(uid);
                uref.setValue(obj)
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

                                User user = new User(name, null, profileUrl, email, null, null);
                                onResult.onResult(user);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();
    }

    public void follow(String followerId, String followedId){
        DatabaseReference followerRef = ref.child(followerId).child("following");
        followerRef.child(followedId).setValue(followedId);
        DatabaseReference followedRef = ref.child(followedId).child("follower");
        followedRef.child(followerId).setValue(followerId);
    }

    public void unfollow(String followerId, String followedId){
        DatabaseReference followerRef = ref.child(followerId).child("following");
        followerRef.child(followedId).setValue(null);
        DatabaseReference followedRef = ref.child(followedId).child("follower");
        followedRef.child(followerId).setValue(null);
    }

    public User getCurrentUser(){
        // TODO replace UserProfileCarrier with currentUser
        return currentUser;
    }

    public void loadCurrentUser(String uid){
        loadUser(uid, new OnResult<User>() {
            @Override
            public void onResult(User obj) {
                currentUser = obj;
            }
        });
    }

    public void regisListener(ProfileListener listener){
        listeners.put(listener.getIdListener(), listener);
    }
    public void removeListener(String id){
        listeners.remove(id);
    }
    public interface ProfileListener{
        String getIdListener();
        void onProfileChange(User user);
    }

}
