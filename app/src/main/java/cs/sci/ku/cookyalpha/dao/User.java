package cs.sci.ku.cookyalpha.dao;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

/**
 * Created by MegapiesPT on 29/11/2560.
 */

@IgnoreExtraProperties
public class User {
    @PropertyName("name")       private String name;
    @PropertyName("id")         private String id;
    @PropertyName("imgProfile") private String imgProfile;
    @PropertyName("email")      private String email;


    public User() {
    }

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public User(String name, String id, String imgProfile, String email) {
        this.name = name;
        this.id = id;
        this.imgProfile = imgProfile;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getImgProfile() {
        return imgProfile;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", imgProfile='" + imgProfile + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
