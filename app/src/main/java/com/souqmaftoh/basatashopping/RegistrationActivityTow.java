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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Models.DefaultResponse;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.Models.ForgetPassResponse;
import com.souqmaftoh.basatashopping.Models.RegistrationStoreResponse;
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

public class RegistrationActivityTow extends AppCompatActivity implements View.OnClickListener {
        String email,name,password,repPassword;
        EditText et_reg_store_name,et_reg_address,et_reg_location,et_reg_phone,et_reg_disc;
        MaterialButton btn_reg_storeRegist;
        ImageView img_profile;
        private static final int PICK_PHOTO_FOR_AVATAR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_tow);

        et_reg_store_name=findViewById(R.id.et_reg_store_name);
        et_reg_address=findViewById(R.id.et_reg_address);
        et_reg_location=findViewById(R.id.et_reg_location);
        et_reg_phone=findViewById(R.id.et_reg_phone);
        et_reg_disc=findViewById(R.id.et_reg_disc);
        btn_reg_storeRegist=findViewById(R.id.btn_reg_storeRegist);
        img_profile=findViewById(R.id.img_profile);

//        img_profile.setOnClickListener(this);
        btn_reg_storeRegist.setOnClickListener(this);



//        User user= SharedPrefManager.getInstance(this).getUser();
//        if(user!=null){
//            if( user.getEmail()!=null&&!user.getEmail().isEmpty()){
//                email=user.getEmail();
//
//            }
//
//            if( user.getName()!=null&&!user.getName().isEmpty()){
//                name=user.getName();
//
//            }
//
//        }
//get data from RegistrationActivityOne

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            email = bundle.getString("email");
            password = bundle.getString("password");
            repPassword = bundle.getString("repPassword");

        }


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_store:
                openGallery();
                break;
            case R.id.btn_reg_storeRegist:
                signUpAsStore();
                break;
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
                Toast.makeText(RegistrationActivityTow.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(RegistrationActivityTow.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    private void signUpAsStore() {

        String market_name=et_reg_store_name.getText().toString();
        String address=et_reg_address.getText().toString();
        String phone=et_reg_phone.getText().toString();
        String description=et_reg_disc.getText().toString();
        String lat="123";
        String lng="321";


        if(market_name.isEmpty()){
            et_reg_store_name.setError("هذا الحقل مطلوب");
            et_reg_store_name.requestFocus();
            return;
        }

        RegistrationStoreByApi(name,email,password,repPassword,market_name,address,lat,lng,phone,description);




    }



    private void RegistrationStoreByApi(String name, String email, String password, String repPassword,
                                        String market_name, String address, String lat, String lng, String phone, String description) {
        Call call= RetrofitClient.
                getInstance().getApi()
                .createUser(name,email,password,repPassword,market_name,address,lat,lng,phone,description);
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
                                Toast.makeText(RegistrationActivityTow.this, msg, Toast.LENGTH_SHORT).show();
                                if(msg.equalsIgnoreCase("تم التسجيل بنجاح, ستصلك رسالة على البريد المسجل لتفعيل الحساب")) {
                                    User user = new User(name,email,  market_name, address, lat, lng, phone, description);
                                    SharedPrefManager.getInstance(RegistrationActivityTow.this)
                                            .saveUser(user);
                                    Intent intent_log = new Intent(RegistrationActivityTow.this, MainActivity.class);
                                    intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent_log);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:registration", response.errorBody().string());
                            Toast.makeText(RegistrationActivityTow.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
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


//    private void RegistrationStoreByApi(String name, String email, String store_name, String address, String lat, String lng, String phone, String description) {
//
//        Call<String> call= RetrofitClient.
//                getInstance()
//                .getApi()
//                .createStore(name,"ahmed_mohamedxyz@yahoo.com",store_name,address,lat,lng,phone,description);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                String urJson  = response.body() ;
//
//                Log.e("gson:edit_profile", urJson );
//
//                if(response.isSuccessful()){
//                    Log.e("res:edit_profile","isSuccessful");
//                }
//
//
////                RegistrationStoreResponse dr=response.body();
////                if (dr != null && dr.getMessage() != null) {
////                 //   Toast.makeText(RegistrationActivityTow.this, dr.getMessage(), Toast.LENGTH_SHORT).show();
////                    Log.e("res:edit_profile", "Data"+dr.getData()+" "+"message"+dr.getMessage());
////                    String image="";
////                    Boolean is_merchant=true;
////                    User user=new User(email,name,image,is_merchant,store_name,address,lat,lng,phone,description);
////                    SharedPrefManager.getInstance(RegistrationActivityTow.this)
////                            .saveUser(user);
////                    Intent intent_log =new Intent(RegistrationActivityTow.this, MainActivity.class);
////                    intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                    startActivity(intent_log);
//
//
////                }
//            }
//
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
////                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
//                Log.e("RegByApiTow:onFailure", String.valueOf(t));
//
//            }
//        });
//    }
}
