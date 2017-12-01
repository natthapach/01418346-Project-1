package cs.sci.ku.cookyalpha.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import cs.sci.ku.cookyalpha.R;
import cs.sci.ku.cookyalpha.callbacks.OnResult;
import cs.sci.ku.cookyalpha.dao.User;
import cs.sci.ku.cookyalpha.managers.ProfileManager;
import cs.sci.ku.cookyalpha.utils.UserProfileCarrier;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private CallbackManager callBackManager;
    private LoginButton facebookLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("Access token", AccessToken.getCurrentAccessToken() + "");
        Log.d("Current Profile", Profile.getCurrentProfile() + "");

        auth = FirebaseAuth.getInstance();
        if (AccessToken.getCurrentAccessToken() != null){
            handleFacebookAccessToken(AccessToken.getCurrentAccessToken());
        }
        initInstance();
    }

    private void initInstance() {



        callBackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callBackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
        facebookLoginBtn = findViewById(R.id.btn_facebook_login);
        facebookLoginBtn.setReadPermissions("email", "public_profile");
        facebookLoginBtn.registerCallback(callBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                facebookLoginBtn.setClickable(false);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                // TODO handle cannot login
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d("handle token", "AccessToken " + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        Log.d("credential", credential + "");
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            Log.d("sign in complete", "uid=" + user.getUid());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            loadProfile();
//                            DatabaseManager.getInstance().init();
//                            getProfile();
//
//                            gotoMainActivity();
                        }else{
                            Log.d("sign in", "failure");
                            Log.wtf("Link-Note", "sign in failure", task.getException());
                        }
                    }
                });
    }

    private void loadProfile(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getUid();
        ProfileManager.getInstance().loadUser(uid, new OnResult<User>() {
            @Override
            public void onResult(User obj) {
                UserProfileCarrier carrier = UserProfileCarrier.getInstance();
                carrier.setUser(obj);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callBackManager.onActivityResult(requestCode, resultCode, data);
    }

}
