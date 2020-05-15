package com.souqmaftoh.basatashopping.fragments.myAccount;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Fonts.LatoBLack;
import com.souqmaftoh.basatashopping.LoginActivity;
import com.souqmaftoh.basatashopping.LoginByEmailActivity;
import com.souqmaftoh.basatashopping.MainActivity;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.RegistrationActivityOne;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;
import com.souqmaftoh.basatashopping.fragments.ItemsRecyclerFragment.ItemsRecyclerFragment;
import com.souqmaftoh.basatashopping.fragments.mapFragment.MapFragment;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class MyAccountFragment extends Fragment implements View.OnClickListener {

    private MyAccountViewModel mViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleApiClient mGoogleApiClient;
    private HashMap<String, String> hashMapAccountSt2;
    boolean flag;

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SmsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getSerializable("hashMapAccountDetails") != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
                hashMapAccountSt2 = (HashMap<String, String>) getArguments().getSerializable("hashMapAccountDetails");
                Log.e("hashMapAccountDetails", String.valueOf(hashMapAccountSt2));
                flag=true;
            }else {
                flag=false;
            }
        }
    }


    LatoBLack btn_pro_logout, btn_pro_edit,btn_pro_addv,btn_pro_conv_merch;
    GoogleSignInClient mGoogleSignInClient;
    EditText et_pro_name, et_pro_email, et_pro_storeName, et_pro_address, et_pro_location, et_pro_phone, et_pro_storeDisc;
    TextView tv_pro_pass;
    String token;
    Boolean is_merchant;
    EditText et_dia_oldPass,et_dia_newPass,et_dia_repPass;
    MapFragment mapFragment;
    String lat;
    String lng;
    ImageView iv_pro_img;
    String encodedImage;
    Bitmap bitmap;
    String location;
    Geocoder geocoder;
    Dialog dialog;
    Uri selectedImage;
    RelativeLayout relative_conv_merch;
    LinearLayout linear_is_merchant;

    User user;

    private static final int PICK_PHOTO_FOR_AVATAR = 0;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,

    };


    private static final int INITIAL_REQUEST = 1337;
    private static final int REQUEST_FRAGMENT = 1444;



    public HashMap<String, String> step1 = new HashMap<>();




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_account_fragment, container, false);
        btn_pro_logout = view.findViewById(R.id.btn_pro_logout);
        et_pro_name = view.findViewById(R.id.et_pro_name);
        et_pro_email = view.findViewById(R.id.et_pro_email);
        tv_pro_pass=view.findViewById(R.id.tv_pro_pass);
        et_pro_storeName = view.findViewById(R.id.et_pro_storeName);
        et_pro_address = view.findViewById(R.id.et_pro_address);
        et_pro_location = view.findViewById(R.id.et_pro_location);
        et_pro_phone = view.findViewById(R.id.et_pro_phone);
        et_pro_storeDisc = view.findViewById(R.id.et_pro_storeDisc);
        btn_pro_edit = view.findViewById(R.id.btn_pro_edit);
        btn_pro_addv=view.findViewById(R.id.btn_pro_addv);
        iv_pro_img=view.findViewById(R.id.iv_pro_img);

        linear_is_merchant=view.findViewById(R.id.linear_is_merchant);
        relative_conv_merch=view.findViewById(R.id.relative_pro_conv_merch);
        btn_pro_conv_merch=view.findViewById(R.id.btn_pro_conv_merch);


        btn_pro_logout.setOnClickListener(this);
        btn_pro_edit.setOnClickListener(this);
        btn_pro_addv.setOnClickListener(this);
        et_pro_name.setOnClickListener(this);
        tv_pro_pass.setOnClickListener(this);
        et_pro_storeName.setOnClickListener(this);
        et_pro_address.setOnClickListener(this);
        et_pro_location.setOnClickListener(this);
        et_pro_phone.setOnClickListener(this);
        et_pro_storeDisc.setOnClickListener(this);
        iv_pro_img.setOnClickListener(this);

        relative_conv_merch.setOnClickListener(this);



        if(flag){
            //get user from hashMap
            SetUserDetailsFromHashMap();

        }else {
            //get user from shared preference
            SetUserDetailsFromSharedPref();

        }






        // Initialize Google Logout button
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
// Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()), gso);



        return view;
    }

    private View.OnClickListener openMap() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   CreateEvent1Fragment.step1.put("Fees",cost.getText().toString());
                //using checkSelfPermission if you have them
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //you have to request them from user at runtime
                    requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
                } else {
                    mapFragment = MapFragment.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("step1", step1);
                    mapFragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, mapFragment, "map").addToBackStack("map").commit();
                    //   Log.e("latlong", String.valueOf(step2));
                }
            }
        };
    }



    private void SetUserDetailsFromHashMap() {
        user = SharedPrefManager.getInstance(getActivity()).getUser();
        if (user != null) {

            if (user.getToken() != null && !user.getToken().isEmpty()) {
                token = user.getToken();
            }

            if (user.getImage() != null && !user.getImage().isEmpty()) {
                //Loading image using Picasso
                String imageUrl = user.getImage();
//                if(Patterns.WEB_URL.matcher(imageUrl).matches()){
                Picasso.get().load(imageUrl).into(iv_pro_img);
//                }else {
//                    iv_pro_img.setImageURI(Uri.parse(imageUrl));
//                }

            }


            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                et_pro_email.setText(user.getEmail());

            }

            if (user.getIs_merchant()) {
                linear_is_merchant.setVisibility(View.VISIBLE);
                relative_conv_merch.setVisibility(View.GONE);
            } else {
                linear_is_merchant.setVisibility(View.GONE);
                btn_pro_conv_merch.setText("تحويل الحساب لحساب محل تجاري");

            }
            is_merchant = user.getIs_merchant();


            if (user.getName() != null && !user.getName().isEmpty()) {
                et_pro_name.setText(user.getName());

            }

            if (user.getMarket_name() != null && !user.getMarket_name().isEmpty()) {
                et_pro_storeName.setText(user.getMarket_name());

            }

            if (user.getAddress() != null && !user.getAddress().isEmpty()) {
                et_pro_address.setText(user.getAddress());

            }

            if (user.getPhone() != null && !user.getPhone().isEmpty()) {
                et_pro_phone.setText(user.getPhone());

            }

            if (user.getDescription() != null && !user.getDescription().isEmpty()) {
                et_pro_storeDisc.setText(user.getDescription());

            }
            if (user.getLat() != null && !user.getLat().isEmpty() && user.getLng() != null && !user.getLng().isEmpty()) {
                convertLatLngToAdd(user.getLat(), user.getLng());

            }
        }
    }
        private void SetUserDetailsFromSharedPref() {
         user = SharedPrefManager.getInstance(getActivity()).getUser();
        if (user != null) {

            if (user.getToken() != null && !user.getToken().isEmpty()) {
                token = user.getToken();
            }

            if (user.getImage() != null && !user.getImage().isEmpty()) {
                //Loading image using Picasso
                String imageUrl=user.getImage();
//                if(Patterns.WEB_URL.matcher(imageUrl).matches()){
                    Picasso.get().load(imageUrl).into(iv_pro_img);
//                }else {
//                    iv_pro_img.setImageURI(Uri.parse(imageUrl));
//                }

            }


            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                et_pro_email.setText(user.getEmail());

            }

            if(user.getIs_merchant()){
                linear_is_merchant.setVisibility(View.VISIBLE);
                relative_conv_merch.setVisibility(View.GONE);
            }else {
                linear_is_merchant.setVisibility(View.GONE);
                btn_pro_conv_merch.setText("تحويل الحساب لحساب محل تجاري");

            }
            is_merchant = user.getIs_merchant();


            if (user.getName() != null && !user.getName().isEmpty()) {
                et_pro_name.setText(user.getName());

            }

            if (user.getMarket_name() != null && !user.getMarket_name().isEmpty()) {
                et_pro_storeName.setText(user.getMarket_name());

            }

            if (user.getAddress() != null && !user.getAddress().isEmpty()) {
                et_pro_address.setText(user.getAddress());

            }

            if (user.getPhone() != null && !user.getPhone().isEmpty()) {
                et_pro_phone.setText(user.getPhone());

            }

            if (user.getDescription() != null && !user.getDescription().isEmpty()) {
                et_pro_storeDisc.setText(user.getDescription());

            }
            if (user.getLat() != null && !user.getLat().isEmpty() && user.getLng() != null && !user.getLng().isEmpty()) {
                convertLatLngToAdd(user.getLat(), user.getLng());

            }
        }
    }

    private void convertLatLngToAdd(String lat, String lng) {
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        StringBuilder strReturnedAddress;
        double latitude= Double.parseDouble(lat);
        double longitude= Double.parseDouble(lng);

        try {
            if (geocoder.isPresent()) {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (addresses != null&&addresses.size()>0 ) {
                    Address returnedAddress = addresses.get(0);
                     strReturnedAddress = new StringBuilder("");
                    Log.e("MaxAddressLine", String.valueOf(returnedAddress.getMaxAddressLineIndex()));
                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i));
                        Log.e("count", String.valueOf(i));
                    }
                    Log.e("Myaddress",strReturnedAddress.toString());
                    et_pro_location.setText(strReturnedAddress.toString());

//                    mptxt.setText(strReturnedAddress.toString());
                } else {
                    Log.e("Myaddress", "No Address returned!");
                }
