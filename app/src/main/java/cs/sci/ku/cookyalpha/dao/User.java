package cs.sci.ku.cookyalpha.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MegapiesPT on 29/11/2560.
 */

@IgnoreExtraProperties
public class User implements Parcelable{
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

    protected User(Parcel in) {
        name = in.readString();
        id = in.readString();
        imgProfile = in.readString();
        email = in.readString();

        followers = new HashMap<>();
        followings = new HashMap<>();

        in.readMap(followers, String.class.getClassLoader());
        in.readMap(followings, String.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Exclude
    public boolean isFollowBy(String uid){
        if (followers == null)
            return false;
        return followers.containsKey(uid);
    }
    @Exclude
    public boolean isFollowing(String uid){
        if (followings == null)
            return false;
        return followings.containsKey(uid);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
        parcel.writeString(imgProfile);
        parcel.writeString(email);

        parcel.writeMap(followers);
        parcel.writeMap(followings);
    }
}
