package com.souqmaftoh.basatashopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.Settings;
import android.service.autofill.SaveCallback;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Fonts.LatoBLack;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.Models.ForgetPassResponse;
import com.souqmaftoh.basatashopping.Models.LoginDefault;
import com.souqmaftoh.basatashopping.Models.LoginResponse;
import com.souqmaftoh.basatashopping.Services.MyFirebaseMessagingService;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.GenericArrayType;
import java.text.ParseException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginByEmailActivity extends AppCompatActivity implements View.OnClickListener {

    LatoBLack txtV_Register;
    MaterialButton btn_loginByEmail;
    EditText et_login_email, et_login_pass;
    LatoBLack txtV_Forgot;
    String device_id,push_token;


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
    }


    @Override
    protected void onStart() {
        super.onStart();
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
                loginByEmail();
                break;

            case R.id.txtV_Forgot:
                forgetPassword();
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

    private void loginByEmail() {

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
                .userLogin(email,password,device_id,push_token);
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
                            if (message != null) {
                                Toast.makeText(LoginByEmailActivity.this, message, Toast.LENGTH_SHORT).show();
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


                                SharedPrefManager.getInstance(LoginByEmailActivity.this)
                                        .saveUser(user);
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
}