//                String locdescSt = mptxt.getText().toString();
                //    mapstep2.put("locdesc", Tools.encodeStr(locdescSt.replaceAll("\\r"," ").replaceAll("\\n"," ")).replace("+", "%20"));
            }
        } catch(IOException e){
            Log.e("MyCurrentaddress", "Canont get Address!");
        }


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyAccountViewModel.class);
        // TODO: Use the ViewModel
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_pro_img:
                openGallery();
                break;
            case R.id.btn_pro_edit:
                if(is_merchant){
                    editMerchantProfile();
                }else {
                    editProfile();
                }
                break;
            case R.id.btn_pro_logout:
                logOut();
                break;
            case R.id.btn_pro_addv:
//                ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
////                    Bundle args = new Bundle();
////                    args.putString("fragment", "item");
////                    addAdvFragment.setArguments(args);
//                activity.getSupportFragmentManager().beginTransaction().add(R.id.items_main_content,itemDetailsFragment ).addToBackStack( "ItemsRecyclerFragment" ).commit();
                ItemsRecyclerFragment itemsRecyclerFragment= new ItemsRecyclerFragment();
                Bundle args = new Bundle();
                args.putString("fragment", "myacc");
                itemsRecyclerFragment.setArguments(args);

                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.my_account_container, itemsRecyclerFragment, "findThisFragment")
                        .addToBackStack(null)
                        .commit();

                break;
            case R.id.et_pro_name:
                et_pro_name.setFocusable(true);
                et_pro_name.setFocusableInTouchMode(true);

                et_pro_storeName.setFocusable(false);
                et_pro_storeName.setFocusableInTouchMode(false);

                et_pro_address.setFocusable(false);
                et_pro_address.setFocusableInTouchMode(false);

                et_pro_phone.setFocusable(false);
                et_pro_phone.setFocusableInTouchMode(false);

                et_pro_storeDisc.setFocusable(false);
                et_pro_storeDisc.setFocusableInTouchMode(false);

                break;
            case R.id.tv_pro_pass:
                openEditPassDialogue();
                break;
            case R.id.et_pro_storeName:
                et_pro_storeName.setFocusable(true);
                et_pro_storeName.setFocusableInTouchMode(true);

                et_pro_name.setFocusable(false);
                et_pro_name.setFocusableInTouchMode(false);

                et_pro_address.setFocusable(false);
                et_pro_address.setFocusableInTouchMode(false);

                et_pro_phone.setFocusable(false);
                et_pro_phone.setFocusableInTouchMode(false);

                et_pro_storeDisc.setFocusable(false);
                et_pro_storeDisc.setFocusableInTouchMode(false);

                break;
            case R.id.et_pro_address:
                et_pro_address.setFocusable(true);
                et_pro_address.setFocusableInTouchMode(true);

                et_pro_storeName.setFocusable(false);
                et_pro_storeName.setFocusableInTouchMode(false);

                et_pro_name.setFocusable(false);
                et_pro_name.setFocusableInTouchMode(false);

                et_pro_phone.setFocusable(false);
                et_pro_phone.setFocusableInTouchMode(false);

                et_pro_storeDisc.setFocusable(false);
                et_pro_storeDisc.setFocusableInTouchMode(false);

                break;
            case R.id.et_pro_location:


                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                MapFragment mapFragment= new MapFragment();
                Bundle bundle = new Bundle();
