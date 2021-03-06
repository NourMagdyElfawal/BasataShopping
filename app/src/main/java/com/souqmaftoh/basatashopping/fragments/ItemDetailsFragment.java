package com.souqmaftoh.basatashopping.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.souqmaftoh.basatashopping.Adapter.SliderAdapter;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Fonts.LatoBLack;
import com.souqmaftoh.basatashopping.Interface.Advertise;
import com.souqmaftoh.basatashopping.Interface.Advertiser;
import com.souqmaftoh.basatashopping.Interface.Items;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;
import com.souqmaftoh.basatashopping.fragments.MobileFragment.MobileViewModel;
import com.souqmaftoh.basatashopping.MainActivity;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.design.CurvedBottomNavigationView;
import com.souqmaftoh.basatashopping.fragments.addAdv.AddAdvFragment;
import com.souqmaftoh.basatashopping.fragments.myAccount.MyAccountFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDetailsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    CurvedBottomNavigationView mView;
    MainActivity mainActivity;
    CardView card_telephone,card_confirm,card_AdvPriceOffer;

    ImageView imgV_adv;
    TextView edit_advName,edit_AdvDescription,edit_AdvCategory,edit_AdvSubCategory,edit_AdvActive,
            edit_AdvStatus,edit_AdvPriceOffer,edit_AdvPrice;
    AlertDialog.Builder builder;
    String selected;
    private String offer_price = "";
    private ArrayList<String> imagesUrl;
    private Activity mActivity;


    //    List<category> historicList = new ArrayList<>();

    private MobileViewModel mobileViewModel;
    private RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private HashMap<String, String> hashMapItem2;
    private HashMap<String, String> hashMapEditAd=new HashMap<String, String>();
    private HashMap<String, String> hashMapAccountDetails=new HashMap<String, String>();


    String ad_key="";
    MaterialButton btn_Edit_Choices,btn_add_rate;
    String message;
    int id;
    Advertise advertise;
    Advertiser advertiser;
    String TAG ="Api";
    private boolean flag=false,visitor=false;
    RatingBar ratingBar,ratingBarIndicat;
    LatoBLack txtV_advertiser;
    ImageView imgV_call;
    LinearLayout advertiser_layout;
    SliderAdapter adapter;
    private int indexOfImage = 0;
    ViewPager viewPager;







    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mAdKey;

    public ItemDetailsFragment() {
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
    public static ItemDetailsFragment newInstance(String param1, String param2) {
        ItemDetailsFragment fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null ) {


            if (SharedPrefManager.getInstance(getActivity()).getUser()!=null) {
                User user = SharedPrefManager.getInstance(getActivity()).getUser();
                if (user.getToken() != null && !user.getToken().isEmpty()) {
//                    token = user.getToken();
//                    Log.e("token",token);


                    if (getArguments().getString("fragment") != null) {
                        if (getArguments().getString("fragment").equalsIgnoreCase("itemsAdapter")) {
                            flag = true;

                        }
                    }

                    if (getArguments().getString("ad_key") != null) {
                        ad_key = getArguments().getString("ad_key");
                        if (ad_key != null) {
                            Log.e("ad_key", ad_key);
                            getAdApi(ad_key);
                        }
                    }

                } else {

                    if (getArguments().getSerializable("hashMapItem") != null) {
                        hashMapItem2 = (HashMap<String, String>) getArguments().getSerializable("hashMapItem");
                        Log.e("hashMapItem2", String.valueOf(hashMapItem2));
                            visitor=true;
//                        if (hashMapItem2 != null) {
////                    if (hashMapItem2.get("ad_key") != null) {
////                        String    ad_key_2 = hashMapItem2.get("ad_key");
////                        getAdApi(ad_key_2);
////                    }
//
//                            addAdvDetails(hashMapItem2);
//
//                        }
                    }


                }
            }

            }


    }



    public static ItemDetailsFragment newInstance() {
        return new ItemDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.item_details_fragment, container, false);

        imgV_adv=root.findViewById(R.id.imgV_adv);
        txtV_advertiser=root.findViewById(R.id.txtV_advertiser);
        imgV_call=root.findViewById(R.id.imgV_call);
        edit_advName=root.findViewById(R.id.edit_advName);
        edit_AdvDescription=root.findViewById(R.id.edit_AdvDescription);
        edit_AdvCategory=root.findViewById(R.id.edit_AdvCategory);
        edit_AdvSubCategory=root.findViewById(R.id.edit_AdvSubCategory);
        edit_AdvActive=root.findViewById(R.id.edit_AdvActive);
        edit_AdvStatus=root.findViewById(R.id.edit_AdvStatus);
        edit_AdvPriceOffer=root.findViewById(R.id.edit_AdvPriceOffer);
        edit_AdvPrice=root.findViewById(R.id.edit_AdvPrice);
        btn_Edit_Choices=root.findViewById(R.id.btn_Edit_Choices);
        ratingBar =root.findViewById(R.id.ratingBar);
        ratingBarIndicat=root.findViewById(R.id.ratingBarIndicat);
        btn_add_rate=root.findViewById(R.id.btn_add_rate);
        advertiser_layout=root.findViewById(R.id.advertiser_layout);
        card_AdvPriceOffer=root.findViewById(R.id.card_AdvPriceOffer);
