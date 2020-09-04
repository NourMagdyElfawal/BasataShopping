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
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Interface.Governorates;
import com.souqmaftoh.basatashopping.Interface.Regions;
import com.souqmaftoh.basatashopping.Models.DefaultResponse;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.Models.RegisterationUserResponse;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivityOne extends AppCompatActivity implements View.OnClickListener {
    EditText et_reg_name,et_reg_email,et_reg_password,et_reg_rep_password;
    Button btn_Register,btn_BusinessMan;
    ImageView img_profile;
    private static final int PICK_PHOTO_FOR_AVATAR = 0;
    String encodedImage;
    Bitmap bitmap;
    String name,email,password;
    public HashMap<String, String> step1 = new HashMap<>();
    private Spinner spinGovernorate,spinRegion;
    int regionId,governorateId;




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

        // Spinner element
        spinGovernorate = findViewById(R.id.spinner_gov);
        spinRegion = findViewById(R.id.spin_reg);


        img_profile.setOnClickListener(this);
        btn_Register.setOnClickListener(this);
        btn_BusinessMan.setOnClickListener(this);

        getGovernoratesApi();


//        Bundle bundle=new Bundle();
//        bundle = getIntent().getExtras();
//        if(bundle!=null){
//            name=bundle.getString("name");
//            email=bundle.getString("email");
//            password=bundle.getString("password");
//            RegistrationByApi(name,email,password,password);
//
//        }


        //get sign in user using gmaile

//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String token = acct.getIdToken();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//            Uri personPhoto = acct.getPhotoUrl();
////            Toast.makeText(this, "gmailInfo" + personName + personGivenName + personFamilyName + personEmail + personId, Toast.LENGTH_SHORT).show();
////            Log.e("gmail","gmailInfo " + personName+" "+ personGivenName +" "+ token +" "+ personEmail +" "+ personId);
//            RegistrationByApi(personName,personEmail,personId,personId);
//
//        }
//
    }
    private void getGovernoratesApi() {

        ArrayList<Governorates> mGovernorates = new ArrayList<>();

        Call call= RetrofitClient.
                getInstance()
                .getApi()
                .get_governorates();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                Log.e("gson:get_governorates", new Gson().toJson(response.body()) );

                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("res:get_governorates", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
                            if (message != null&&!message.isEmpty()) {
                                Toast.makeText(RegistrationActivityOne.this, message, Toast.LENGTH_SHORT).show();
                            }
                            JSONObject jsonData  = jsonObject.getJSONObject("data");
                            JSONArray arrJson = jsonData.getJSONArray("data");
                            JSONObject[] arr=new JSONObject[arrJson.length()];

                            for(int i = 0; i < arrJson.length(); i++) {
                                arr[i] = arrJson.getJSONObject(i);
                                Log.e("tag", String.valueOf(arr[i]));
                                String governorate=arr[i].getString("name_ar");
                                int id=arr[i].getInt("id");

                                mGovernorates.add(new Governorates(governorate,id));
                            }
                            Log.e("governorate", String.valueOf(mGovernorates));
                            if(mGovernorates!=null){
                                spinnerGovernorates(mGovernorates);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
                    }

                } else if (response.errorBody() != null) {
                    try {
                        Log.e("gson:governorates_error", response.errorBody().string());
                        Toast.makeText(RegistrationActivityOne.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }




            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("categories:onFailure", String.valueOf(t));

            }
        });


    }



    private void getRegionApi(int governorateId) {

        ArrayList<Regions> mRegions = new ArrayList<>();

        Call call= RetrofitClient.
                getInstance()
                .getApi()
                .get_regions(governorateId);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                Log.e("gson:get_regions", new Gson().toJson(response.body()) );

                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("res:get_regions", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
                            if (message != null&&!message.isEmpty()) {
//                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                            JSONObject jsonData  = jsonObject.getJSONObject("data");
                            JSONArray arrJson = jsonData.getJSONArray("data");
                            JSONObject[] arr=new JSONObject[arrJson.length()];

                            for(int i = 0; i < arrJson.length(); i++) {
                                arr[i] = arrJson.getJSONObject(i);
                                Log.e("tag", String.valueOf(arr[i]));
                                String region=arr[i].getString("name_ar");
                                int id=arr[i].getInt("id");

                                mRegions.add(new Regions(region,id));
                            }
                            Log.e("get_regions", String.valueOf(mRegions));
                            if(mRegions!=null){
                                spinnerRegions(mRegions);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
                    }

                } else if (response.errorBody() != null) {
                    try {
                        Log.e("gson:get_regions_error", response.errorBody().string());
                        Toast.makeText(RegistrationActivityOne.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }




            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("get_regions:onFailure", String.valueOf(t));

            }
        });




    }





    private void spinnerRegions(ArrayList<Regions> mRegion) {
        // Spinner click listener
        spinRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.e("position", String.valueOf(position));

                regionId=mRegion.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                regionId=mRegion.get(0).getId();

            }
        });

        // Spinner Drop down elements
        List<String> regions = new ArrayList<String>();

        for(int i = 0; i < mRegion.size(); i++) {
            String[] arr = new String[mRegion.size()];
//            arr[i] = mCategories.get(i).getCategory();
            regions.add(mRegion.get(i).getRegion());
        }

