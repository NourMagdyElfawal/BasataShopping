package com.souqmaftoh.basatashopping.Api;

import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.Models.DefaultResponse;
import com.souqmaftoh.basatashopping.Models.ForgetPassResponse;
import com.souqmaftoh.basatashopping.Models.LoginDefault;
import com.souqmaftoh.basatashopping.Models.LoginResponse;
import com.souqmaftoh.basatashopping.Models.RegisterationUserResponse;
import com.souqmaftoh.basatashopping.Models.RegistrationStoreResponse;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

            @Headers({
            "Accept: application/json",
//            "Content-Type: application/json",
//            "Authorization: Bearer token"
    })
    @FormUrlEncoded
    @POST("register")
    Call<Object> createUser(
        @Field("name") String name,
        @Field("email") String email,
        @Field("password") String password,
        @Field("password_confirmation") String password_confirmation

        );

    @Headers({
            "Accept: application/json",
//            "Content-Type: application/json",
//            "Authorization: Bearer token"
    })
    @FormUrlEncoded
    @POST("register")
    Call<Object> createStore(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation,
            @Field("market_name") String market_name,
            @Field("address") String address,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("phone") String phone,
            @Field("description") String description

    );



    @Headers({
            "Accept: application/json",
//            "Content-Type: application/json",
    })
    @FormUrlEncoded
    @POST("login")
    Call<Object> userLogin(
         @Field("email") String email,
         @Field("password") String password,
         @Field("device_id") String device_id,
         @Field("push_token") String push_token
    );

    @Headers({
            "Accept: application/json",
//            "Content-Type: application/json",
    })
    @FormUrlEncoded
    @POST("forget_password")
    Call<ForgetPassResponse> userForgetPassword(
            @Field("email") String email
    );

    @Headers({
            "Accept: application/json",
//            "Content-Type: application/json",
//            "Authorization: Bearer token"
    })
    @FormUrlEncoded
    @POST("edit_profile?")
    Call<String> edit_profile(
                    @Field("name") String name,
                    @Field("email") String email,
                    @Field("market_name") String market_name,
                    @Field("is_merchant") Boolean is_merchant,
                    @Field("address") String address,
                    @Field("lat") String lat,
                    @Field("lng") String lng,
                    @Field("phone") String phone,
                    @Field("description") String description

    );

    @Headers({
            "Accept: application/json",
//            "Content-Type: application/json"
//            "Authorization: Bearer token"
    })
    @FormUrlEncoded
    @POST("create_ad")
    Call<Object> createAd(
            @Field("title") String title,
            @Field("price") int price,
            @Field("description") String description,
            @Field("sub_category") int sub_category,
            @Field("item_condition") String item_condition,
            @Field("main_image") String main_image

    );


    @Headers({
            "Accept: application/json",
//            "Content-Type: application/json"
//            "Authorization: Bearer token"
    })
    @FormUrlEncoded
    @POST("reset_password")
    Call<Object> reset_password(
            @Field("old_password") String old_password,
            @Field("new_password") String new_password,
            @Field("new_password_confirmation") String new_password_confirmation

    );


    @Headers({
            "Accept: application/json",
//            "Content-Type: application/json"
//            "Authorization: Bearer token"
    })
    @GET("get_my_ads?page=1")
    Call<Object> get_my_ads();

}


