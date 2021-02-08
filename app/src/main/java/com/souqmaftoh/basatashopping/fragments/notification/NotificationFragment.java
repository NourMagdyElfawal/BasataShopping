package com.souqmaftoh.basatashopping.fragments.notification;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Adapter.ItemsAdapter;
import com.souqmaftoh.basatashopping.Adapter.MyFavoritesAdapter;
import com.souqmaftoh.basatashopping.Adapter.MyItemsAdapter;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Interface.Items;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.design.DividerItemDecoration;
import com.souqmaftoh.basatashopping.fragments.ItemsRecyclerFragment.ItemsRecyclerViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    private NotificationViewModel mViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView mRecyclerView;
    MyFavoritesAdapter mSportAdapter;
    private Activity mActivity;


    LinearLayoutManager mLayoutManager;


    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SmsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }





    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.notification_fragment, container, false);

        mRecyclerView=view.findViewById(R.id.mRecyclerView);

        setUpListOfMyFavoritesItems();

        return view;
    }

    private void setUpListOfMyFavoritesItems() {

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mSportAdapter = new MyFavoritesAdapter(new ArrayList<>());

//        prepareDemoContent();

        getMyFavoritesApi();

    }

    private void getMyFavoritesApi() {

        ArrayList<Items> mSports = new ArrayList<>();


        Call<Object> call= RetrofitClient.
                getInstance()
                .getApi()
                .get_my_favorites();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                Log.e("gson:get_my_favorites", new Gson().toJson(response.body()) );

                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("res:get_my_favorites", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
//                            String message = jsonObject.getString("message");
//                            if (message != null&&!message.isEmpty()) {
//                                Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
//                            }
                            JSONArray arrJson  = jsonObject.getJSONArray("data");
                            Log.e("arrJson", String.valueOf(arrJson));

                            JSONObject[] arr=new JSONObject[arrJson.length()];

                            for(int i = 0; i < arrJson.length(); i++) {
                                arr[i] = arrJson.getJSONObject(i);
                                Log.e("tag", String.valueOf(arr[i]));
                                String ad_key=arr[i].getString("ad_key");
                                String owner_name=arr[i].getString("owner_name");
                                String title=arr[i].getString("title");
                                String description=arr[i].getString("description");
                                String item_condition=arr[i].getString("item_condition");
                                int price=arr[i].getInt("price");
                                String subcategory=arr[i].getString("subcategory");
                                String image=arr[i].getString("image");
                                String status=arr[i].getString("status");
                                mSports.add(new Items(ad_key,image,item_condition , title, price,subcategory,status,owner_name,description));
                            }
                            mSportAdapter.addItems(mSports);
                            mRecyclerView.setAdapter(mSportAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
                    }

                } else if (response.errorBody() != null) {
                    try {
                        Log.e("gson:get_my_fav_error", response.errorBody().string());
                        Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("get_my_fav:onFailure", String.valueOf(t));

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivity = getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    private void doAction() {
        if (mActivity == null) {
            return;
        }
    }


}
