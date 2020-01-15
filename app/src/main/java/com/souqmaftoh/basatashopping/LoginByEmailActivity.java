package com.souqmaftoh.basatashopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Fonts.LatoBLack;
import com.souqmaftoh.basatashopping.Models.LoginResponse;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginByEmailActivity extends AppCompatActivity implements View.OnClickListener {

    LatoBLack txtV_Register;
    MaterialButton btn_loginByEmail;
    EditText et_login_email, et_login_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        et_login_email = findViewById(R.id.et_login_email);
        et_login_pass = findViewById(R.id.et_login_pass);
        txtV_Register = findViewById(R.id.txtV_Register);
        btn_loginByEmail = findViewById(R.id.btn_loginByEmail);
        txtV_Register.setOnClickListener(this);
        btn_loginByEmail.setOnClickListener(this);
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
        }
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

        Call<LoginResponse> call= RetrofitClient.
                getInstance().getApi().userLogin(email,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse= response.body();

                if(loginResponse!=null&&loginResponse.getData()!=null) {
                    Toast.makeText(LoginByEmailActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("res", String.valueOf(loginResponse.getData().getName()));
                    SharedPrefManager.getInstance(LoginByEmailActivity.this)
                            .saveUser(loginResponse.getData());
                    Intent intent_log =new Intent(LoginByEmailActivity.this, MainActivity.class);
                    intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent_log);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });


    }
}