//        addAdvDetails();
        //Image Slider
         viewPager = (ViewPager) root.findViewById(R.id.pager);



        //navigationBottom Bar
        card_telephone=root.findViewById(R.id.card_telephone);
        card_confirm=root.findViewById(R.id.card_AdvAddress);
        mView = root.findViewById(R.id.customBottomBar);
        mView.setOnNavigationItemSelectedListener(ItemDetailsFragment.this);
//        ((MainActivity) Objects.requireNonNull(getActivity())).hideFloatingActionButton();
//        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
                        if (hashMapItem2 != null) {

                            addAdvDetails(hashMapItem2);

                        }

        if(flag){
            btn_Edit_Choices.setText("اضافه الاعلان الى المفضله");
            btn_add_rate.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.VISIBLE);
            ratingBarIndicat.setVisibility(View.GONE);
            btn_add_rate.setOnClickListener(this);

        }else {
            btn_Edit_Choices.setText("خيارات التعديل");
            ratingBar.setVisibility(View.GONE);
            ratingBarIndicat.setVisibility(View.VISIBLE);

        }
        if(visitor){
//            advertiser_layout.setVisibility(View.GONE);
            txtV_advertiser.setOnClickListener(null);
            btn_Edit_Choices.setVisibility(View.GONE);
            ratingBar.setVisibility(View.GONE);
            ratingBarIndicat.setVisibility(View.VISIBLE);

        }else {
            txtV_advertiser.setOnClickListener(this);
        }
        btn_Edit_Choices.setOnClickListener(this);
        imgV_call.setOnClickListener(this);

