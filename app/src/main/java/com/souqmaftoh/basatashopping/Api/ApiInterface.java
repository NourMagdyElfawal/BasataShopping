package com.souqmaftoh.basatashopping.Api;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers({
            "Accept: application/json",
    })
    @POST("fcm/send")
    Call<Object> sendNotification();
//            @Body RootModel root);
}
