package com.souqmaftoh.basatashopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Interface.Categories;
import com.souqmaftoh.basatashopping.Interface.SubCategory;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;
import com.souqmaftoh.basatashopping.fragments.ItemDetailsFragment;
import com.souqmaftoh.basatashopping.fragments.MobileFragment.MobileFragment;
import com.souqmaftoh.basatashopping.fragments.ItemsRecyclerFragment.ItemsRecyclerFragment;
import com.souqmaftoh.basatashopping.fragments.myAccount.MyAccountFragment;
import com.souqmaftoh.basatashopping.fragments.accessories.AccessoriesFragment;
import com.souqmaftoh.basatashopping.fragments.addAdv.AddAdvFragment;
import com.souqmaftoh.basatashopping.fragments.chat.ChatFragment;
import com.souqmaftoh.basatashopping.fragments.notification.NotificationFragment;
import com.souqmaftoh.basatashopping.fragments.other.OthersFragment;
import com.souqmaftoh.basatashopping.fragments.tablets.TabletsFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

     FloatingActionButton fab;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigation;
    private FirebaseAuth mAuth;
    ImageView imgV_myAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgV_myAccount = findViewById(R.id.imgV_myAccount);
        imgV_myAccount.setOnClickListener(this);

        viewPager =findViewById(R.id.viewpager);
        getCategoryApi(viewPager);
//        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        //bottom bar
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //    openFragment(MobileFragment.newInstance("", ""));



        User user= SharedPrefManager.getInstance().getUser();


        fab = findViewById(R.id.fab);
        showFloatingActionButton();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getToken()!=null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.main, AddAdvFragment.newInstance("", ""));
                    transaction.addToBackStack(null);
                    transaction.commit();
                    hideFloatingActionButton();

                }else {
                    Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(myIntent);

                }



            }
        });


//        createKeyHash();


        //get sign in user using gmaile

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
//            Toast.makeText(this, "gmailInfo" + personName + personGivenName + personFamilyName + personEmail + personId, Toast.LENGTH_SHORT).show();
            Log.e("gmail","gmailInfo" + personName + personGivenName + personFamilyName + personEmail + personId);
        }

