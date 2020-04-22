package com.souqmaftoh.basatashopping.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Interface.Advertise;
import com.souqmaftoh.basatashopping.Interface.Advertiser;
import com.souqmaftoh.basatashopping.Interface.Items;
import com.souqmaftoh.basatashopping.RegistrationActivityTow;
import com.souqmaftoh.basatashopping.fragments.ItemsRecyclerFragment.ItemsRecyclerFragment;
import com.souqmaftoh.basatashopping.fragments.MobileFragment.MobileViewModel;
import com.souqmaftoh.basatashopping.MainActivity;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.design.CurvedBottomNavigationView;
import com.souqmaftoh.basatashopping.fragments.addAdv.AddAdvFragment;

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


    //    List<category> historicList = new ArrayList<>();

    private MobileViewModel mobileViewModel;
    private RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private HashMap<String, String> hashMapItem2;
    private HashMap<String, String> hashMapEditAd=new HashMap<String, String>();

    String ad_key;
    MaterialButton btn_Edit_Choices;
    String message;
    int id;
    Advertise advertise;
    Advertiser advertiser;
    String TAG ="Api";
    private boolean flag=false;




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
            if (getArguments().getSerializable("hashMapItem") != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
                hashMapItem2 = (HashMap<String, String>) getArguments().getSerializable("hashMapItem");
                Log.e("hashMapItem2", String.valueOf(hashMapItem2));

            }

            if (getArguments().getString("fragment") != null) {
                if (getArguments().getString("fragment").equalsIgnoreCase("itemsAdapter")) {
                    flag = true;

                }
            }
            if (getArguments().getString("ad_key") != null) {
                ad_key=getArguments().getString("ad_key");
                if(ad_key!=null)
                Log.e("ad_key",ad_key);
                getAdApi(ad_key);
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
        edit_advName=root.findViewById(R.id.edit_advName);
        edit_AdvDescription=root.findViewById(R.id.edit_AdvDescription);
        edit_AdvCategory=root.findViewById(R.id.edit_AdvCategory);
        edit_AdvSubCategory=root.findViewById(R.id.edit_AdvSubCategory);
        edit_AdvActive=root.findViewById(R.id.edit_AdvActive);
        edit_AdvStatus=root.findViewById(R.id.edit_AdvStatus);
        edit_AdvPriceOffer=root.findViewById(R.id.edit_AdvPriceOffer);
        edit_AdvPrice=root.findViewById(R.id.edit_AdvPrice);
        btn_Edit_Choices=root.findViewById(R.id.btn_Edit_Choices);


        card_AdvPriceOffer=root.findViewById(R.id.card_AdvPriceOffer);
//        addAdvDetails();

        //navigationBottom Bar
        card_telephone=root.findViewById(R.id.card_telephone);
        card_confirm=root.findViewById(R.id.card_AdvAddress);
        mView = root.findViewById(R.id.customBottomBar);
        mView.setOnNavigationItemSelectedListener(ItemDetailsFragment.this);
//        ((MainActivity) Objects.requireNonNull(getActivity())).hideFloatingActionButton();
//        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        if(flag){
            btn_Edit_Choices.setText("اضافه الاعلان الى المفضله");
        }else {
            btn_Edit_Choices.setText("خيارات التعديل");
        }
        btn_Edit_Choices.setOnClickListener(this);

        return root;
    }

//    private void addAdvDetails() {
//        if(hashMapItem2!=null) {
////            if(hashMapItem2.get("ImageUrl")!=null) {
////                String ImageUrl = hashMapItem2.get("ImageUrl");
////                Glide.with(this)
////                        .load(ImageUrl)
////                        .into(imgV_adv);
////            }
//
////            if(hashMapItem2.get("ad_key")!=null){
////                ad_key=hashMapItem2.get("ad_key");
//                getAdApi(ad_key);
////            }
//
////            if(hashMapItem2.get("Title")!=null){
////                edit_advName.setText(hashMapItem2.get("Title"));
////            }
////        if(advertise!= null)
////            edit_advName.setText(advertise.getTitle());
//
////            if(hashMapItem2.get("Item_condition")!=null){
////                if(hashMapItem2.get("Item_condition").equalsIgnoreCase("new"))
////                    edit_AdvDescription.setText("جديد");
////                else
////                    edit_AdvDescription.setText("قديم");
////
////            }
////
////            if(hashMapItem2.get("SubTitle")!=null){
////                edit_AdvCategory.setText(hashMapItem2.get("SubTitle"));
////            }
////            if(hashMapItem2.get("Sub_category")!=null){
////                edit_AdvSubCategory.setText(hashMapItem2.get("Sub_category"));
////            }
////            if(hashMapItem2.get("Active")!=null){
////                if(hashMapItem2.get("Active").equalsIgnoreCase("1")) {
////                    edit_AdvActive.setText("مفعل");
////                }else {
////                    edit_AdvActive.setText("غير مفعل");
////
////                }
//            }
////            if(hashMapItem2.get("Info")!=null){
////                edit_AdvPrice.setText(hashMapItem2.get("Info"));
////            }
////            if(hashMapItem2.get("Status")!=null){
////                if(hashMapItem2.get("Status").equalsIgnoreCase("sold"))
////                    edit_AdvStatus.setText("المنتج مباع");
////                else
////                    edit_AdvStatus.setText("المنتج غير مباع");
////
////            }
//
//
//
//        }

//    }




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
                            JSONArray social_links=jsonadvertiser.getJSONArray("social_links");
                            String[] arr_social_links = new String[social_links.length()];
                            for(int i = 0; i < social_links.length(); i++)
                                arr_social_links[i] = social_links.getString(i);

                             advertiser=new Advertiser(name,image,market_name,address,lat,lng,phone,description,arr_social_links);

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
                            String[] arr_images = new String[images.length()];
                            for(int i = 0; i < images.length(); i++)
                                arr_images[i] = images.getString(i);

                            advertise=new Advertise(active,offer,title,main_image,price,descriptionAdv,category,sub_category_id,sub_category,
                                    is_favorite,rate,rate_users,item_condition,status,arr_images);
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
                String ImageUrl = advertise.getMain_image();
                Glide.with(this)
                        .load(ImageUrl)
                        .into(imgV_adv);
                hashMapEditAd.put("main_image",advertise.getMain_image());
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
            case R.id.btn_Edit_Choices:
                if(flag){
                    setAsFavoritApi();
                }else {
                    openDialog();
                }
                break;
        }
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


}
