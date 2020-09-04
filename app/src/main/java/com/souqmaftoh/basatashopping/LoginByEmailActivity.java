package com.souqmaftoh.basatashopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Fonts.LatoBLack;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.Models.ForgetPassResponse;
import com.souqmaftoh.basatashopping.Services.MyFirebaseMessagingService;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginByEmailActivity extends AppCompatActivity implements View.OnClickListener {

    LatoBLack txtV_Register;
    MaterialButton btn_loginByEmail;
    EditText et_login_email, et_login_pass;
    LatoBLack txtV_Forgot;
    String device_id,push_token;
    String email,password;
    Bitmap bitmap;
    String encodedImage;
    String facebookUrl,instagramUrl,youtubeUrl;
    private FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        et_login_email = findViewById(R.id.et_login_email);
        et_login_pass = findViewById(R.id.et_login_pass);
        txtV_Register = findViewById(R.id.txtV_Register);
        btn_loginByEmail = findViewById(R.id.btn_loginByEmail);
        txtV_Forgot=findViewById(R.id.txtV_Forgot);
//device id
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if(device_id!=null)Log.e("device_id",device_id);

//device token


//        device_token=(String) ParseInstallation.getCurrentInstallation().get("deviceToken");
        push_token= MyFirebaseMessagingService.getToken(this);
        if(push_token!=null)Log.e("push_token",push_token);

        txtV_Register.setOnClickListener(this);
        btn_loginByEmail.setOnClickListener(this);
        txtV_Forgot.setOnClickListener(this);


        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            email = bundle.getString("email");
            if(email!=null) {
                Log.e("email_login", email);
                et_login_email.setText(email);
                et_login_email.setEnabled(false);
            }
            password = bundle.getString("password");
            if(password!=null) {
                Log.e("password_login", password);
                et_login_pass.setText(password);
                et_login_pass.setEnabled(false);

            }
            facebookUrl = bundle.getString("facebook");
            instagramUrl = bundle.getString("instagram");
            youtubeUrl = bundle.getString("youtube");
//          if(facebookUrl!=null&&!facebookUrl.isEmpty()){
//              AddSocialMediaLinks("facebook",facebookUrl);
//          }
//          if(instagramUrl!=null&&!instagramUrl.isEmpty()){
//              AddSocialMediaLinks("instagram",instagramUrl);
//
//          }
//          if(youtubeUrl!=null&&!youtubeUrl.isEmpty()){
//              AddSocialMediaLinks("youtube",youtubeUrl);
//
//          }
            bitmap = (Bitmap) getIntent().getParcelableExtra("bitmap");
        }

        if(bitmap!=null){
            Encode(bitmap);

        }
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    private void handleCustomAccessToken(String mCustomToken) {

        mAuth.signInWithCustomToken(mCustomToken).

                addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete (@NonNull Task< AuthResult > task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("mCustomToken", "signInWithCustomToken:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginByEmailActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();

//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("mCustomToken", "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(LoginByEmailActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    private void AddSocialMediaLinks(String type, String link) {
            Call<Object> call = RetrofitClient.
                    getInstance()
                    .getApi()
                    .add_social_link(type, link);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Log.e("gson:add_social_link", new Gson().toJson(response.body()));

                    if (response != null) {

                        if (response.body() != null) {
                            Log.e("res:add_social_link", "isSuccessful");
                            try {
                                JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                                String message = jsonObject.getString("message");
                                if (message != null) {
//                                Toast.makeText(g, message, Toast.LENGTH_SHORT).show();
                                    Log.e("gson:add_social_link", message);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:add_social_link", response.errorBody().string());
//                            Toast.makeText(LoginByEmailActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Log.e("add_so_link:onFailure", String.valueOf(t));

                }
            });


    }

    private void Encode(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

    }


    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent_log =new Intent(LoginByEmailActivity.this, MainActivity.class);
            intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent_log);

        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtV_Register:
                Intent intent_login = new Intent(LoginByEmailActivity.this, RegistrationActivityOne.class);
                startActivity(intent_login);
                break;
            case R.id.btn_loginByEmail:
                if(push_token!=null&&!push_token.isEmpty()&&device_id!=null&&!device_id.isEmpty())
                loginByEmail(push_token,device_id);
                break;

            case R.id.txtV_Forgot:
                forgetPassword();
//                TODO remove access token and back forgot pass
//                handleCustomAccessToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJmaXJlYmFzZS1hZG1pbnNkay1oN3hueUBiYXNhdGFzaG9wcGluZy5pYW0uZ3NlcnZpY2VhY2NvdW50LmNvbSIsInN1YiI6ImZpcmViYXNlLWFkbWluc2RrLWg3eG55QGJhc2F0YXNob3BwaW5nLmlhbS5nc2VydmljZWFjY291bnQuY29tIiwiYXVkIjoiaHR0cHM6XC9cL2lkZW50aXR5dG9vbGtpdC5nb29nbGVhcGlzLmNvbVwvZ29vZ2xlLmlkZW50aXR5LmlkZW50aXR5dG9vbGtpdC52MS5JZGVudGl0eVRvb2xraXQiLCJpYXQiOjE1OTg5MTA5MDIsImV4cCI6MTU5ODkxNDUwMiwidWlkIjoiYWhtZWRfbW9oYW1lZHh5ekB5YWhvby5jb20ifQ.jZmeDQSGhzmCodfXYKlff9uSAyLyypEkPBY1LO8EcRT-EmnmGcdY19Z446Y9mtMnlISwBCtMeoCt1NrIU9XfUK8VMfwBcvN7wuBuHNS9cqB5QYdYZFOliOO_qvZkbpopCMQFgh0669mhxsqrhNNUSaNe9PK-0s3rZWH_JkDMkPzdyv-WGITcTGmZhVKj1DAHQD1yHcUM7BlQxHGaN0FxAPoGr038EIVAXkyFsHuBM0u5SQA4GfLdf9g_CcaQuu4QV0EBVLdvZOn-TEHQnR8hZTvxX9Yx5vmzL72MW8nVEEB2HqoASKHoN0mZ9JxkA9sHkbPZHKqRw5jjDpESphRrfw");
                break;
        }
    }

    private void forgetPassword() {
     String  email=et_login_email.getText().toString();

        if (email.isEmpty()) {
            et_login_email.setError("هذا الحقل مطلوب");
            et_login_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_login_email.setError("برجاء اختيار ايميل صحيح");
            et_login_email.requestFocus();
            return;
        }


        Call<ForgetPassResponse> call= RetrofitClient.
                getInstance().getApi().userForgetPassword(email);
        call.enqueue(new Callback<ForgetPassResponse>() {
            @Override
            public void onResponse(Call<ForgetPassResponse> call, Response<ForgetPassResponse> response) {
                ForgetPassResponse forgetPassResponse= response.body();

                if(forgetPassResponse!=null&&forgetPassResponse.getMessage()!=null) {
                    Toast.makeText(LoginByEmailActivity.this, forgetPassResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("res:forget_password", "Data"+forgetPassResponse.getData()+" "+"message"+forgetPassResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ForgetPassResponse> call, Throwable t) {

            }
        });


    }

    private void loginByEmail(String push_token, String device_id) {

        String email = et_login_email.getText().toString();
        String password = et_login_pass.getText().toString();


        if (email.isEmpty()) {
            et_login_email.setError("هذا الحقل مطلوب");
            et_login_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_login_email.setError("برجاء اختيار ايميل صحيح");
            et_login_email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            et_login_pass.setError("هذا الحقل مطلوب");
            et_login_pass.requestFocus();
            return;

        }

        Call call= RetrofitClient.
                getInstance()
                .getApi()
                .userLogin(email,password, device_id, push_token);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("gson:login", new Gson().toJson(response.body()) );

                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.e("res:login", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
                            if (message != null&&!message.isEmpty()) {
                                Toast.makeText(LoginByEmailActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (data != null) {
                                String token = data.getString("token");
                                String firebase_token=data.getString("firebase_token");
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
                                JSONObject social_links = data.getJSONObject("social_links");

//                                int length = social_links.length();
////                    ArrayList<Object>  socialLinks = new ArrayList<>();
//                                for (int i = 0; i < length; i++) {
//                                    JSONObject links = social_links.getJSONObject(i);
//                                    String type = links.getString("type");
//                                    String link = links.getString("link");

                                String facebookUrl="",instagramUrl="",youtubeUrl="";

                                if(social_links.has("facebook")) {
                                     facebookUrl = social_links.getString("facebook");
                                }
                                if(social_links.has("instagram")) {

                                     instagramUrl = social_links.getString("instagram");
                                }
                                 if(social_links.has("youtube")) {

                                      youtubeUrl = social_links.getString("youtube");
                                 }
//
//                                        Log.e("social_links", String.valueOf(social_links));



//                                }

                                User user = new User(token,firebase_token, name, email, image, is_merchant, market_name, address, lat, lng, phone, description,facebookUrl,instagramUrl,youtubeUrl);


                                SharedPrefManager.getInstance(LoginByEmailActivity.this)
                                        .saveUser(user);
                                if(token!=null&&!token.isEmpty()) {
                                    //add image profile
                                    if(encodedImage!=null&&!encodedImage.isEmpty()){
                                        AddImageProfileApi(encodedImage);
                                    }

                                    ///add social url
                                    if(facebookUrl!=null&&!facebookUrl.isEmpty()){
                                        AddSocialMediaLinks("facebook",facebookUrl);
                                    }
                                    if(instagramUrl!=null&&!instagramUrl.isEmpty()){
                                        AddSocialMediaLinks("instagram",instagramUrl);

                                    }
                                    if(youtubeUrl!=null&&!youtubeUrl.isEmpty()){
                                        AddSocialMediaLinks("youtube",youtubeUrl);

                                    }
                                }
                            }
                                Intent intent_log = new Intent(LoginByEmailActivity.this, MainActivity.class);
                                intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent_log);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

            } else if (response.errorBody() != null) {
                try {
                    Log.e("gson:loginError", response.errorBody().string());
                    Toast.makeText(LoginByEmailActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }




        }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("loginByApi:onFailure", String.valueOf(t));


            }
        });


    }

    private void AddImageProfileApi(String encodedImage) {
        Call call= RetrofitClient.
                getInstance().getApi()
                .storeImage(encodedImage);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:Change_Image", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");
                            if (msg != null) {
//                                Toast.makeText(RegistrationActivityTow.this, msg, Toast.LENGTH_SHORT).show();
                                Log.e("Change_Image",msg);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:Change_Image", response.errorBody().string());
                            Toast.makeText(LoginByEmailActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("ChangImgByApi:onFailure", String.valueOf(t));

            }
        });


    }

}
