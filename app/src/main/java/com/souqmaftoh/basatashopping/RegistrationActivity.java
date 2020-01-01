package com.souqmaftoh.basatashopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_reg_name,et_reg_email,et_reg_password,et_reg_rep_password;
    Button btn_Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        et_reg_name=findViewById(R.id.et_reg_name);
        et_reg_email=findViewById(R.id.et_reg_email);
        et_reg_password=findViewById(R.id.et_reg_password);
        et_reg_rep_password=findViewById(R.id.et_reg_rep_password);
        btn_Register=findViewById(R.id.btn_Register);
        
        btn_Register.setOnClickListener(this);
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
        if (password.length()<6){
            et_reg_password.setError("برجاء اختيار رقم سري مكون من 6 ارقام على الاقل");
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


    }
}