//                bundle.putString("Lat", lat);
//                bundle.putString("Lng",lng);
                mapFragment.setArguments(bundle);
                mapFragment.setTargetFragment(MyAccountFragment.this, REQUEST_FRAGMENT);
                ft.addToBackStack(null);
                ft.add(R.id.my_account_container, mapFragment, "findThisFragment");
                ft.commit();




//                MapFragment mapFragment= new MapFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("Lat", "123");
//                bundle.getString("Lng","123");
//                mapFragment.setArguments(bundle);

//                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.my_account_container, mapFragment, "findThisFragment")
//                        .addToBackStack(null)
//                        .commit();


                break;
            case R.id.et_pro_phone:
                et_pro_phone.setFocusable(true);
                et_pro_phone.setFocusableInTouchMode(true);

                et_pro_storeName.setFocusable(false);
                et_pro_storeName.setFocusableInTouchMode(false);

                et_pro_address.setFocusable(false);
                et_pro_address.setFocusableInTouchMode(false);

                et_pro_name.setFocusable(false);
                et_pro_name.setFocusableInTouchMode(false);

                et_pro_storeDisc.setFocusable(false);
                et_pro_storeDisc.setFocusableInTouchMode(false);

                break;

            case R.id.et_pro_storeDisc:
                et_pro_storeDisc.setFocusable(true);
                et_pro_storeDisc.setFocusableInTouchMode(true);

                et_pro_storeName.setFocusable(false);
                et_pro_storeName.setFocusableInTouchMode(false);

                et_pro_address.setFocusable(false);
                et_pro_address.setFocusableInTouchMode(false);

                et_pro_phone.setFocusable(false);
                et_pro_phone.setFocusableInTouchMode(false);

                et_pro_name.setFocusable(false);
                et_pro_name.setFocusableInTouchMode(false);

                break;
            case R.id.relative_pro_conv_merch:
                if (is_merchant) {
                    linear_is_merchant.setVisibility(View.GONE);
                    btn_pro_conv_merch.setText("تحويل الى حساب محل تجاري ");
                    is_merchant=false;
                }else {
                linear_is_merchant.setVisibility(View.VISIBLE);
                    btn_pro_conv_merch.setText("العوده الى حساب عادي");
                    is_merchant=true;
                }
                break;



        }

    }


    private void openGallery() {
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_PHOTO_FOR_AVATAR && null != data) {
                if (resultCode == RESULT_OK) {

                    selectedImage = data.getData();
                    try {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), selectedImage);
                        Bitmap converetdImage = getResizedBitmap(bitmap, 500);


                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        converetdImage.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                        byte[] b = baos.toByteArray();


                        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    iv_pro_img.setImageURI(selectedImage);
                }
            }
