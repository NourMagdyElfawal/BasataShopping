package com.souqmaftoh.basatashopping.Api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL="http://alasy-edu.com/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    private String token;

//    OkHttpClient client = new OkHttpClient.Builder()
//            .addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request.Builder ongoing = chain.request().newBuilder();
////                    ongoing.addHeader("Accept", "application/json;versions=1");
//                    if (SharedPrefManager.getInstance().getUser()!=null) {
//                        User user = SharedPrefManager.getInstance().getUser();
//                        if (user.getToken() != null && !user.getToken().isEmpty()) {
//                            token=user.getToken();
//                            ongoing.addHeader("Authorization", token);
//
//                        }
//
//                    }
//                    return chain.proceed(ongoing.build());
//                }
//            })
//            .build();

//    private boolean isUserLoggedIn() {
//        User user = SharedPrefManager.getInstance().getUser();
//        if (user != null) {
//            if (user.getToken() != null && !user.getToken().isEmpty()) {
//                token=user.getToken();
//            }
//            return true;
//
//        }else {
//            return false;
//        }
//    }



    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
                                if (SharedPrefManager.getInstance().getUser()!=null) {
                                    User user = SharedPrefManager.getInstance().getUser();
                                    if (user.getToken() != null && !user.getToken().isEmpty()) {
                                        token = user.getToken();
                                        Log.e("token",token);
                                    }
                                }

            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();


    private RetrofitClient(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

                 retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

    }
    public static synchronized RetrofitClient getInstance(){
        if (mInstance==null){
            mInstance=new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi(){

        return retrofit.create(Api.class);
    }

}

