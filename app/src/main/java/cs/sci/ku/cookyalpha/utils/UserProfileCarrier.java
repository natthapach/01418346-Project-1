package cs.sci.ku.cookyalpha.utils;

import cs.sci.ku.cookyalpha.dao.User;

/**
 * Created by MegapiesPT on 29/11/2560.
 */

public class UserProfileCarrier {
    private static UserProfileCarrier instance;
    private User user;

    public UserProfileCarrier getInstance(){
        if (instance == null)
            instance = new UserProfileCarrier();
        return instance;
    }

    private UserProfileCarrier() {
        user = new User("user001", "uid001");
    }

    public static void setInstance(UserProfileCarrier instance) {
        UserProfileCarrier.instance = instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
