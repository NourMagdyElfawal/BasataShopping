package com.souqmaftoh.basatashopping.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.souqmaftoh.basatashopping.fragments.HomeFragment.HomeViewModel;
import com.souqmaftoh.basatashopping.MainActivity;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.design.CurvedBottomNavigationView;

public class ItemDetailsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    CurvedBottomNavigationView mView;
    MainActivity mainActivity;
    CardView card_telephone,card_confirm;

    //    List<category> historicList = new ArrayList<>();

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    public static ItemDetailsFragment newInstance() {
        return new ItemDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.item_details_fragment, container, false);

        card_telephone=root.findViewById(R.id.card_telephone);
        card_confirm=root.findViewById(R.id.card_AdvAddress);
        mView = root.findViewById(R.id.customBottomBar);
        mView.setOnNavigationItemSelectedListener(ItemDetailsFragment.this);
//        ((MainActivity) Objects.requireNonNull(getActivity())).hideFloatingActionButton();
//        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        return root;
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
}
