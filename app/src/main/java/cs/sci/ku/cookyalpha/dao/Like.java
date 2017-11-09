package cs.sci.ku.cookyalpha.dao;

import com.google.firebase.database.PropertyName;

/**
 * Created by MegapiesPT on 9/11/2560.
 */

public class Like {
    @PropertyName("user-id")    public String userId;

    public Like() {}

    public Like(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Like("+userId+")";
    }
}