//        mainActivity.hideFloatingActionButton();

        return root;
    }

    private void addAdvDetails(HashMap<String, String> hashMapItem2) {
        if(hashMapItem2 !=null) {
            Log.e("hash", String.valueOf(hashMapItem2));
            if(hashMapItem2.get("ImageUrl")!=null) {
                String ImageUrl = hashMapItem2.get("ImageUrl");
                Glide.with(this)
                        .load(ImageUrl)
                        .into(imgV_adv);
            }

            if(hashMapItem2.get("ad_key")!=null){
                ad_key= this.hashMapItem2.get("ad_key");
//                getAdApi(ad_key);
            }
//
            if(hashMapItem2.get("Title")!=null){
                Log.e("title", hashMapItem2.get("Title"));
                String title=hashMapItem2.get("Title");
                edit_advName.setText(title);
            }
//        if(advertise!= null)
//            edit_advName.setText(advertise.getTitle());

            if(hashMapItem2.get("Item_condition")!=null){
                if(hashMapItem2.get("Item_condition").equalsIgnoreCase("new"))
                    edit_AdvDescription.setText("جديد");
                else
                    edit_AdvDescription.setText("قديم");

            }

            if(hashMapItem2.get("SubTitle")!=null){
                edit_AdvCategory.setText(this.hashMapItem2.get("SubTitle"));
            }
            if(hashMapItem2.get("Sub_category")!=null){
                edit_AdvSubCategory.setText(this.hashMapItem2.get("Sub_category"));
            }
            if(hashMapItem2.get("Active")!=null){
                if(hashMapItem2.get("Active").equalsIgnoreCase("1")) {
                    edit_AdvActive.setText("مفعل");
                }else {
                    edit_AdvActive.setText("غير مفعل");

                }
            }
            if(hashMapItem2.get("Info")!=null){
                edit_AdvPrice.setText(this.hashMapItem2.get("Info"));
            }
            if(hashMapItem2.get("Status")!=null){
                if(hashMapItem2.get("Status").equalsIgnoreCase("sold"))
                    edit_AdvStatus.setText("المنتج مباع");
                else
                    edit_AdvStatus.setText("المنتج غير مباع");

            }



        }



    }


    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            indexOfImage = position;
            if(String.valueOf(indexOfImage)!=null)
                Log.e("index", String.valueOf(indexOfImage));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void getAdApi(String ad_key) {

        ArrayList<Items> mSports = new ArrayList<>();


        Call<Object> call= RetrofitClient.
                getInstance()
                .getApi()
                .get_ad(ad_key);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                Log.e("gson:get_ad", new Gson().toJson(response.body()) );

                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("res:get_ad", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
                            if (message != null&&!message.isEmpty()) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }
                            JSONObject jsonData  = jsonObject.getJSONObject("data");
                            JSONObject jsonadvertiser=jsonData.getJSONObject("advertiser");
                            JSONObject jsonadvertise=jsonData.getJSONObject("advertise");

                            //get Advertiser information
                            String name=jsonadvertiser.getString("name");
                            String image=jsonadvertiser.getString("image");
                            String market_name=jsonadvertiser.getString("market_name");
                            String address=jsonadvertiser.getString("address");
                            String lat=jsonadvertiser.getString("lat");
                            String lng=jsonadvertiser.getString("lng");
                            String phone=jsonadvertiser.getString("phone");
                            String description=jsonadvertiser.getString("description");
                            String firebase_id=jsonadvertiser.getString("firebase_id");
                            JSONObject social_links=jsonadvertiser.getJSONObject("social_links");
//                            String[] arr_social_links = new String[social_links.length()];
//                            for(int i = 0; i < social_links.length(); i++)
//                                arr_social_links[i] = social_links.getString(i);
                            String facebookUrl="",instagramUrl="",youtubeUrl="";

                            if(social_links.has("facebook")) {
                                facebookUrl = social_links.getString("facebook");
                            }
                            if(social_links.has("instagram")) {

                                instagramUrl = social_links.getString("instagram");
                            }
                            if(social_links.has("youtube")) {

                                youtubeUrl = social_links.getString("youtube");
                            }

                             advertiser=new Advertiser(name,image,market_name,address,lat,lng,phone,description,facebookUrl,instagramUrl,youtubeUrl,firebase_id);

                            //get Advertise information
                            String active=jsonadvertise.getString("active");
                            String offer=jsonadvertise.getString("offer");
                            String title=jsonadvertise.getString("title");
                            String main_image=jsonadvertise.getString("main_image");
                            String price=jsonadvertise.getString("price");
                            String descriptionAdv=jsonadvertise.getString("description");
                            String category=jsonadvertise.getString("category");
                            String sub_category_id=jsonadvertise.getString("sub_category_id");
                            String sub_category=jsonadvertise.getString("sub_category");
                            String is_favorite=jsonadvertise.getString("is_favorite");
                            String rate=jsonadvertise.getString("rate");
                            String rate_users=jsonadvertise.getString("rate_users");
                            String item_condition=jsonadvertise.getString("item_condition");
                            String status=jsonadvertise.getString("status");
                            JSONArray images=jsonadvertise.getJSONArray("images");
                            int lenImg = images.length();
                            ArrayList<String> imagesUrl = new ArrayList<>();
                            for(int i = 0; i < lenImg; i++) {
                                JSONObject json = images.getJSONObject(i);
                                imagesUrl.add(json.getString("url"));

                            }
                            advertise=new Advertise(active,offer,title,main_image,price,descriptionAdv,category,sub_category_id,sub_category,
                                    is_favorite,rate,rate_users,item_condition,status,imagesUrl);
                            if(advertise!= null&&advertiser!=null){
                                setAdDetails();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
////
                    }

                } else if (response.errorBody() != null) {
                    try {
                        Log.e("gson:get_ad_error", response.errorBody().string());
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("get_ad:onFailure", String.valueOf(t));

            }
        });

    }

    private void setAdDetails() {

            if(advertise.getMain_image()!=null) {
                if (mActivity == null) {
                    return;
                }
                String ImageUrl = advertise.getMain_image();
                Glide.with(mActivity)
                        .load(ImageUrl)
                        .into(imgV_adv);
                hashMapEditAd.put("main_image",advertise.getMain_image());
            }
        if(advertise.getArr_images()!=null&&advertise.getArr_images().size()!=0) {
              imagesUrl=advertise.getArr_images();
            String[] imagesUrlArr=new String[imagesUrl.size()];
            imagesUrlArr =imagesUrl.toArray(imagesUrlArr);
            if(imagesUrlArr!=null) {
                adapter = new SliderAdapter(getContext(), imagesUrlArr); //Here we are defining the Imageadapter object

                viewPager.setAdapter(adapter); // Here we are passing and setting the adapter for the images

                viewPager.setOnPageChangeListener(new MyPageChangeListener());
            }
        }else{
                viewPager.setVisibility(View.GONE);
        }
            if(advertiser.getName()!=null){
                txtV_advertiser.setText(advertiser.getName());
            }else {
                advertiser_layout.setVisibility(View.GONE);
//                txtV_advertiser.setText("");

            }



        if(advertise.getTitle()!=null){
            hashMapEditAd.put("title",advertise.getTitle());
            edit_advName.setText(advertise.getTitle());
        }

        if(advertise.getPrice()!=null){
            hashMapEditAd.put("price",advertise.getPrice());
        }

        if(advertise.getOffer()!=null&&!
                advertise.getOffer().isEmpty()){
            card_AdvPriceOffer.setVisibility(View.VISIBLE);
            edit_AdvPriceOffer.setText(advertise.getOffer());
        }



        if(advertise.getDescriptionAdv()!=null){
            hashMapEditAd.put("description",advertise.getDescriptionAdv());
            edit_AdvDescription.setText(advertise.getDescriptionAdv());
        }


        if(advertise.getCategory()!=null){
                edit_AdvCategory.setText(advertise.getCategory());
            }
            if(advertise.getSub_category()!=null){
                edit_AdvSubCategory.setText(advertise.getSub_category());
            }



        if(advertise.getActive()!=null) {
            if(advertise.getActive().equalsIgnoreCase("1")) {
                edit_AdvActive.setText("مفعل");
            }else {
                edit_AdvActive.setText("غير مفعل");

            }
        }

        if(advertise.getIs_favorite()!=null){

        }
        if (advertise.getPrice()!=null){
            edit_AdvPrice.setText(advertise.getPrice());
        }

        if(advertise.getStatus()!=null){
                if(advertise.getStatus().equalsIgnoreCase("sold"))
                    edit_AdvStatus.setText("المنتج مباع");
                else
                    edit_AdvStatus.setText("المنتج غير مباع");

            }
        if(advertise.getIs_favorite()!=null){
            if (flag) {
                if (advertise.getIs_favorite().equalsIgnoreCase("true")) {

                    btn_Edit_Choices.setText("ازالة الاعلان من المفضله");

                } else {
                    btn_Edit_Choices.setText("اضافه الاعلان الى المفضله");

                }
            }
            Log.e("fav",advertise.getIs_favorite());
        }



    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //        switch (menuItem.getItemId()) {
//            case R.id.action_offers:
//
//                break;
//
//            case R.id.action_categories:
//
//                android.app.Fragment fmm = new Shop_Now_fragment();
//                FragmentManager fmm2 = getFragmentManager();
//                fmm2.beginTransaction().replace(R.id.contentPanel, fmm).addToBackStack(null).commit();
//
//                break;
//
//            case R.id.action_cart:
//
//                android.app.Fragment fm = new Cart_fragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm).addToBackStack(null).commit();
//
//
//
//
////        item.setVisible(true);
////
////        View count = item.getActionView();
////        count.setOnClickListener(new View.OnClickListener() {
////
////            @Override
////            public void onClick(View v) {
////               // menu.performIdentifierAction(itemm.getItemId(), 0);
////            }
////        });
////
////        totalBudgetCount = (TextView) count.findViewById(R.id.actionbar_notifcation_textview);
////        totalBudgetCount.setText("" + dbcart.getCartCount());
//
//
//                ((MainActivity) getActivity()).setCartCounter("" + dbcart.getCartCount());
//
//
//
//                break;
//
//            case R.id.action_coupons:
//
//
//                android.app.Fragment fr = new Coupon_fragment();
//                FragmentManager fmc = getFragmentManager();
//                fmc.beginTransaction().replace(R.id.contentPanel, fr).addToBackStack(null).commit();
//
////                        Bundle args = new Bundle();
////                        Fragment fm = new Product_fragment();
////                        args.putString("cat_deal", "2");
////                        fm.setArguments(args);
////                        FragmentManager fragmentManager = getFragmentManager();
////                        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
////                                .addToBackStack(null).commit();
////
//
//                break;
//
//            case R.id.action_wallet:
//                android.app.Fragment fm1 = new Wallet_fragment();
//                FragmentManager fm2 = getFragmentManager();
//                fm2.beginTransaction().replace(R.id.contentPanel, fm1).addToBackStack(null).commit();
//
//                break;
//        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgV_call:
                if(advertiser.getPhone()!=null&&!advertiser.getPhone().isEmpty()){
                    Intent call = new Intent(Intent.ACTION_DIAL);
                    call.setData(Uri.parse("tel:" + advertiser.getPhone()));
                    startActivity(call);
                }else {
                    Toast.makeText(getActivity(), "لا يوجد رقم هاتف", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.txtV_advertiser:
                openAdvertiserDetailsFragment();
                break;
            case R.id.btn_Edit_Choices:
                if(flag){
                    setAsFavoritApi();
                }else {
                    openDialog();
                }
                break;
            case R.id.btn_add_rate:
                getRating();
                break;
        }
    }

    private void openAdvertiserDetailsFragment() {
        if(advertiser.getName()!=null){
            hashMapAccountDetails.put("name",advertiser.getName());
        }

        if(advertiser.getImage()!=null){
            hashMapAccountDetails.put("image",advertiser.getImage());
        }
        if(advertiser.getMarket_name()!=null){
            hashMapAccountDetails.put("market_name",advertiser.getMarket_name());
        }
        if(advertiser.getAddress()!=null){
            hashMapAccountDetails.put("address",advertiser.getAddress());
        }


        if(advertiser.getLat()!=null){
            hashMapAccountDetails.put("lat",advertiser.getLat());
        }

        if(advertiser.getLng()!=null){
            hashMapAccountDetails.put("lng",advertiser.getLng());
        }
        if(advertiser.getPhone()!=null){
            hashMapAccountDetails.put("phone",advertiser.getPhone());
        }
        if(advertiser.getDescription()!=null){
            hashMapAccountDetails.put("description",advertiser.getDescription());
        }
        if (advertiser.getFacebookUrl()!=null){
            hashMapAccountDetails.put("facebookUrl",advertiser.getFacebookUrl());
        }
        if (advertiser.getInstagramUrl()!=null){
            hashMapAccountDetails.put("instagramUrl",advertiser.getInstagramUrl());
        }
        if (advertiser.getYoutubeUrl()!=null){
            hashMapAccountDetails.put("youtubeUrl",advertiser.getYoutubeUrl());
        }

        if (advertiser.getFirebase_id()!=null){
            hashMapAccountDetails.put("firebase_id",advertiser.getFirebase_id());
        }


        MyAccountFragment myAccountFragment= new MyAccountFragment();
        Bundle args = new Bundle();
        args.putSerializable("hashMapAccountDetails",hashMapAccountDetails);
        myAccountFragment.setArguments(args);


        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.item_details_container, myAccountFragment, "AccountFragment")
                .addToBackStack(null)
                .commit();


    }

    private void getRating() {
       int rate= (int) ratingBar.getRating();
        Call call= RetrofitClient.
                getInstance().getApi()
                .rate_ad(ad_key,rate);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:rate_ad", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");
