package cs.sci.ku.cookyalpha.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import cs.sci.ku.cookyalpha.callbacks.ProfileCarrierListener;
import cs.sci.ku.cookyalpha.dao.User;
import cs.sci.ku.cookyalpha.managers.ProfileManager;

/**
 * Created by MegapiesPT on 29/11/2560.
 */

public class UserProfileCarrier {
    private static UserProfileCarrier instance;
    private User user;
    private ProfileCarrierListener listener;

    public static UserProfileCarrier getInstance(){
        if (instance == null)
            instance = new UserProfileCarrier();
        return instance;
    }

    private UserProfileCarrier() {
//        user = new User("user001", "uid001");
    }



    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        Log.d("set user" , user + "");
        Log.d("set user" , listener + "");
        this.user = user;
        if (listener != null) {
            listener.onProfileChange();
            listener = null;
        }
    }

    public void setListener(ProfileCarrierListener listener) {
        this.listener = listener;
    }
}
