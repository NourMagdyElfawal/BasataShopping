package com.souqmaftoh.basatashopping.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.souqmaftoh.basatashopping.Adapter.HomeAdapter;
import com.souqmaftoh.basatashopping.Interface.category;
import com.souqmaftoh.basatashopping.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

//    List<category> historicList = new ArrayList<>();

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public HomeFragment() {
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.home_fragment, container, false);
//        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();

        recyclerView = (RecyclerView) root.findViewById(R.id.home_recycleviw);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);

        ArrayList<category> imageUrlList = prepareImages();
        ArrayList<category> imageTitleList = prepareTitles();
        homeAdapter = new HomeAdapter(getActivity(), imageUrlList, imageTitleList);

//        homeAdapter = new HomeAdapter(getActivity(), historicList);
        recyclerView.setAdapter(homeAdapter);

        return root;
    }


    private ArrayList<category> prepareImages() {
        // here you should give your image URLs and that can be a link from the Internet
        String imageUrls[] = {
                "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
                "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
                "https://homepages.cae.wisc.edu/~ece533/images/fruits.png",
                "https://homepages.cae.wisc.edu/~ece533/images/frymire.png",
                "https://homepages.cae.wisc.edu/~ece533/images/girl.png",
                "https://homepages.cae.wisc.edu/~ece533/images/monarch.png",
                "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
                "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
                "https://homepages.cae.wisc.edu/~ece533/images/boat.png",
                "https://homepages.cae.wisc.edu/~ece533/images/monarch.png",
                "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
                "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
                "https://homepages.cae.wisc.edu/~ece533/images/cat.png"};

        ArrayList<category> imageUrlList = new ArrayList<category>();
        for (int i = 0; i < imageUrls.length; i++) {
            category imageUrl = new category();
            imageUrl.setImageUrl(imageUrls[i]);
            imageUrlList.add(imageUrl);
        }
        Log.d("HomeFragment", "List count: " + imageUrlList.size());
        return imageUrlList;
    }

    private ArrayList<category> prepareTitles() {
        // here you should give your image titles and that can be a link from the Internet
        String itemTitles[] = {
                "أبل",
                "سامسونج",
                "هواوي",
                "اتش تي سي",
                "سوني",
                "ال جي",
                "الكاتيل",
                "اسوس",
                "نوكيا",
                "شاومي",
                "اوبو",
                "لينوفو",
                "بلاك بيري",
        };

        ArrayList<category> imageTitleList = new ArrayList<>();
        for (int i = 0; i < itemTitles.length; i++) {
            category imageTitle = new category();
            imageTitle.setName(itemTitles[i]);
            imageTitleList.add(imageTitle);
        }
        Log.d("HomeFragment", "List count: " + imageTitleList.size());
        return imageTitleList;

    }

}