//                            if (msg != null&&msg.equalsIgnoreCase("تم حذف الإعلان بنجاح")) {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Log.e("rate_ad",msg);
                            Intent intent_log =new Intent(getActivity(), MainActivity.class);
                            intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent_log);

//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:rate_ad", response.errorBody().string());
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("rate_ad:onFailure", String.valueOf(t));

            }
        });



    }

    private void setAsFavoritApi() {
        Call call= RetrofitClient.
                getInstance().getApi()
                .set_as_favorite(ad_key);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:set_as_favorite", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");
//                            if (msg != null&&msg.equalsIgnoreCase("تم حذف الإعلان بنجاح")) {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Log.e("set_as_favorite",msg);
                            Intent intent_log =new Intent(getActivity(), MainActivity.class);
                            intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent_log);

//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:set_as_favorite", response.errorBody().string());
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("set_favorite:onFailure", String.valueOf(t));

            }
        });



    }

    private void openDialog() {
        CharSequence[] array = {"تعديل الاعلان", "تفعيل/الغاء تفعيل الاعلان", "مسح الاعلان", "عمل عرض/الغاء عرض","المنتج مباع"};

        builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("خيارات التعديل")
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
                            case "تعديل الاعلان":
                                AddAdvFragment addAdvFragment= new AddAdvFragment();
                                Bundle args = new Bundle();
                                args.putString("fragment", "editAdv");
                                args.putString("ad_key",ad_key);
                                args.putSerializable("hashMapEditAd",hashMapEditAd);
                                addAdvFragment.setArguments(args);


                                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.item_details_container, addAdvFragment, "findThisFragment")
                                        .addToBackStack(null)
                                        .commit();

                                break;
                            case "تفعيل/الغاء تفعيل الاعلان":
                                openDialogActivateAd();
                                break;
                            case "مسح الاعلان":
                                message="هل انت متأكد انك تريد مسح الاعلان؟";
                                id=1;
                                openCheckDialog(message,id);
                                break;
                            case "عمل عرض/الغاء عرض":
                               if(advertise.getOffer()!=null&&!advertise.getOffer().isEmpty()) {
                                   message="هل انت متأكد انك تريد الغاء العرض؟";
                                   id=2;
                                   openCheckDialog(message,id);

                               }else {
                                   createOfferDialog();
                               }
                                break;
                            case "المنتج مباع":
                                message="هل انت متأكد انك تريد جعل الاعلان مباع؟";
                                id=3;
                                openCheckDialog(message,id);
                                break;


                        }


                    }
                });
        builder.show();

    }

    private void createOfferDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("السعر الجديد بعد العرض");

