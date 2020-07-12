package com.souqmaftoh.basatashopping;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Interface.Categories;
import com.souqmaftoh.basatashopping.Interface.SubCategory;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class ApiFunctions {

//    public static void getCategoryApi() {
//
//        ArrayList<Categories> mCategories = new ArrayList<>();
//
//        Call call= RetrofitClient.
//                getInstance()
//                .getApi()
//                .get_categories();
//        call.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
//                Log.e("gson:get_categories", new Gson().toJson(response.body()) );
//
//                if(response!=null) {
//
//                    if (response.body() != null) {
//                        Log.e("res:get_categories", "isSuccessful");
//                        try {
//                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
//                            String message = jsonObject.getString("message");
////                            if (message != null&&!message.isEmpty()) {
////                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
////                            }
//                            JSONObject jsonData  = jsonObject.getJSONObject("data");
//                            JSONArray arrJson = jsonData.getJSONArray("data");
//                            JSONObject[] arr=new JSONObject[arrJson.length()];
//
//                            for(int i = 0; i < arrJson.length(); i++) {
//                                arr[i] = arrJson.getJSONObject(i);
//                                Log.e("tag", String.valueOf(arr[i]));
//                                String category=arr[i].getString("name_ar");
//                                int id=arr[i].getInt("id");
//
//                                mCategories.add(new Categories(category,id));
//                            }
//                            Log.e("category", String.valueOf(mCategories));
////                            if(mCategories!=null){
////                                spinnerCategories(mCategories);
////                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
////
//                    }
//
//                } else if (response.errorBody() != null) {
//                    try {
//                        Log.e("gson:categories_error", response.errorBody().string());
////                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//
//
//
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Log.e("categories:onFailure", String.valueOf(t));
//
//            }
//        });
//
//
//    }


//    private void getSubCategoryApi(int categoryId) {
//
//        ArrayList<SubCategory> mSubCategories = new ArrayList<>();
//
//        Call call= RetrofitClient.
//                getInstance()
//                .getApi()
//                .get_subcategories(categoryId);
//        call.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
//                Log.e("gson:get_subcategories", new Gson().toJson(response.body()) );
//
//                if(response!=null) {
//
//                    if (response.body() != null) {
//                        Log.e("res:get_subcategories", "isSuccessful");
//                        try {
//                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
//                            String message = jsonObject.getString("message");
//                            if (message != null&&!message.isEmpty()) {
////                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                            }
//                            JSONObject jsonData  = jsonObject.getJSONObject("data");
//                            JSONArray arrJson = jsonData.getJSONArray("data");
//                            JSONObject[] arr=new JSONObject[arrJson.length()];
//
//                            for(int i = 0; i < arrJson.length(); i++) {
//                                arr[i] = arrJson.getJSONObject(i);
//                                Log.e("tag", String.valueOf(arr[i]));
//                                String subcategories=arr[i].getString("name_ar");
//                                int id=arr[i].getInt("id");
//                                mSubCategories.add(new SubCategory(subcategories,id));
//                            }
//                            Log.e("subcategories", String.valueOf(mSubCategories));
////                            if(mSubCategories!=null){
////                                spinnerSubCategories(mSubCategories);
////                            }
//
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
////
//                    }
//
//                } else if (response.errorBody() != null) {
//                    try {
//                        Log.e("gson:categories_error", response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//
//
//
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Log.e("categories:onFailure", String.valueOf(t));
//
//            }
//        });
//
//
//
//
//    }
//
//}
