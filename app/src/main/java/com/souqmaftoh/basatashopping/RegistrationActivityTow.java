package com.souqmaftoh.basatashopping;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.FragmentTransitionSupport;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import com.souqmaftoh.basatashopping.fragments.mapFragment.MapFragment;
import com.souqmaftoh.basatashopping.fragments.myAccount.MyAccountFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivityTow extends AppCompatActivity implements View.OnClickListener {
        String email,name,password,repPassword;
        EditText et_reg_store_name,et_reg_address,et_reg_location,et_reg_phone,et_reg_disc;
        MaterialButton btn_reg_storeRegist;
        ImageView img_profile;
        private static final int PICK_PHOTO_FOR_AVATAR = 0;
        String encodedImage;
        private static final int REQUEST_FRAGMENT = 1444;
        String lat;
        String lng;
        Bitmap bitmap;
        Dialog dialog;
        EditText et_dia_facebook,et_dia_instagram,et_dia_youtube;
        ConstraintLayout cons_socialmedia;
        String facebook,instagram,youtube;
        String market_name,address,phone,description;
        Geocoder geocoder;
        public HashMap<String, String> step2 = new HashMap<>();
        Double latitude,longitude;
        String market_region;







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
        cons_socialmedia=findViewById(R.id.cons_socialmedia);

//        img_profile.setOnClickListener(this);
        btn_reg_storeRegist.setOnClickListener(this);
        et_reg_location.setOnClickListener(this);
        cons_socialmedia.setOnClickListener(this);


//get data from RegistrationActivityOne

        Intent intent = getIntent();
        if(intent.getSerializableExtra("step1")!=null) {
            step2 = (HashMap<String, String>) intent.getSerializableExtra("step1");
            Log.e("step2", String.valueOf(step2));

        }
//        if (bundle != null) {
//            if(bundle.getString("step1")!=null&&!bundle.getString("step1").isEmpty())
//                step2 = bundle.getSerializable("step1");
//
//            name = bundle.getString("name");
//            email = bundle.getString("email");
//            password = bundle.getString("password");
//            repPassword = bundle.getString("repPassword");
//

//            market_name = bundle.getString("market_name");
//            if(market_name!=null&&!market_name.isEmpty()){
//                et_reg_store_name.setText(market_name);
//            }
//            address = bundle.getString("address");
//            if(address!=null&&!address.isEmpty()){
//                et_reg_address.setText(address);
//            }
//
//            phone = bundle.getString("phone");
//            if(phone!=null&&!phone.isEmpty()){
//                et_reg_phone.setText(phone);
//            }
//
//            description = bundle.getString("description");
//            if(description!=null&&!description.isEmpty()){
//                et_reg_disc.setText(description);
//            }

//            lat = bundle.getString("Lat");
//            lng = bundle.getString("Lng");

//            bitmap = (Bitmap) getIntent().getParcelableExtra("bitmap");
        }

//        if(bitmap!=null){
//            Encude(bitmap);
//
//        }
//        if (bundle != null) {
//
//            if (lat != null)
//                Log.e("maplat", lat);
//        }


//        if(savedInstanceState!=null){
//            name = savedInstanceState.getString("name");
//            email = savedInstanceState.getString("email");
//            password = savedInstanceState.getString("password");
//            repPassword = savedInstanceState.getString("repPassword");
//            market_name = savedInstanceState.getString("market_name");
//            et_reg_store_name.setText(market_name);
//            address = savedInstanceState.getString("address");
//            phone = savedInstanceState.getString("phone");
//            description = savedInstanceState.getString("description");
//
//            Log.e("name",name);
//
//        }
//    }

    private void Encude(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.img_store:
//                openGallery();
//                break;
            case R.id.btn_reg_storeRegist:
                signUpAsStore();
                break;

            case R.id.et_reg_location:
                market_name=et_reg_store_name.getText().toString();
                address=et_reg_address.getText().toString();
                phone=et_reg_phone.getText().toString();
                description=et_reg_disc.getText().toString();

                if(market_name!=null&&!market_name.isEmpty()){
                    step2.put("market_name",market_name);
                }
                if(address!=null&&!address.isEmpty()){
                    step2.put("address",address);
                }
                if(phone!=null&&!phone.isEmpty()){
                    step2.put("phone",phone);
                }
                if(description!=null&&!description.isEmpty()){
                    step2.put("description",description);
                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                MapFragment mapFragment= new MapFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable("step2",step2);
                mapFragment.setArguments(bundle);
                ft.replace(R.id.regist_cont, mapFragment, "findThisFragment");
                ft.commit();
                Log.e("step2", String.valueOf(step2));


                break;

            case R.id.cons_socialmedia:
                openAddAdsDialogue();
                break;
        }
    }



    private void openAddAdsDialogue() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogue_socialmedia_links);


        et_dia_facebook = dialog.findViewById(R.id.et_dia_facebook);
        et_dia_instagram = dialog.findViewById(R.id.et_dia_instagram);
        et_dia_youtube = dialog.findViewById(R.id.et_dia_youtube);

        Button btn_dialog_ok = dialog.findViewById(R.id.btn_dialog_ok);
        Button btn_dialog_cancel = dialog.findViewById(R.id.btn_dialog_cancel);



        btn_dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 facebook=et_dia_facebook.getText().toString();
                instagram=et_dia_instagram.getText().toString();
                 youtube=et_dia_youtube.getText().toString();
                dialog.dismiss();

            }
        });



        btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }



//    private void openGallery() {
//        try {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_PHOTO_FOR_AVATAR);
//            } else {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//
////                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
////                intent.setType("image/*");
//                startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case PICK_PHOTO_FOR_AVATAR:
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore
//                            .Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(galleryIntent, PICK_PHOTO_FOR_AVATAR);
//                } else {
//                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
//                }
//                break;
//        }
//
//    }
//
//
//    /**
//     * Dispatch incoming result to the correct fragment.
//     *
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_FRAGMENT) {
                if(data != null){
                    lat = data.getStringExtra("Lat");
                    lng = data.getStringExtra("Lng");
                    Log.e("mapLat", lat);
                    Log.e("mapLng", lng);
                    convertLatLngToAdd(lat,lng);
                }


            }

        }

    }
////            try {
//                final Uri imageUri = data.getData();
//
//            try {
//
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this).getContentResolver(), imageUri);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
//                byte[] b = baos.toByteArray();
//
//                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            if (requestCode==REQUEST_FRAGMENT){
//                lat = data.getStringExtra("Lat");
//                lng=data.getStringExtra("Lng");
//                Log.e("mapLat",lat);
//                Log.e("mapLng",lng);
//
//            }
//
//
////                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
////                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
////                img_profile.setImageBitmap(selectedImage);
////            } catch (FileNotFoundException e) {
////                e.printStackTrace();
////                Toast.makeText(RegistrationActivityTow.this, "Something went wrong", Toast.LENGTH_LONG).show();
////            }
//
//        }else {
//            Toast.makeText(RegistrationActivityTow.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
//        }
//
//    }

    private void signUpAsStore() {

         market_name=et_reg_store_name.getText().toString();
         address=et_reg_address.getText().toString();
         phone=et_reg_phone.getText().toString();
         description=et_reg_disc.getText().toString();


        if(market_name.isEmpty()){
            et_reg_store_name.setError("هذا الحقل مطلوب");
            et_reg_store_name.requestFocus();
            return;
        }

        RegistrationStoreByApi(name,email,market_region,password,repPassword,market_name,address,lat,lng,phone,description);
        AddImageProfileApi();



    }

    private void AddImageProfileApi() {
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
                            Log.e("gson:registration", response.errorBody().string());
                            Toast.makeText(RegistrationActivityTow.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
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

    private void RegistrationStoreByApi(String name, String email, String market_region,String password, String repPassword,
                                        String market_name, String address, String lat, String lng, String phone, String description) {
        Call call= RetrofitClient.
                getInstance().getApi()
                .createStore(name,email, Integer.parseInt(market_region),password,repPassword,market_name,address,lat,lng,phone,description);
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
//                                    User user = new User(name,email,  market_name, address, lat, lng, phone, description);
//                                    SharedPrefManager.getInstance(RegistrationActivityTow.this)
//                                            .saveUser(user);
//
//                                    Intent intent_log = new Intent(RegistrationActivityTow.this, MainActivity.class);
//                                    intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent_log);

                                    Intent intent_log = new Intent(RegistrationActivityTow.this, LoginByEmailActivity.class);
                                    intent_log.putExtra("email",email);
                                    intent_log.putExtra("password", password);
                                    intent_log.putExtra("BitmapImage", bitmap);
                                    intent_log.putExtra("facebook",facebook);
                                    intent_log.putExtra("instagram",instagram);
                                    intent_log.putExtra("youtube",youtube);

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
//@Override
//public void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
//    super.onRestoreInstanceState(savedInstanceState);
//    // Restore UI state from the savedInstanceState.
//    // This bundle has also been passed to onCreate.
////    name = savedInstanceState.getString("name");
////    et_reg_store_name.setText(name);
////    email = savedInstanceState.getString("email");
////    password = savedInstanceState.getString("password");
////    repPassword = savedInstanceState.getString("repPassword");
////    market_name = savedInstanceState.getString("market_name");
////    address = savedInstanceState.getString("address");
////    phone = savedInstanceState.getString("phone");
////    description = savedInstanceState.getString("description");
////
////    Log.e("name",name);
//
//}
//
//
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
////        super.onSaveInstanceState(savedInstanceState);
//        // Save UI state changes to the savedInstanceState.
//        // This bundle will be passed to onCreate if the process is
//        // killed and restarted.
//        savedInstanceState.putString("name",name);
//        savedInstanceState.putString("email",email);
//        savedInstanceState.putString("password",password);
//        savedInstanceState.putString("repPassword",repPassword);
//
//
//        savedInstanceState.putString("market_name",market_name);
//        savedInstanceState.putString("address",address);
//        savedInstanceState.putString("phone",phone);
//        savedInstanceState.putString("description",description);
//        super.onSaveInstanceState(savedInstanceState);
//
//
//        // etc.
//    }


private void convertLatLngToAdd(String lat, String lng) {
    geocoder = new Geocoder(this, Locale.getDefault());
    StringBuilder strReturnedAddress;
    if(lat!=null&&lng!=null) {
        latitude = Double.parseDouble(lat);
        longitude = Double.parseDouble(lng);

        try {
            if (geocoder.isPresent()) {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (addresses != null && addresses.size() > 0) {
                    Address returnedAddress = addresses.get(0);
                    strReturnedAddress = new StringBuilder("");
                    Log.e("MaxAddressLine", String.valueOf(returnedAddress.getMaxAddressLineIndex()));
                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i));
                        Log.e("count", String.valueOf(i));
                    }
                    Log.e("Myaddress", strReturnedAddress.toString());
                    et_reg_location.setText(strReturnedAddress.toString());

//                    mptxt.setText(strReturnedAddress.toString());
                } else {
                    Log.e("Myaddress", "No Address returned!");
                }
//                String locdescSt = mptxt.getText().toString();
                //    mapstep2.put("locdesc", Tools.encodeStr(locdescSt.replaceAll("\\r"," ").replaceAll("\\n"," ")).replace("+", "%20"));
            }
        } catch (IOException e) {
            Log.e("MyCurrentaddress", "Canont get Address!");
        }
    }

}

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("start","start");

        Intent intent = getIntent();
        if(intent.getSerializableExtra("mapstep2")!=null) {
            step2 = (HashMap<String, String>) intent.getSerializableExtra("mapstep2");
            Log.e("step2aftermap", String.valueOf(step2));
           lat= step2.get("lat");
           lng= step2.get("lng");
           convertLatLngToAdd(lat,lng);
            name=step2.get("name");
            email=step2.get("email");
            market_region=step2.get("market_region");
            password=step2.get("password");
            repPassword=step2.get("repPassword");


            market_name=step2.get("market_name");
            if(market_name!=null&&!market_name.isEmpty())
                et_reg_store_name.setText(market_name);
                address=step2.get("address");
            if(address!=null&&!address.isEmpty())
                et_reg_address.setText(address);


            phone=step2.get("phone");
            if(phone!=null&&!phone.isEmpty())
                et_reg_phone.setText(phone);

                description= step2.get("description");
            if(description!=null&&!description.isEmpty())
                et_reg_disc.setText(description);


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume","onResume");

//        Bundle bundle = new Bundle();
//        bundle = getIntent().getExtras();
//        if (bundle != null) {
//            name = bundle.getString("name");
//            email = bundle.getString("email");
//            password = bundle.getString("password");
//            repPassword = bundle.getString("repPassword");
//
//
//            market_name = bundle.getString("market_name");
//            if (market_name != null && !market_name.isEmpty()) {
//                et_reg_store_name.setText(market_name);
//            }
//            address = bundle.getString("address");
//            if (address != null && !address.isEmpty()) {
//                et_reg_address.setText(address);
//            }
//
//            phone = bundle.getString("phone");
//            if (phone != null && !phone.isEmpty()) {
//                et_reg_phone.setText(phone);
//            }
//
//            description = bundle.getString("description");
//            if (description != null && !description.isEmpty()) {
//                et_reg_disc.setText(description);
//            }
//
//        }
    }


}
