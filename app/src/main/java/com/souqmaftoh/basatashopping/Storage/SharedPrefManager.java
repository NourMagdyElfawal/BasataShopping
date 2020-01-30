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
    public SharedPrefManager() {
    }


    public static synchronized SharedPrefManager getInstance(Context mCtx){
        if(mInstance ==null){
            mInstance=new SharedPrefManager(mCtx);
        }
        return mInstance;
    }


    public static SharedPrefManager getInstance() {
        if(mInstance ==null){
            mInstance=new SharedPrefManager();
        }
        return mInstance;

    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("token", user.getToken());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());
        editor.putString("image", user.getImage());
        editor.putBoolean("is_merchant",user.getIs_merchant());
        editor.putString("market_name", user.getMarket_name());
        editor.putString("address", user.getAddress());
        editor.putString("lat", user.getLat());
        editor.putString("lng", user.getLng());
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
                sharedPreferences.getString("token",null),
                sharedPreferences.getString("name",null),
                sharedPreferences.getString("email",null),
                sharedPreferences.getString("image",null),
                sharedPreferences.getBoolean("is_merchant",false),
                sharedPreferences.getString("market_name",null),
                sharedPreferences.getString("address",null),
                sharedPreferences.getString("lat",null),
                sharedPreferences.getString("lng",null),
                sharedPreferences.getString("phone",null),
                sharedPreferences.getString("description",null)
        );
    }


    public void clear(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

    }

    public void removeSingleValue(String KeyName){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(KeyName).apply();


    }

}
