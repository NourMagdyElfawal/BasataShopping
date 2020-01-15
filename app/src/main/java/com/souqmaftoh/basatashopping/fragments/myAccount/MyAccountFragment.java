package com.souqmaftoh.basatashopping.fragments.myAccount;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.souqmaftoh.basatashopping.Fonts.LatoBLack;
import com.souqmaftoh.basatashopping.LoginActivity;
import com.souqmaftoh.basatashopping.LoginByEmailActivity;
import com.souqmaftoh.basatashopping.MainActivity;
import com.souqmaftoh.basatashopping.Models.User;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;

import java.util.Objects;

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


     LatoBLack btn_pro_logout;
    private GoogleSignInClient mGoogleSignInClient;
    EditText et_pro_name,et_pro_email,et_pro_storeName,et_pro_address,et_pro_location,et_pro_phone,et_pro_storeDisc;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.my_account_fragment, container, false);
        btn_pro_logout=view.findViewById(R.id.btn_pro_logout);
        et_pro_name=view.findViewById(R.id.et_pro_name);
        et_pro_email=view.findViewById(R.id.et_pro_email);
        et_pro_storeName=view.findViewById(R.id.et_pro_storeName);
        et_pro_address=view.findViewById(R.id.et_pro_address);
        et_pro_location=view.findViewById(R.id.et_pro_location);
        et_pro_phone=view.findViewById(R.id.et_pro_phone);
        et_pro_storeDisc=view.findViewById(R.id.et_pro_storeDisc);


        btn_pro_logout.setOnClickListener(this);

        User user=SharedPrefManager.getInstance(getActivity()).getUser();
        et_pro_name.setText(user.getName());
        et_pro_email.setText(user.getEmail());


        return view;
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
            case R.id.btn_pro_logout:
                logOut();

        }

    }

    private void logOut() {
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

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(getActivity(), "User Sign out!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            Objects.requireNonNull(getFragmentManager()).popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }

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
