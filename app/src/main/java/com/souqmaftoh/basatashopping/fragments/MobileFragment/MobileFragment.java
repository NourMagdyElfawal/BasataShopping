package com.souqmaftoh.basatashopping.fragments.MobileFragment;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Adapter.HomeAdapter;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Interface.SubCategory;
import com.souqmaftoh.basatashopping.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileFragment extends Fragment {

//    List<category> historicList = new ArrayList<>();
    private Activity mActivity;
    private MobileViewModel mobileViewModel;
    private RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<SubCategory> mSubCategories;
    ArrayList<String> imageUrlList,imageTitleList;
    ArrayList<Integer>     imageIdList;
    int catId;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public MobileFragment() {
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
    public static MobileFragment newInstance(String param1, String param2) {
        MobileFragment fragment = new MobileFragment();
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
           catId=getArguments().getInt("categoryId");
              Log.e("mobile", String.valueOf(catId));


        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mobileViewModel =
                ViewModelProviders.of(this).get(MobileViewModel.class);
        View root = inflater.inflate(R.layout.mobile_fragment, container, false);
//        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();

        recyclerView = (RecyclerView) root.findViewById(R.id.home_recycleviw);
        Log.e("mobile","oncreate");

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
       getSubCategoryApi(catId);

        return root;
    }


    private ArrayList<String> prepareImages(ArrayList<SubCategory> mSubCategories) {
        // here you should give your image URLs and that can be a link from the Internet
//        String imageUrls[] = {
//                "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/fruits.png",
////                "https://homepages.cae.wisc.edu/~ece533/images/frymire.png",
////                "https://homepages.cae.wisc.edu/~ece533/images/girl.png",
////                "https://homepages.cae.wisc.edu/~ece533/images/monarch.png",
////                "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
////                "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
////                "https://homepages.cae.wisc.edu/~ece533/images/boat.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/monarch.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/cat.png"};

        ArrayList<String> imageUrlList = new ArrayList<String>();
        for (int i = 0; i < mSubCategories.size(); i++) {
//            category imageUrl = new category();
//            imageUrl.setImageUrl(imageUrls[i]);
            imageUrlList.add(mSubCategories.get(i).getImageUrl());

//            imageUrlList.add(imageUrl);
        }
        Log.d("MobileFragment", "List count: " + imageUrlList.size());
        return imageUrlList;
    }

    private ArrayList<String> prepareTitles(ArrayList<SubCategory> mSubCategories) {
        // here you should give your image titles and that can be a link from the Internet
//        String itemTitles[] = {
//                "أبل",
//                "سامسونج",
//                "هواوي",
////                "اتش تي سي",
////                "سوني",
////                "ال جي",
////                "الكاتيل",
////                "اسوس",
////                "نوكيا",
//                "شاومي",
//                "اوبو",
//                "لينوفو",
//                "بلاك بيري",
//        };

        ArrayList<String> imageTitleList = new ArrayList<>();
        for (int i = 0; i < mSubCategories.size(); i++) {
//            category imageTitle = new category();
//            imageTitle.setName(itemTitles[i]);
            imageTitleList.add(mSubCategories.get(i).getSubCategory());
        }
        Log.d("MobileFragment", "List count: " + imageTitleList.size());
        return imageTitleList;

    }


    private ArrayList<Integer> prepareIds(ArrayList<SubCategory> mSubCategories) {
        ArrayList<Integer> imageIdList = new ArrayList<>();
        for (int i = 0; i < mSubCategories.size(); i++) {
            imageIdList.add(mSubCategories.get(i).getSubCategoryId());
        }
        Log.d("MobileFragment", "List count: " + imageIdList.size());
        return imageIdList;

    }



    private void getSubCategoryApi(int categoryId) {

        ArrayList<SubCategory> mSubCategories = new ArrayList<>();

        Call call= RetrofitClient.
                getInstance()
                .getApi()
                .get_subcategories(categoryId);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                Log.e("gson:get_subcategories", new Gson().toJson(response.body()) );

                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("res:get_subcategories", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
                            if (message != null&&!message.isEmpty()) {
//                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                            JSONObject jsonData  = jsonObject.getJSONObject("data");
                            JSONArray arrJson = jsonData.getJSONArray("data");
                            JSONObject[] arr=new JSONObject[arrJson.length()];

                            for(int i = 0; i < arrJson.length(); i++) {
                                arr[i] = arrJson.getJSONObject(i);
                                Log.e("tag", String.valueOf(arr[i]));
                                String subcategories=arr[i].getString("name_ar");
                                int id=arr[i].getInt("id");
                                String imageUrl=arr[i].getString("image");

                                //                                String offer=arr[i].getString("offer");
//                                String main_image=arr[i].getString("main_image");
//                                String price=arr[i].getString("price");
//                                String category=arr[i].getString("category");
//                                String sub_category=arr[i].getString("sub_category");
//                                String active=arr[i].getString("active");
//                                String item_condition=arr[i].getString("item_condition");
//                                String status=arr[i].getString("status");
                                mSubCategories.add(new SubCategory(subcategories,id,imageUrl));
                            }
                            Log.e("subcategories", String.valueOf(mSubCategories));
                            if(mSubCategories!=null){
                                if (mActivity == null) {
                                    return;
                                }

//                                prepareImages(mSubCategories);
                                imageUrlList = prepareImages(mSubCategories);
                                imageTitleList = prepareTitles(mSubCategories);
                                imageIdList=prepareIds(mSubCategories);
                                HomeAdapter homeAdapter = new HomeAdapter(getActivity(), imageUrlList, imageTitleList,imageIdList);

//        homeAdapter = new HomeAdapter(getActivity(), historicList);
                                recyclerView.setAdapter(homeAdapter);


//                                spinnerSubCategories(mSubCategories);
                            }
//                            mSportAdapter.addItems(mSports);
//                            mRecyclerView.setAdapter(mSportAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
                    }

                } else if (response.errorBody() != null) {
                    try {
                        Log.e("gson:categories_error", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }




            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("categories:onFailure", String.valueOf(t));

            }
        });


    }

    @Override
    public void onAttach(@NotNull Context context) {
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
