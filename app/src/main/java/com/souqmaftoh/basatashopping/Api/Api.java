package com.souqmaftoh.basatashopping.Api;

import com.souqmaftoh.basatashopping.Models.DefaultResponse;
import com.souqmaftoh.basatashopping.Models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("register")
    Call<DefaultResponse> createUser(
        @Field("name") String name,
        @Field("email") String email,
        @Field("password") String password,
        @Field("password_confirmation") String password_confirmation
    );


    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> userLogin(
         @Field("email") String email,
         @Field("password") String password
    );
}

