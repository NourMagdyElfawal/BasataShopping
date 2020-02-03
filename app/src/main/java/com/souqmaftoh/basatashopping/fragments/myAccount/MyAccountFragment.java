package com.souqmaftoh.basatashopping.fragments.myAccount;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Fonts.LatoBLack;
import com.souqmaftoh.basatashopping.LoginActivity;
import com.souqmaftoh.basatashopping.LoginByEmailActivity;
import com.souqmaftoh.basatashopping.MainActivity;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;
import com.souqmaftoh.basatashopping.fragments.ItemsRecyclerFragment.ItemsRecyclerFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    LatoBLack btn_pro_logout, btn_pro_edit,btn_pro_addv;
    private GoogleSignInClient mGoogleSignInClient;
    EditText et_pro_name, et_pro_email, et_pro_storeName, et_pro_address, et_pro_location, et_pro_phone, et_pro_storeDisc;
    TextView tv_pro_pass;
    String token;
    Boolean is_merchant;
    EditText et_dia_oldPass,et_dia_newPass,et_dia_repPass;

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


        //get user from shared preference
        SetUserDetailsSharedPref();


        return view;
    }

    private void SetUserDetailsSharedPref() {
        User user = SharedPrefManager.getInstance(getActivity()).getUser();
        if (user != null) {

             if (user.getToken() != null && !user.getToken().isEmpty()) {
                 token = user.getToken();
             }

}            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                et_pro_email.setText(user.getEmail());

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
            if (user.getLat() != null && !user.getLat().isEmpty()) {
                et_pro_location.setText(user.getLat());

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
            case R.id.btn_pro_edit:
                editProfile();
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



        }

    }

    private void openEditPassDialogue() {
            final Dialog dialog = new Dialog(getContext());
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
            Log.e("RegByApiTow:onFailure", String.valueOf(t));

        }
    });
//

}


    private void editProfile() {

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
        String lat="456";
        String lng="234";

        editProfileApi(name,email,market_name,address,lat,lng,phone,description);
    }

    private void editProfileApi(String name, String email, String market_name, String address, String lat,String lng, String phone, String description) {


        Call<String> call= RetrofitClient.
                getInstance()
                .getApi()
                .edit_profile(name,email,market_name,address,lat,lng,phone,description);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:edit_profile", new Gson().toJson(response.body()));
//                        try {
//                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
//                            String msg = jsonObject.getString("message");
//                            if (msg != null) {
//                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//                                if (msg.equalsIgnoreCase("تم تعديل البانات بنجاح")) {
//                                SharedPrefManager.getInstance(getActivity()).clear();
                                User user = new User(token,name, email,is_merchant, market_name, address, lat, lng, phone, description);
                                    SharedPrefManager.getInstance(getActivity())
                                            .saveUser(user);
                                //get user from shared preference
//                                SetUserDetailsSharedPref();
//                                    Intent intent_log = new Intent(getActivity(), MainActivity.class);
////                                    intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent_log);
//                                }
//                            }


//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }


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
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("RegByApiTow:onFailure", String.valueOf(t));

            }
        });
//


    }


    private void logOut() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Toast.makeText(getActivity(), "User Sign out!", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(getActivity(), LoginActivity.class));
        Intent intent_log =new Intent(getActivity(), LoginActivity.class);
        intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent_log);

        Objects.requireNonNull(getFragmentManager()).popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);



        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();



//        // Google sign out
//        if(mGoogleSignInClient!=null)
//        mGoogleSignInClient.signOut();

//        // Google sign out
//        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(@NonNull Status status) {
//                        //do what you want
//                        Toast.makeText(getActivity(), "User Sign out gmail!", Toast.LENGTH_SHORT).show();
//
//                    }
//                });

//        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
//            Toast.makeText(getActivity(), "User Sign out!", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//            Objects.requireNonNull(getFragmentManager()).popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//
//        }

    }

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
