package com.souqmaftoh.basatashopping.Api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GoogleService {

        @POST("token")
        @FormUrlEncoded
        @Headers("Content-Type:application/x-www-form-urlencoded")
        Call<Object> getToken(
                @Field("grant_type") String grantType,
                @Field("client_id") String clientId,
                @Field("client_secret") String clientSecret,
                @Field("redirect_uri") String redirectUri,
                @Field("code") String code);
    }


