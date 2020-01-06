package com.souqmaftoh.basatashopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;
import com.souqmaftoh.basatashopping.fragments.MobileFragment.MobileFragment;
import com.souqmaftoh.basatashopping.fragments.ItemsRecyclerFragment.ItemsRecyclerFragment;
import com.souqmaftoh.basatashopping.fragments.myAccount.MyAccountFragment;
import com.souqmaftoh.basatashopping.fragments.accessories.AccessoriesFragment;
import com.souqmaftoh.basatashopping.fragments.addAdv.AddAdvFragment;
import com.souqmaftoh.basatashopping.fragments.chat.ChatFragment;
import com.souqmaftoh.basatashopping.fragments.notification.NotificationFragment;
import com.souqmaftoh.basatashopping.fragments.other.OthersFragment;
import com.souqmaftoh.basatashopping.fragments.tablets.TabletsFragment;

import java.util.ArrayList;
import java.util.List;

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
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        //bottom bar
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        //    openFragment(MobileFragment.newInstance("", ""));

        fab = findViewById(R.id.fab);
//        showFloatingActionButton();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.main, AddAdvFragment.newInstance("", ""));
                transaction.addToBackStack(null);
                transaction.commit();


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
            Toast.makeText(this, "gmailInfo" + personName + personGivenName + personFamilyName + personEmail + personId, Toast.LENGTH_SHORT).show();
        }

//        FirebaseUser user = mAuth.getCurrentUser();
//        if(user!=null){
//            String name=user.getDisplayName();
//            String email=user.getEmail();
//            String userId=user.getUid();
//            Toast.makeText(this, "firebase"+name+email+userId, Toast.LENGTH_SHORT).show();
//
//        }

    }

    //app bar
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OthersFragment(), "اخرى");
        adapter.addFragment(new AccessoriesFragment(), "اكسسوارات");
        adapter.addFragment(new TabletsFragment(), "تابلت");
        adapter.addFragment(new MobileFragment(), "جوالات");

        viewPager.setAdapter(adapter);

    }


//    public void showFloatingActionButton() {
//        fab.show();
//    }
//
//    public void hideFloatingActionButton() {
//        fab.hide();
//    }


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
                            return true;
                        case R.id.navigation_sms:
                            openFragment(ChatFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_notifications:
                            openFragment(NotificationFragment.newInstance("", ""));
                            return true;
                        case R.id.navigation_home:
                            FragmentManager manager = getSupportFragmentManager();
                            FragmentTransaction trans = manager.beginTransaction();
                            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            trans.commit();
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
                break;
//            case R.id.fab:
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.add(R.id.main, AddAdvFragment.newInstance("", ""));
//                transaction.addToBackStack(null);
//                transaction.commit();
//                break;
        }
    }

}


class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
