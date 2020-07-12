package com.souqmaftoh.basatashopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.Services.MyFirebaseMessagingService;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    CallbackManager mCallbackManager;
    private static final String TAG = "facebook";
    private FirebaseAuth mAuth;
    int RC_SIGN_IN=0;
    Button next,btnLogin;
    String facebookEmail,facebookToken,facebookName,facebookId;
    String device_id,push_token;

    TextView registration;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //device id
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if(device_id!=null)Log.e("device_id",device_id);

//device token


//        device_token=(String) ParseInstallation.getCurrentInstallation().get("deviceToken");
        push_token= MyFirebaseMessagingService.getToken(this);
        if(push_token!=null)Log.e("push_token",push_token);


        User user= SharedPrefManager.getInstance(this).getUser();
        if(user!=null) {
            if (user.getToken() != null && !user.getToken().isEmpty()) {
                Intent intent_log =new Intent(LoginActivity.this, MainActivity.class);
                intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent_log);

            }
        }


        registration=findViewById(R.id.txtV_registration);
        next=findViewById(R.id.next);
        btnLogin=findViewById(R.id.btn_login);


        registration.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        next.setOnClickListener(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Google Login button
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        String serverClientId = getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestIdToken(serverClientId)
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .build();
// Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.login_button_gmail);
        signInButton.setOnClickListener(this);


        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button_facebook);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });
// ...

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if ((currentUser != null)||(account != null) ){
//            updateUI();
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(token!=null){
                                Log.e("token",token.getToken());
                                facebookToken=token.getToken();
                                socialUserApi(facebookToken,"facebook",device_id,push_token);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void socialUserApi(String token, String type, String device_id, String push_token) {

        Call<Object> call= RetrofitClient.
                getInstance()
                .getApi()
                .social_user(token,type,device_id,push_token);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("gson:loginSocial", new Gson().toJson(response.body()) );

                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.e("res:loginSocial", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
                            if (message != null&&!message.isEmpty()) {
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (data != null) {
                                String token = data.getString("token");
                                String name = data.getString("name");
                                String email = data.getString("email");
                                String image = data.getString("image");
                                Boolean is_merchant = data.getBoolean("is_merchant");
                                String market_name = data.getString("market_name");
                                String address = data.getString("address");
                                String lat = data.getString("lat");
                                String lng = data.getString("lng");
                                String phone = data.getString("phone");
                                String description = data.getString("description");
                                JSONArray social_links = data.getJSONArray("social_links");

                                int length = social_links.length();
//                    ArrayList<Object>  socialLinks = new ArrayList<>();
                                for (int i = 0; i < length; i++) {
                                    JSONObject links = social_links.getJSONObject(i);
                                    String type = links.getString("type");
                                    String link = links.getString("link");
                                    Log.e("social_links", type + "  " + link);

                                }

                                User user = new User(token, name, email, image, is_merchant, market_name, address, lat, lng, phone, description);


                                SharedPrefManager.getInstance(LoginActivity.this)
                                        .saveUser(user);

                            }
                            Intent intent_log = new Intent(LoginActivity.this, MainActivity.class);
                            intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent_log);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                } else if (response.errorBody() != null) {
                    try {
                        Log.e("gson:loginSocialError", response.errorBody().string());
                        Toast.makeText(LoginActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }




            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("loginSocial:onFailure", String.valueOf(t));


            }
        });


    }





    private void updateUI() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_gmail:
                signIn();
                break;
            case R.id.next:
                Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.txtV_registration:
                Intent intent_reg =new Intent(LoginActivity.this, RegistrationActivityOne.class);
                startActivity(intent_reg);
                break;
            case R.id.btn_login:
                Intent intent_login =new Intent(LoginActivity.this, LoginByEmailActivity.class);
                startActivity(intent_login);
                break;


        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully.
            if(account!=null){
                String tk=account.getIdToken();


            if (tk != null) {
                Log.e("google_token",tk);
                socialUserApi(tk,"google",device_id,push_token);

            }

            }

//            Intent intent =new Intent(LoginActivity.this,RegistrationActivityOne.class);
//            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("google", "signInResult:failed code=" + e.getStatusCode());
        }
    }

}