//            } else {
//                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
//            }


//            try {
//                final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(RegistrationActivityOne.this, "Something went wrong", Toast.LENGTH_LONG).show();
//            }
            else if (requestCode == REQUEST_FRAGMENT && null != data) {
                if (resultCode == RESULT_OK) {
                    lat = data.getStringExtra("Lat");
                lng = data.getStringExtra("Lng");
                Log.e("mapLat", lat);
                Log.e("mapLng", lng);

                convertLatLngToAdd(lat, lng);
            }
        }
    }
    /**
     * reduces the size of the image
     * @param image
     * @param maxSize
     * @return
     */
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }





    private void openEditPassDialogue() {
            dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_reset_password);


          et_dia_oldPass = dialog.findViewById(R.id.et_dia_oldPass);
          et_dia_newPass = dialog.findViewById(R.id.et_dia_newPass);
          et_dia_repPass = dialog.findViewById(R.id.et_dia_repPass);

         Button btn_dialog_ok = dialog.findViewById(R.id.btn_dialog_ok);
         Button btn_dialog_cancel = dialog.findViewById(R.id.btn_dialog_cancel);



        btn_dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password=et_dia_oldPass.getText().toString();
                String new_password=et_dia_newPass.getText().toString();
                String new_password_confirmation=et_dia_repPass.getText().toString();

                resetPassword(old_password,new_password,new_password_confirmation);

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

    private void resetPassword(String old_password, String new_password, String new_password_confirmation) {

        Call<Object> call= RetrofitClient.
                getInstance()
                .getApi()
                .reset_password(old_password,new_password,new_password_confirmation);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e("gson:reset_password", new Gson().toJson(response.body()) );

                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("res:reset_password", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
                            if (message != null) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                if(dialog!=null)
                                dialog.dismiss();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                } else if (response.errorBody() != null) {
                    try {
                        Log.e("gson:reset_pass_error", response.errorBody().string());
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



        @Override
        public void onFailure(Call<Object> call, Throwable t) {
            Log.e("reset_pass:onFailure", String.valueOf(t));

        }
    });
//

}
    private void editProfile() {
        et_pro_name.setFocusable(false);
        et_pro_name.setFocusableInTouchMode(false);

        String name = et_pro_name.getText().toString();
        String email=et_pro_email.getText().toString();
        is_merchant=false;
        editProfileApi(name,email,is_merchant);
        if(encodedImage!=null)
            AddImageProfileApi(encodedImage, selectedImage);

    }



    private void editMerchantProfile() {

        et_pro_name.setFocusable(false);
        et_pro_name.setFocusableInTouchMode(false);

        et_pro_storeName.setFocusable(false);
        et_pro_storeName.setFocusableInTouchMode(false);

        et_pro_address.setFocusable(false);
        et_pro_address.setFocusableInTouchMode(false);

        et_pro_phone.setFocusable(false);
        et_pro_phone.setFocusableInTouchMode(false);

        et_pro_storeDisc.setFocusable(false);
        et_pro_storeDisc.setFocusableInTouchMode(false);

        String name = et_pro_name.getText().toString();
        String email=et_pro_email.getText().toString();
        String market_name=et_pro_storeName.getText().toString();
        String address=et_pro_address.getText().toString();
        String location=et_pro_location.getText().toString();
        String phone=et_pro_phone.getText().toString();
        String description=et_pro_storeDisc.getText().toString();

        editMerchantProfileApi(name,email,market_name,address,lat,lng,phone,description);
        if(encodedImage!=null)
        AddImageProfileApi(encodedImage, selectedImage);
    }

    private void editProfileApi(String name, String email, Boolean is_merchant) {
        Call<Object> call= RetrofitClient.
                getInstance()
                .getApi()
                .edit_profile(name,email,false);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:edit_profile", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");
                            if (msg != null) {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                if (msg.equalsIgnoreCase("تم تعديل البيانات بنجاح")) {
                                    SharedPrefManager.getInstance(getActivity()).clear();
                                    User user = new User(token,name, email, MyAccountFragment.this.is_merchant);
                                    SharedPrefManager.getInstance(getActivity())
                                            .saveUser(user);
                                    Log.e("param:edit_profile", String.valueOf(user));

//                        get user from shared preference
                                    SetUserDetailsFromSharedPref();
                                    Intent intent_log = new Intent(getActivity(), MainActivity.class);
                                    intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent_log);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:edit_profile_error", response.errorBody().string());
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("edit_profile:onFailure", String.valueOf(t));

            }
        });

    }



    private void editMerchantProfileApi(String name, String email, String market_name, String address, String lat,String lng, String phone, String description) {


        Call<Object> call= RetrofitClient.
                getInstance()
                .getApi()
                .edit_merchant_profile(name,email,market_name,true,address,lat,lng,phone,description);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:edit_merch_profile", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");
                            if (msg != null) {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                if (msg.equalsIgnoreCase("تم تعديل البيانات بنجاح")) {
                                SharedPrefManager.getInstance(getActivity()).clear();
                                User user = new User(token,name, email,is_merchant, market_name, address, lat, lng, phone, description);
                                    SharedPrefManager.getInstance(getActivity())
                                            .saveUser(user);
                        Log.e("param:edit_merch_pro", String.valueOf(user));

//                        get user from shared preference
                                SetUserDetailsFromSharedPref();
                                    Intent intent_log = new Intent(getActivity(), MainActivity.class);
                                    intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent_log);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:edit_merch_pro_err", response.errorBody().string());
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("edt_merch_pro:onFailure", String.valueOf(t));

            }
        });