// Set up the input
        final EditText input = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                offer_price = input.getText().toString();
                createOfferApi(offer_price);
                Toast.makeText(getContext(), offer_price, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void deleteOfferApi() {
        Call call= RetrofitClient.
                getInstance().getApi()
                .delete_offer(ad_key);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:delete_offer", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");
//                            if (msg != null&&msg.equalsIgnoreCase("تم حذف الإعلان بنجاح")) {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Log.e("delete_offer",msg);
                            Intent intent_log =new Intent(getActivity(), MainActivity.class);
                            intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent_log);

//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:error_delete_offer", response.errorBody().string());
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("delete_offer:onFailure", String.valueOf(t));

            }
        });

    }


    private void createOfferApi(String offer_price) {
        Call call= RetrofitClient.
                getInstance().getApi()
                .create_offer(ad_key,offer_price);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:create_offer", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");
//                            if (msg != null&&msg.equalsIgnoreCase("تم حذف الإعلان بنجاح")) {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Log.e("create_offer",msg);
                            Intent intent_log =new Intent(getActivity(), MainActivity.class);
                            intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent_log);

//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:error_create_offer", response.errorBody().string());
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("create_offer:onFailure", String.valueOf(t));

            }
        });

    }


    private void openDialogActivateAd() {
        CharSequence[] array = {"تفعيل الاعلان" , "الغاء تفعيل الاعلان"};

        builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        selected = array[arg1].toString();
                        Toast.makeText(getContext(), selected, Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        switch (selected){
                            case "تفعيل الاعلان":
                                activateAdApi();
                                Log.e(TAG,"activateAdApi");
                                break;

                            case "الغاء تفعيل الاعلان":
                                deActivateAdApi();
                                Log.e(TAG,"deActivateAdApi");
                                break;

                        }


                    }
                });
        builder.show();

    }




    private void activateAdApi() {
        Call call= RetrofitClient.
                getInstance().getApi()
                .activate_ad(ad_key);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:activate_ad", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");
