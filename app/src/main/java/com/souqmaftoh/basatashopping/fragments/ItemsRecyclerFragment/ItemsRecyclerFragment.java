package com.souqmaftoh.basatashopping.fragments.ItemsRecyclerFragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Adapter.HomeAdapter;
import com.souqmaftoh.basatashopping.Adapter.ItemsAdapter;
import com.souqmaftoh.basatashopping.Adapter.MyItemsAdapter;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Interface.Items;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.design.DividerItemDecoration;
import com.souqmaftoh.basatashopping.fragments.addAdv.AddAdvFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsRecyclerFragment extends Fragment {

    private ItemsRecyclerViewModel mViewModel;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    MyItemsAdapter mSportAdapter;
    ItemsAdapter itemsAdapter;

    LinearLayoutManager mLayoutManager;

//    List<category> historicList = new ArrayList<>();

    private RecyclerView recyclerView;
    private LinearLayout layout_icons;
    HomeAdapter homeAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2,fragmentName;
    private boolean flag=false;
    int subCategoryId;
    AlertDialog.Builder builder;
    String selected;
    String itemCond;
    private Activity mActivity;




    public ItemsRecyclerFragment() {
        // Required empty public constructor
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
    public static ItemsRecyclerFragment newInstance(String param1, String param2) {
        ItemsRecyclerFragment fragment = new ItemsRecyclerFragment();
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
            if(getArguments().getString("fragment")!=null) {
                if (getArguments().getString("fragment").equalsIgnoreCase("myacc")) {
                    flag = true;

                }else {
                    flag = false;
                }
            }

            subCategoryId = getArguments().getInt("subCategoryId");
            Log.e("subCategoryId", String.valueOf(subCategoryId));

        }
    }


    public static ItemsRecyclerFragment newInstance() {
        return new ItemsRecyclerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.items_recycler_fragment, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)mActivity).setSupportActionBar(toolbar);
        ((AppCompatActivity)mActivity).setTitle("حالة المنتج");
        toolbar.setNavigationIcon(R.drawable.ic_list_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseItemCondition();
                }
        });

        // Spinner element
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner_nav);
        Button button=(Button)view.findViewById(R.id.button);


