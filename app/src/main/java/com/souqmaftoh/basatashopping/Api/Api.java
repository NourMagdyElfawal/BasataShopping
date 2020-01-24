package com.souqmaftoh.basatashopping.Api;

import com.souqmaftoh.basatashopping.Models.DefaultResponse;
import com.souqmaftoh.basatashopping.Models.ForgetPassResponse;
import com.souqmaftoh.basatashopping.Models.LoginDefault;
import com.souqmaftoh.basatashopping.Models.LoginResponse;
import com.souqmaftoh.basatashopping.Models.RegisterationUserResponse;
import com.souqmaftoh.basatashopping.Models.RegistrationStoreResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("register")
    Call<Object> createUser(
        @Field("name") String name,
        @Field("email") String email,
        @Field("password") String password,
        @Field("password_confirmation") String password_confirmation
    );


    @FormUrlEncoded
    @POST("login")
    Call<Object> userLogin(
         @Field("email") String email,
         @Field("password") String password
    );


    @FormUrlEncoded
    @POST("forget_password")
    Call<ForgetPassResponse> userForgetPassword(
            @Field("email") String email
    );


    @FormUrlEncoded
    @POST("edit_profile?")
    Call<String> createStore(
                    @Field("name") String name,
                    @Field("email") String email,
                    @Field("market_name") String market_name,
                    @Field("address") String address,
                    @Field("lat") String lat,
                    @Field("lng") String lng,
                    @Field("phone") String phone,
                    @Field("description") String description

    );


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

}