//        regions.add("samsung");
//        regions.add("iphon");
//        regions.add("nokia");
//        regions.add("infinix");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regions);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinRegion.setAdapter(dataAdapter);



    }

    private void spinnerGovernorates(ArrayList<Governorates> mGovernorates) {
        // Spinner click listener
        spinGovernorate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.e("position", String.valueOf(position));
                governorateId=mGovernorates.get(position).getId();
                Log.e("governorateId", String.valueOf(governorateId));

                getRegionApi(governorateId);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                governorateId=mGovernorates.get(0).getId();
                if(governorateId!=-1)
                    getRegionApi(governorateId);

            }
        });

        // Spinner Drop down elements
        List<String> governorates = new ArrayList<String>();
        for(int i = 0; i < mGovernorates.size(); i++) {
            String[] arr = new String[mGovernorates.size()];
//            arr[i] = mCategories.get(i).getCategory();
            governorates.add(mGovernorates.get(i).getGovernorate());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, governorates);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinGovernorate.setAdapter(dataAdapter);


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
            final Uri imageUri = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this).getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();

                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                img_profile.setImageURI(imageUri);

            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(RegistrationActivityOne.this, "Something went wrong", Toast.LENGTH_LONG).show();
//            }

        }else {
            Toast.makeText(RegistrationActivityOne.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    private void SignUpBusiness() {
        String name=et_reg_name.getText().toString();
        String email=et_reg_email.getText().toString();
        String password=et_reg_password.getText().toString();
//        String password="12345678";

        String repPassword=et_reg_rep_password.getText().toString();
//        String repPassword="12345678";


//TODO:unHash validation

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

        step1.put("name", name);
        step1.put("email", email);
        step1.put("password", password);
        step1.put("repPassword", repPassword);
        step1.put("market_region", String.valueOf(regionId));


        Intent intent = new Intent(RegistrationActivityOne.this, RegistrationActivityTow.class);
        intent.putExtra("step1",step1);
//        step1.put("BitmapImage", bitmap);
        startActivity(intent);


//        Call call= RetrofitClient.
//                getInstance().getApi()
//                .createUser(name,email,password,repPassword);
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) {
//                Log.e("gson:register", new Gson().toJson(response.body()) );
//
//                if(response.isSuccessful()){
//                    Log.e("res:register","isSuccessful");
//
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
//                        String msg = jsonObject.getString("message");
//                        if (msg != null) {
//                        Toast.makeText(RegistrationActivityOne.this, msg, Toast.LENGTH_SHORT).show();
//                        User user = new User(email, name);
//                        SharedPrefManager.getInstance(RegistrationActivityOne.this)
//                                .saveUser(user);
//                        Intent intent_log = new Intent(RegistrationActivityOne.this, RegistrationActivityTow.class);
//                        intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent_log);
//                    }
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }else{
//                    Toast.makeText(RegistrationActivityOne.this, "Some error occurred...", Toast.LENGTH_LONG).show();
//
//                }
//                }
//
//
//                @Override
//                public void onFailure(Call call, Throwable t) {
//                    Log.e("RegByApi:onFailure", String.valueOf(t));
//
//                }
//            });



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
        RegistrationByApi(name,email,regionId,password,repPassword);
    }
//    private void AddImageProfileApi() {
//        Call call= RetrofitClient.
//                getInstance().getApi()
//                .storeImage(encodedImage);
//        call.enqueue(new Callback() {
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) {
//                if(response!=null) {
//
//                    if (response.body() != null) {
//                        Log.e("gson:Change_Image", new Gson().toJson(response.body()));
//                        try {
//                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
//                            String msg = jsonObject.getString("message");
//                            if (msg != null) {
////                                Toast.makeText(RegistrationActivityTow.this, msg, Toast.LENGTH_SHORT).show();
//                                Log.e("Change_Image",msg);
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    } else if (response.errorBody() != null) {
//                        try {
//                            Log.e("gson:Change_Image", response.errorBody().string());
//                            Toast.makeText(RegistrationActivityOne.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call call, Throwable t) {
////                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
//                Log.e("ChangImgByApi:onFailure", String.valueOf(t));
//
//            }
//        });
//
//
//    }


    private void RegistrationByApi(String name, String email,int regionId, String password,String repPassword) {
        Call call= RetrofitClient.
                getInstance().getApi()
                .createUser(name,email,regionId,password,repPassword);
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
                                             if(msg.equalsIgnoreCase("تم التسجيل بنجاح, ستصلك رسالة على البريد المسجل لتفعيل الحساب")){}

//                                             User user = new User(email, name);
//                                             SharedPrefManager.getInstance(RegistrationActivityOne.this)
//                                                     .saveUser(user);
                                             Intent intent_log = new Intent(RegistrationActivityOne.this, LoginByEmailActivity.class);
                                             intent_log.putExtra("email",email);
                                             intent_log.putExtra("password", password);
                                             intent_log.putExtra("BitmapImage", bitmap);
                                             intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             startActivity(intent_log);
//                                             AddImageProfileApi();

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
