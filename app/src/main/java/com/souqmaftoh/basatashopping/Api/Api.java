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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

            @Headers({
            "Accept: application/json",
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
//            "Authorization: Bearer token"
    })
    @FormUrlEncoded
    @POST("change_image")
    Call<Object> storeImage(
            @Field("image") String image
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
    Call<Object> edit_profile(
                    @Field("name") String name,
                    @Field("email") String email,
                    @Field("is_merchant") Boolean is_merchant

                    );

    @Headers({
            "Accept: application/json",
//            "Content-Type: application/json",
//            "Authorization: Bearer token"
    })
    @FormUrlEncoded
    @POST("edit_profile?")
    Call<Object> edit_merchant_profile(
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
    })
    @FormUrlEncoded
    @POST("edit_ad")
    Call<Object> editAd(
            @Field("ad_key") String ad_key,
            @Field("title") String title,
            @Field("price") int price,
            @Field("description") String description,
            @Field("main_image") String main_image
            );


    @Headers({
            "Accept: application/json",
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
    })
    @FormUrlEncoded
    @POST("add_social_link")
    Call<Object> add_social_link(
            @Field("type") String type,
            @Field("link") String link
    );

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("remove_social_link")
    Call<Object> remove_social_link(
            @Field("type") String type,
            @Field("link") String link
    );



    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("deactivate_ad")
    Call<Object> deactivate_ad(
            @Field("ad_key") String ad_key
    );
    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("activate_ad")
    Call<Object> activate_ad(
            @Field("ad_key") String ad_key
    );


    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("set_as_sold")
    Call<Object> set_as_sold(
            @Field("ad_key") String ad_key
    );

    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("delete_ad")
    Call<Object> delete_ad(
            @Field("ad_key") String ad_key
    );

    @Headers({
            "Accept: application/json",
    })
    @GET("categories")
    Call<Object> get_categories();



    @Headers({
            "Accept: application/json",
    })
    @GET("subcategories/{categoriesId}")
    Call<Object> get_subcategories(
            @Path("categoriesId") int categoriesId

    );



    @Headers({
            "Accept: application/json",
    })
    @GET("get_my_ads")
    Call<Object> get_my_ads();


    @Headers({
            "Accept: application/json",
    })
    @GET("search_ads")
    Call<Object> search_ads();



    @Headers({
            "Accept: application/json",
    })
    @GET("get_ad/{ad_key}")
    Call<Object> get_ad(
            @Path("ad_key") String ad_key
    );


    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("social-user")
    Call<Object> social_user(
            @Field("token") String token,
            @Field("type") String type,
            @Field("device_id") String device_id,
            @Field("push_token") String push_token

    );


    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("create_offer")
    Call<Object> create_offer(
            @Field("ad_key") String ad_key,
            @Field("price") String price

    );


    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("delete_offer")
    Call<Object> delete_offer(
            @Field("ad_key") String ad_key
    );



    @Headers({
            "Accept: application/json",
    })
    @POST("logout")
    Call<Object> logout();


    @Headers({
            "Accept: application/json",
    })
    @FormUrlEncoded
    @POST("set_as_favorite")
    Call<Object> set_as_favorite(
            @Field("ad_key") String ad_key
    );





}


