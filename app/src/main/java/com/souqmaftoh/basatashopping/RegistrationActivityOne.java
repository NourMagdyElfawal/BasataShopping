package com.souqmaftoh.basatashopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Models.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivityOne extends AppCompatActivity implements View.OnClickListener {
    EditText et_reg_name,et_reg_email,et_reg_password,et_reg_rep_password;
    Button btn_Register,btn_BusinessMan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_one);
        et_reg_name=findViewById(R.id.et_reg_name);
        et_reg_email=findViewById(R.id.et_reg_email);
        et_reg_password=findViewById(R.id.et_reg_password);
        et_reg_rep_password=findViewById(R.id.et_reg_rep_password);
        btn_Register=findViewById(R.id.btn_Register);
        btn_BusinessMan=findViewById(R.id.btn_BusinessMan);

        btn_Register.setOnClickListener(this);
        btn_BusinessMan.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Register:
                SignUp();
                break;

            case R.id.btn_BusinessMan:
                SignUp();
                Intent intent_reg =new Intent(RegistrationActivityOne.this, RegistrationActivityTow.class);
                startActivity(intent_reg);


        }
}

    private void SignUp() {
        String name=et_reg_name.getText().toString();
        String email=et_reg_email.getText().toString();
        String password=et_reg_password.getText().toString();
        String repPassword=et_reg_rep_password.getText().toString();


        if(name.isEmpty()){
            et_reg_name.setError("هذا الحقل مطلوب");
            et_reg_name.requestFocus();
            return;
        }

        if(email.isEmpty()){
            et_reg_email.setError("هذا الحقل مطلوب");
            et_reg_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_reg_email.setError("برجاء اختيار ايميل صحيح");
            et_reg_email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            et_reg_password.setError("هذا الحقل مطلوب");
            et_reg_password.requestFocus();
            return;
        }
        if (password.length()<8){
            et_reg_password.setError("برجاء اختيار رقم سري مكون من 8 ارقام على الاقل");
            et_reg_password.requestFocus();
            return;

        }

        if(repPassword.isEmpty()){
            et_reg_rep_password.setError("هذا الحقل مطلوب");
            et_reg_rep_password.requestFocus();
            return;
        }

        if (!password.equals(repPassword)){
            et_reg_rep_password.setError("الرقم السري غير متطابق");
            et_reg_rep_password.requestFocus();
            return;

        }
        Call<DefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .createUser(name,email,password,repPassword);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(@NonNull Call<DefaultResponse> call, @NonNull Response<DefaultResponse> response) {


                DefaultResponse dr=response.body();
                if (dr != null && dr.getMsg() != null)
                    Toast.makeText(RegistrationActivityOne.this, dr.getMsg(), Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(RegistrationActivityOne.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