//


    }

        private void AddImageProfileApi(String encodedImage, Uri selectedImage) {
            Call<Object> call= RetrofitClient.
                getInstance().getApi()
                .storeImage(encodedImage);
            call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:Change_Image", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");

                            if (msg != null) {
                                if (msg.equalsIgnoreCase("تم تغيير الصورة بنجاح")) {

                                }
                                JSONObject data = jsonObject.getJSONObject("data");
                                if (data != null) {
                                    String imageUrl = data.getString("image");
                                    SharedPrefManager.getInstance(getActivity()).editSingleValue("image", imageUrl);

                                }
//                                Toast.makeText(RegistrationActivityTow.this, msg, Toast.LENGTH_SHORT).show();
                                Log.e("Change_Image", String.valueOf(selectedImage));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:Change_Image", response.errorBody().string());
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call<Object> call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("ChangImgByApi:onFailure", String.valueOf(t));

            }
        });


    }



    private void logOut() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Toast.makeText(getActivity(), "User Sign out!", Toast.LENGTH_SHORT).show();
        logOutApi();
//        startActivity(new Intent(getActivity(), LoginActivity.class));
        Intent intent_log =new Intent(getActivity(), LoginActivity.class);
        intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent_log);

        Objects.requireNonNull(getFragmentManager()).popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);



        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();



        // Google sign out
        if(mGoogleSignInClient!=null){
            mGoogleSignInClient.signOut().addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
//                    Toast.makeText(getActivity(), "User Sign out gmail!", Toast.LENGTH_SHORT).show();
                    Log.e("gmail","User Sign out");

                }
            });

        }
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(getActivity(), "User Sign out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            Objects.requireNonNull(getFragmentManager()).popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }

    }

    private void logOutApi() {
        Call<Object> call= RetrofitClient.
                getInstance().getApi()
                .logout();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:logout", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");

//                            if (msg != null) {
//                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//                            }
//

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:errorlogout", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call<Object> call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("logout:onFailure", String.valueOf(t));

            }
        });


    }

    /**
     * Receive the result from a previous call to
     * {@link #startActivityForResult(Intent, int)}.  This follows the
     * related Activity API as described there in
     *
//     * @param requestCode The integer request code originally supplied to
//     *                    startActivityForResult(), allowing you to identify who this
//     *                    result came from.
//     * @param resultCode  The integer result code returned by the child activity
//     *                    through its setResult().
//     * @param data        An Intent, which can return result data to the caller
     */
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//    }

    @Override
    public void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()){
            Intent intent_log =new Intent(getActivity(), MainActivity.class);
            intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent_log);

        }
    }

}
