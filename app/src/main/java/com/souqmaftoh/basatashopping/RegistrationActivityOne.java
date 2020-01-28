package com.souqmaftoh.basatashopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Models.DefaultResponse;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.Models.RegisterationUserResponse;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivityOne extends AppCompatActivity implements View.OnClickListener {
    EditText et_reg_name,et_reg_email,et_reg_password,et_reg_rep_password;
    Button btn_Register,btn_BusinessMan;
    ImageView img_profile;
    private static final int PICK_PHOTO_FOR_AVATAR = 0;


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
        img_profile=findViewById(R.id.img_profile);

        img_profile.setOnClickListener(this);
        btn_Register.setOnClickListener(this);
        btn_BusinessMan.setOnClickListener(this);




        //get sign in user using gmaile

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String token = acct.getIdToken();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
//            Toast.makeText(this, "gmailInfo" + personName + personGivenName + personFamilyName + personEmail + personId, Toast.LENGTH_SHORT).show();
//            Log.e("gmail","gmailInfo " + personName+" "+ personGivenName +" "+ token +" "+ personEmail +" "+ personId);
            RegistrationByApi(personName,personEmail,token,token);

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

            case R.id.img_profile:
                openGallery();
                break;
            case R.id.btn_Register:
                SignUp();
                break;

            case R.id.btn_BusinessMan:
                SignUpBusiness();


        }
}

    private void openGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_PHOTO_FOR_AVATAR);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_PHOTO_FOR_AVATAR:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore
                            .Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_PHOTO_FOR_AVATAR);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }

    }


    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                img_profile.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(RegistrationActivityOne.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(RegistrationActivityOne.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    private void SignUpBusiness() {
        String name=et_reg_name.getText().toString();
        String email=et_reg_email.getText().toString();
        String password=et_reg_password.getText().toString();
        String repPassword=et_reg_rep_password.getText().toString();

//TODO:unHash validation

//        if(name.isEmpty()){
//            et_reg_name.setError("هذا الحقل مطلوب");
//            et_reg_name.requestFocus();
//            return;
//        }
//
//        if(email.isEmpty()){
//            et_reg_email.setError("هذا الحقل مطلوب");
//            et_reg_email.requestFocus();
//            return;
//        }
//
//        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            et_reg_email.setError("برجاء اختيار ايميل صحيح");
//            et_reg_email.requestFocus();
//            return;
//        }
//        if(password.isEmpty()){
//            et_reg_password.setError("هذا الحقل مطلوب");
//            et_reg_password.requestFocus();
//            return;
//        }
//        if (password.length()<8){
//            et_reg_password.setError("برجاء اختيار رقم سري مكون من 8 ارقام على الاقل");
//            et_reg_password.requestFocus();
//            return;
//
//        }
//
//        if(repPassword.isEmpty()){
//            et_reg_rep_password.setError("هذا الحقل مطلوب");
//            et_reg_rep_password.requestFocus();
//            return;
//        }
//
//        if (!password.equals(repPassword)){
//            et_reg_rep_password.setError("الرقم السري غير متطابق");
//            et_reg_rep_password.requestFocus();
//            return;
//
//        }
        Call call= RetrofitClient.
                getInstance().getApi()
                .createUser(name,email,password,repPassword);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                Log.e("gson:register", new Gson().toJson(response.body()) );

                if(response.isSuccessful()){
                    Log.e("res:register","isSuccessful");


                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        String msg = jsonObject.getString("message");
                        if (msg != null) {
                        Toast.makeText(RegistrationActivityOne.this, msg, Toast.LENGTH_SHORT).show();
                        User user = new User(email, name);
                        SharedPrefManager.getInstance(RegistrationActivityOne.this)
                                .saveUser(user);
                        Intent intent_log = new Intent(RegistrationActivityOne.this, RegistrationActivityTow.class);
                        intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent_log);
                    }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else{
                    Toast.makeText(RegistrationActivityOne.this, "Some error occurred...", Toast.LENGTH_LONG).show();

                }
                }


                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.e("RegByApi:onFailure", String.valueOf(t));

                }
            });



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
        RegistrationByApi(name,email,password,repPassword);
    }


    private void RegistrationByApi(String name, String email, String password,String repPassword) {
        Call call= RetrofitClient.
                getInstance().getApi()
                .createUser(name,email,password,repPassword);
        call.enqueue(new Callback() {
                         @Override
                         public void onResponse(@NotNull Call call, @NotNull Response response) {
                             if(response!=null) {

                                 if (response.body() != null) {
                                     Log.e("gson:registration", new Gson().toJson(response.body()));
                                     try {
                                         JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                                         String msg = jsonObject.getString("message");
                                         if (msg != null) {
                                             Toast.makeText(RegistrationActivityOne.this, msg, Toast.LENGTH_SHORT).show();
                                             User user = new User(email, name);
                                             SharedPrefManager.getInstance(RegistrationActivityOne.this)
                                                     .saveUser(user);
                                             Intent intent_log = new Intent(RegistrationActivityOne.this, MainActivity.class);
                                             intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             startActivity(intent_log);
                                         }


                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }


                                 } else if (response.errorBody() != null) {
                                     try {
                                         Log.e("gson:registration", response.errorBody().string());
                                         Toast.makeText(RegistrationActivityOne.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                     }
                                 }

//                                 try {
//                                     JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
//                                     String msg = jsonObject.getString("message");
//                                     if (msg != null) {
//                                         Toast.makeText(RegistrationActivityOne.this, msg, Toast.LENGTH_SHORT).show();
//                                         User user = new User(email, name);
//                                         SharedPrefManager.getInstance(RegistrationActivityOne.this)
//                                                 .saveUser(user);
//                                         Intent intent_log = new Intent(RegistrationActivityOne.this, MainActivity.class);
//                                         intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                         startActivity(intent_log);
//                                     }
//
//
//                                 } catch (JSONException e) {
//                                     e.printStackTrace();
//                                 }
//
                             }
            }


            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("RegByApi:onFailure", String.valueOf(t));

            }
        });

    }

}
