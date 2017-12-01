package cs.sci.ku.cookyalpha.utils;

import cs.sci.ku.cookyalpha.dao.User;

/**
 * Created by MegapiesPT on 29/11/2560.
 */

public class UserProfileCarrier {
    private static UserProfileCarrier instance;
    private User user;

    public static UserProfileCarrier getInstance(){
        if (instance == null)
            instance = new UserProfileCarrier();
        return instance;
    }

    private UserProfileCarrier() {
        user = new User("user001", "uid001");
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
