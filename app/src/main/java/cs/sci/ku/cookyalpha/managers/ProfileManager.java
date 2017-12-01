package cs.sci.ku.cookyalpha.managers;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void loadUser(String uid, final OnResult<User> onResult){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user").child(uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("data snap shot", dataSnapshot + "");
//                User user = dataSnapshot.getValue(User.class);
//                onResult.onResult(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
