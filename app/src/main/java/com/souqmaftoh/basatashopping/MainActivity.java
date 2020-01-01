package com.souqmaftoh.basatashopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.robertlevonyan.views.customfloatingactionbutton.FloatingActionButton;
import com.souqmaftoh.basatashopping.fragments.HomeFragment;
import com.souqmaftoh.basatashopping.fragments.ItemsRecyclerFragment.ItemsRecyclerFragment;
import com.souqmaftoh.basatashopping.fragments.MyAccountFragment;
import com.souqmaftoh.basatashopping.fragments.accessories.AccessoriesFragment;
import com.souqmaftoh.basatashopping.fragments.addAdv.AddAdvFragment;
import com.souqmaftoh.basatashopping.fragments.chat.ChatFragment;
import com.souqmaftoh.basatashopping.fragments.notification.NotificationFragment;
import com.souqmaftoh.basatashopping.fragments.other.OthersFragment;
import com.souqmaftoh.basatashopping.fragments.tablets.TabletsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.container, new HomeFragment())
//                .commit();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        //bottom bar
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
    //    openFragment(HomeFragment.newInstance("", ""));

        fab = findViewById(R.id.fab);
//        showFloatingActionButton();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                openFragment(AddAdvFragment.newInstance("", ""));
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
            Toast.makeText(this, "gmailInfo"+personName+personGivenName+personFamilyName+personEmail+personId, Toast.LENGTH_SHORT).show();
        }
    }

    //app bar
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OthersFragment(), "اخرى");
        adapter.addFragment(new AccessoriesFragment(), "اكسسوارات");
        adapter.addFragment(new TabletsFragment(), "تابلت");
        adapter.addFragment(new HomeFragment(), "جوالات");

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

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                        case R.id.navigation_Profile:
                            openFragment(MyAccountFragment.newInstance("", ""));
                            return true;

                    }
                    return false;
                }
            };
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
