package com.souqmaftoh.basatashopping.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.souqmaftoh.basatashopping.Interface.User;

public class SharedPrefManager {


    private static SharedPrefManager mInstance;
    private Context mCtx;
    private static final String SHARED_PREF_NAME="my_shared_pref";

    public SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }


    public static synchronized SharedPrefManager getInstance(Context mCtx){
        if(mInstance ==null){
            mInstance=new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("image", user.getImage());
        editor.putString("market_name", user.getMarket_name());
        editor.putString("address", user.getAddress());
        editor.putString("lat", user.getLat());
        editor.putString("phone", user.getPhone());
        editor.putString("description", user.getDescription());

        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id",-1)!=-1;
    }

    public User getUser(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("name",null),
                sharedPreferences.getString("image",null),
                sharedPreferences.getString("market_name",null),
                sharedPreferences.getString("address",null),
                sharedPreferences.getString("lat",null),
                sharedPreferences.getString("phone",null),
                sharedPreferences.getString("description",null)
        );
    }


    public void cleare(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
    }

}
