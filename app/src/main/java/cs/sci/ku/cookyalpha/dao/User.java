package cs.sci.ku.cookyalpha.dao;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.Map;

/**
 * Created by MegapiesPT on 29/11/2560.
 */

@IgnoreExtraProperties
public class User {
    @PropertyName("name")       private String name;
    @PropertyName("id")         private String id;
    @PropertyName("imgProfile") private String imgProfile;
    @PropertyName("email")      private String email;
    @PropertyName("follower")   private Map<String, String> followers;
    @PropertyName("following")  private Map<String, String> followings;


    public User() {
    }

    public User(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public User(String name, String id, String imgProfile, String email, Map<String, String> followers, Map<String, String> followings) {
        this.name = name;
        this.id = id;
        this.imgProfile = imgProfile;
        this.email = email;
        this.followers = followers;
        this.followings = followings;
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

    @PropertyName("follower")
    public Map<String, String> getFollowers() {
        return followers;
    }

    @PropertyName("following")
    public Map<String, String> getFollowings() {
        return followings;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int countFollower(){
        if (followers == null)
            return 0;
        return followers.size();
    }

    public int countFollowing(){
        if (followings == null)
            return 0;
        return followings.size();
    }
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", imgProfile='" + imgProfile + '\'' +
                ", email='" + email + '\'' +
                ", followers=" + followers +
                ", followings=" + followings +
                '}';
    }
}