//        getMyAdsApi();

        if(flag){
            spinner.setVisibility(View.GONE);
            setUpListOfMyItems();
        }else {
            spinner.setVisibility(View.VISIBLE);
            // Spinner click listener
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    String items=spinner.getSelectedItem().toString();
                    Log.e("Selected item : ",items);
                    Log.e("Selected position : ", String.valueOf(position));
                    String orderBy;
                    switch (position) {
                        case 0:
                            itemCond="all";
                            setUpListOfItemsByDefault(itemCond);
                            break;

                        case 1:
                            orderBy="price_desc";
                            setUpListOfItems(orderBy);
                            break;

                        case 2:
                            orderBy="price_asc";
                            setUpListOfItems(orderBy);

                            break;


                        case 3:
                            orderBy="rate_desc";
                            setUpListOfItems(orderBy);
                            break;

                        case 4:
                            orderBy="rate_asc";
                            setUpListOfItems(orderBy);
                            break;


                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
//                setUpListOfItemsByDefault();

                }
            });

            // Spinner Drop down elements
            List<String> categories = new ArrayList<String>();
            categories.add("الاحدث               ");
            categories.add("السعر: من الأعلى للأقل");
            categories.add("السعر: من الأقل للأعلى");
            categories.add("التقييم: من الأعلى للأقل");
            categories.add("التقييم: من الأقل للأعلى");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);

        }



        ButterKnife.bind(this,view);

        return view;
    }

    private void choseItemCondition() {
        CharSequence[] array = {"جديد", "مستعمل", "الجميع"};

        builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("حاله المنتج")
                .setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        selected = array[arg1].toString();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        switch (selected){
                            case "جديد":
                            setUpListOfItemsByDefault("new");
                                break;
                            case "مستعمل":
                                setUpListOfItemsByDefault("old");
                                break;
                            case "الجميع":
                                setUpListOfItemsByDefault("all");
                                break;


                        }


                    }
                });
        builder.show();

    }



    private void getMyAdsApi() {

        ArrayList<Items> mSports = new ArrayList<>();


        Call<Object> call= RetrofitClient.
                getInstance()
                .getApi()
                .get_my_ads();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                Log.e("gson:get_my_ads", new Gson().toJson(response.body()) );

                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("res:get_my_ads", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
                            if (message != null&&!message.isEmpty()) {
                                Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                            }
                            JSONObject jsonData  = jsonObject.getJSONObject("data");
                            JSONArray arrJson = jsonData.getJSONArray("ads");
                            JSONObject[] arr=new JSONObject[arrJson.length()];

                            for(int i = 0; i < arrJson.length(); i++) {
                                arr[i] = arrJson.getJSONObject(i);
                                Log.e("tag", String.valueOf(arr[i]));
                                String ad_key=arr[i].getString("ad_key");
                                String title=arr[i].getString("title");
                                String offer=arr[i].getString("offer");
                                String main_image=arr[i].getString("main_image");
                                String price=arr[i].getString("price");
                                String category=arr[i].getString("category");
                                String sub_category=arr[i].getString("sub_category");
                                String active=arr[i].getString("active");
                                String item_condition=arr[i].getString("item_condition");
                                String status=arr[i].getString("status");
                                mSports.add(new Items(ad_key,main_image,item_condition , title, price,offer,category,sub_category,active,status));
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
                        Log.e("gson:get_my_ads_error", response.errorBody().string());
                        Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("get_my_ads:onFailure", String.valueOf(t));

            }
        });

    }

    private void setUpListOfItemsByDefault(String itemCond) {
        mLayoutManager = new LinearLayoutManager(mActivity);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        itemsAdapter = new ItemsAdapter(new ArrayList<>());

//        prepareDemoContent();
        getAdsApiByDefault(itemCond);


    }


    private void getAdsApiByDefault(String itemCond) {
        ArrayList<Items> mSports = new ArrayList<>();

        Call<Object> call= RetrofitClient.
                getInstance()
                .getApi()
                .searchByDate(subCategoryId);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                Log.e("gson:search_ads", new Gson().toJson(response.body()) );

                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("res:search_ads", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
                            if (message != null&&!message.isEmpty()) {
                                Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                            }
                            JSONObject jsonData  = jsonObject.getJSONObject("data");
                            JSONArray arrJson = jsonData.getJSONArray("data");
                            JSONObject[] arr=new JSONObject[arrJson.length()];

                            for(int i = 0; i < arrJson.length(); i++) {
                                arr[i] = arrJson.getJSONObject(i);
                                Log.e("tag", String.valueOf(arr[i]));
                                String ad_key=arr[i].getString("ad_key");
                                String title=arr[i].getString("title");
                                String offer=arr[i].getString("offer");
                                String main_image=arr[i].getString("main_image");
                                String price=arr[i].getString("price");
                                String category=arr[i].getString("category");
                                String sub_category=arr[i].getString("sub_category");
                                String active=arr[i].getString("active");
                                String item_condition=arr[i].getString("item_condition");
                                String status=arr[i].getString("status");
                                if(item_condition.equalsIgnoreCase(itemCond)||itemCond.equalsIgnoreCase("all")){
                                    mSports.add(new Items(ad_key,main_image,item_condition , title, price,offer,category,sub_category,active,status));
                                }
                            }
                            itemsAdapter.addItems(mSports);
                            mRecyclerView.setAdapter(itemsAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
                    }

                } else if (response.errorBody() != null) {
                    try {
                        Log.e("gson:search_ads_error", response.errorBody().string());
                        Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("search_ads:onFailure", String.valueOf(t));

            }
        });


    }



    private void setUpListOfItems(String orderBy) {
        mLayoutManager = new LinearLayoutManager(mActivity);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        itemsAdapter = new ItemsAdapter(new ArrayList<>());

//        prepareDemoContent();
        getAdsApi(orderBy);


    }

    private void getAdsApi(String orderBy) {
        ArrayList<Items> mSports = new ArrayList<>();

        Call<Object> call= RetrofitClient.
                getInstance()
                .getApi()
                .search_ads(subCategoryId,orderBy);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                Log.e("gson:search_ads", new Gson().toJson(response.body()) );

                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("res:search_ads", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
                            if (message != null&&!message.isEmpty()) {
                                Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
                            }
                            JSONObject jsonData  = jsonObject.getJSONObject("data");
                            JSONArray arrJson = jsonData.getJSONArray("data");
                            JSONObject[] arr=new JSONObject[arrJson.length()];

                            for(int i = 0; i < arrJson.length(); i++) {
                                arr[i] = arrJson.getJSONObject(i);
                                Log.e("tag", String.valueOf(arr[i]));
                                String ad_key=arr[i].getString("ad_key");
                                String title=arr[i].getString("title");
                                String offer=arr[i].getString("offer");
                                String main_image=arr[i].getString("main_image");
                                String price=arr[i].getString("price");
                                String category=arr[i].getString("category");
                                String sub_category=arr[i].getString("sub_category");
                                String active=arr[i].getString("active");
                                String item_condition=arr[i].getString("item_condition");
                                String status=arr[i].getString("status");
                                mSports.add(new Items(ad_key,main_image,item_condition , title, price,offer,category,sub_category,active,status));
                            }
                            itemsAdapter.addItems(mSports);
                            mRecyclerView.setAdapter(itemsAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
                    }

                } else if (response.errorBody() != null) {
                    try {
                        Log.e("gson:search_ads_error", response.errorBody().string());
                        Toast.makeText(mActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("search_ads:onFailure", String.valueOf(t));

            }
        });


    }


    private void setUpListOfMyItems() {
        mLayoutManager = new LinearLayoutManager(mActivity);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mSportAdapter = new MyItemsAdapter(new ArrayList<>());

//        prepareDemoContent();

        getMyAdsApi();

    }



    private void prepareDemoContent() {
//        CommonUtils.showLoading(getActivity());
        new Handler().postDelayed(() -> {
            //prepare data and show loading
//            CommonUtils.hideLoading();

            //TODO Fix bug not attached to a context.
            ArrayList<Items> mSports = new ArrayList<>();
            String[] sportsList = getResources().getStringArray(R.array.sports_titles);
            String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
            String[] sportsImage = getResources().getStringArray(R.array.sports_images);
            for (int i = 0; i < sportsList.length; i++) {
                mSports.add(new Items(sportsImage[i], sportsInfo[i], "News", sportsList[i]));
            }
            mSportAdapter.addItems(mSports);
            mRecyclerView.setAdapter(mSportAdapter);
        }, 2000);

    }
        @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ItemsRecyclerViewModel.class);
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