//        FirebaseUser user = mAuth.getCurrentUser();
//        if(user!=null){
//            String name=user.getDisplayName();
//            String email=user.getEmail();
//            String userId=user.getUid();
//            Toast.makeText(this, "firebase"+name+email+userId, Toast.LENGTH_SHORT).show();
//
//        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        showFloatingActionButton();
                        break;
                    case 1:
                        showFloatingActionButton();
                        break;
                    case 3:
                        showFloatingActionButton();
                        break;
                    default:
                        showFloatingActionButton();
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            Log.e("ad_key_Main", String.valueOf(extras));

            String ad_key = extras.getString("ad_key");
            if (ad_key != null) {
                Log.e("ad_key_Main", ad_key);
                ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Bundle args = new Bundle();
                args.putString("fragment", "itemsAdapter");
                args.putString("ad_key", ad_key);
                itemDetailsFragment.setArguments(args);
                transaction.replace(R.id.main, itemDetailsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
//        activity.getSupportFragmentManager().beginTransaction().add(R.id.items_main_content,itemDetailsFragment ).addToBackStack( "ItemsRecyclerFragment" ).commit();
        }



    }

    /**
     * Called when the activity has detected the user's press of the back
     * key. The {@link #getOnBackPressedDispatcher() OnBackPressedDispatcher} will be given a
     * chance to handle the back button before the default behavior of
     * {@link Activity#onBackPressed()} is invoked.
     *
     * @see #getOnBackPressedDispatcher()
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showFloatingActionButton();
    }

//    public interface OnBackPressedListener {
//        void onBackPressed();
//    }


    //app bar
    private void setupViewPager(ViewPager viewPager, ArrayList<Categories> mCategories) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        List<String> categories = new ArrayList<String>();
        for(int i = 0; i < mCategories.size(); i++) {
            String[] arr = new String[mCategories.size()];
//            arr[i] = mCategories.get(i).getCategory();
//            getSubCategoryApi(mCategories.get(i).getId());
            adapter.addFragment(new MobileFragment(),mCategories.get(i).getCategory(),mCategories.get(i).getId());
        }

//        adapter.addFragment(new OthersFragment(), "اخرى");
//        adapter.addFragment(new AccessoriesFragment(), "اكسسوارات");
//        adapter.addFragment(new TabletsFragment(), "تابلت");
//        adapter.addFragment(new MobileFragment(), "جوالات");

        viewPager.setAdapter(adapter);

    }

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
//                                String imageUrl=arr[i].getString("image");
//
//                                //                                String offer=arr[i].getString("offer");
////                                String main_image=arr[i].getString("main_image");
////                                String price=arr[i].getString("price");
////                                String category=arr[i].getString("category");
////                                String sub_category=arr[i].getString("sub_category");
////                                String active=arr[i].getString("active");
////                                String item_condition=arr[i].getString("item_condition");
////                                String status=arr[i].getString("status");
//                                mSubCategories.add(new SubCategory(subcategories,id,imageUrl));
//                            }
//                            Log.e("subcategories", String.valueOf(mSubCategories));
//                            if(mSubCategories!=null){
////                                spinnerSubCategories(mSubCategories);
//                            }
////                            mSportAdapter.addItems(mSports);
////                            mRecyclerView.setAdapter(mSportAdapter);
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



    public void showFloatingActionButton() {
        fab.setVisibility(View.VISIBLE);
    }

    public void hideFloatingActionButton() {
        fab.setVisibility(View.GONE);
    }


//    private void createKeyHash() {
//        PackageInfo info;
//        try {
//            info = getPackageManager().getPackageInfo("com.souqmaftoh.basatashopping", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md;
//                md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String something = new String(Base64.encode(md.digest(), 0));
//                //String something = new String(Base64.encodeBytes(md.digest()));
//                Log.e("hash key", something);
//            }
//        } catch (PackageManager.NameNotFoundException e1) {
//            Log.e("name not found", e1.toString());
//        } catch (NoSuchAlgorithmException e) {
//            Log.e("no such an algorithm", e.toString());
//        } catch (Exception e) {
//            Log.e("exception", e.toString());
//        }
//
//    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_stores:
                            openFragment(ItemsRecyclerFragment.newInstance("", ""));
                            showFloatingActionButton();
                            return true;
                        case R.id.navigation_sms:
                            openFragment(ChatFragment.newInstance("", ""));
                            showFloatingActionButton();
                            return true;
                        case R.id.navigation_notifications:
                            openFragment(NotificationFragment.newInstance("", ""));
                            showFloatingActionButton();
                            return true;
                        case R.id.navigation_home:
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction trans = manager.beginTransaction();
                            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            trans.commit();
                            showFloatingActionButton();
                            return true;

                    }
                    return false;
                }
            };
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgV_myAccount:
                openFragment(MyAccountFragment.newInstance("", ""));
                showFloatingActionButton();
                break;
        }
    }



    public  void getCategoryApi(ViewPager viewPager) {

        ArrayList<Categories> mCategories = new ArrayList<>();

        Call call= RetrofitClient.
                getInstance()
                .getApi()
                .get_categories();
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                Log.e("gson:get_categories", new Gson().toJson(response.body()) );

                if(response!=null) {

                    if (response.body() != null) {
                        Log.e("res:get_categories", "isSuccessful");
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
//                            if (message != null&&!message.isEmpty()) {
//                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                            }
                            JSONObject jsonData  = jsonObject.getJSONObject("data");
                            JSONArray arrJson = jsonData.getJSONArray("data");
                            JSONObject[] arr=new JSONObject[arrJson.length()];

                            for(int i = 0; i < arrJson.length(); i++) {
                                arr[i] = arrJson.getJSONObject(i);
                                Log.e("tag", String.valueOf(arr[i]));
                                String category=arr[i].getString("name_ar");
                                int id=arr[i].getInt("id");

                                mCategories.add(new Categories(category,id));
                            }
                            Log.e("category", String.valueOf(mCategories));
                            if(mCategories!=null){
                                setupViewPager(viewPager,mCategories);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
                    }

                } else if (response.errorBody() != null) {
                    try {
                        Log.e("gson:categories_error", response.errorBody().string());
//                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
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


}


class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Integer> mFragmentIdList = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment categoryFragment=mFragmentList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("categoryId", mFragmentIdList.get(position));
        categoryFragment.setArguments(bundle);
        return categoryFragment;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title, int id) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mFragmentIdList.add(id);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