//                            if (msg != null&&msg.equalsIgnoreCase("تم حذف الإعلان بنجاح")) {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                Log.e("activate_ad",msg);
                                Intent intent_log =new Intent(getActivity(), MainActivity.class);
                                intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent_log);

//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:error_activate_ad", response.errorBody().string());
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("activate_ad:onFailure", String.valueOf(t));

            }
        });

    }

    private void deActivateAdApi() {

        Call call= RetrofitClient.
                getInstance().getApi()
                .deactivate_ad(ad_key);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:deactivate_ad", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");
//                            if (msg != null&&msg.equalsIgnoreCase("تم حذف الإعلان بنجاح")) {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                Log.e("deactivate_ad",msg);
                                Intent intent_log =new Intent(getActivity(), MainActivity.class);
                                intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent_log);

//                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:err_deactivate_ad", response.errorBody().string());
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("deactivate_ad:onFailure", String.valueOf(t));

            }
        });

    }


    private void openCheckDialog(String message, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        switch (i){
                            case 1:
                                deleteAdApi();
                                Log.e(TAG,"deleteAdApi");
                                break;

                            case 2:
                                deleteOfferApi();
                                Log.e(TAG,"deleteOfferApi");
                                break;
                            case 3:
                                setAsSoldApi();
                                Log.e(TAG,"setAsSoldApi");
                                break;
                        }
                    }
                })
                .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.show();


    }

    private void deleteAdApi() {
        Call call= RetrofitClient.
                getInstance().getApi()
                .delete_ad(ad_key);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:delete_ad", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");
                            if (msg != null&&msg.equalsIgnoreCase("تم حذف الإعلان بنجاح")) {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                Log.e("delete_ad",msg);
                                Intent intent_log =new Intent(getActivity(), MainActivity.class);
                                intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent_log);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:error_delete_ad", response.errorBody().string());
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("delete_ad:onFailure", String.valueOf(t));

            }
        });

    }

    private void setAsSoldApi() {

        Call call= RetrofitClient.
                getInstance().getApi()
                .set_as_sold(ad_key);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("gson:set_as_sold", new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String msg = jsonObject.getString("message");
                            if (msg != null&&msg.equalsIgnoreCase("تم تغيير حالة الإعلان بنجاح")) {
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                Log.e("set_as_sold",msg);
                                Intent intent_log =new Intent(getActivity(), MainActivity.class);
                                intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent_log);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson:error_set_as_sold", response.errorBody().string());
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }


            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("set_as_sold:onFailure", String.valueOf(t));

            }
        });

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
