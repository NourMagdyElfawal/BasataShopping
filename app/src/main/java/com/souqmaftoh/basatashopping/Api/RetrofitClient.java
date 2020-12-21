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
    private static final String BASE_URL="https://basatamobile.com/api/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;
    private String token;


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

