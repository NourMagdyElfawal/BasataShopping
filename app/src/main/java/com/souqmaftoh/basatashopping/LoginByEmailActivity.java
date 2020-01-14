package com.souqmaftoh.basatashopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.souqmaftoh.basatashopping.Fonts.LatoBLack;

public class LoginByEmailActivity extends AppCompatActivity implements View.OnClickListener {

    LatoBLack txtV_Register;
    MaterialButton btn_loginByEmail; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
         txtV_Register = findViewById(R.id.txtV_Register);
         btn_loginByEmail=findViewById(R.id.btn_loginByEmail);
         txtV_Register.setOnClickListener(this);
        btn_loginByEmail.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtV_Register:
                Intent intent_login =new Intent(LoginByEmailActivity.this, RegistrationActivityOne.class);
                startActivity(intent_login);
                break;
            case R.id.btn_loginByEmail:
                loginByEmail();
                break;
        }
    }

    private void loginByEmail() {
    }
}
