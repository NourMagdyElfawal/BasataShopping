package com.souqmaftoh.basatashopping.fragments.other;

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

public class OthersFragment extends Fragment {

    private OthersViewModel mViewModel;
    private RecyclerView recyclerView;
    HomeAdapter homeAdapter;


    public static OthersFragment newInstance() {
        return new OthersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OthersViewModel.class);
        // TODO: Use the ViewModel
    }
    private ArrayList<category> prepareImages() {
        // here you should give your image URLs and that can be a link from the Internet
        String imageUrls[] = {
//                "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/fruits.png",
                "https://homepages.cae.wisc.edu/~ece533/images/frymire.png",
                "https://homepages.cae.wisc.edu/~ece533/images/girl.png",
                "https://homepages.cae.wisc.edu/~ece533/images/monarch.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/boat.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/monarch.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
//                "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
                "https://homepages.cae.wisc.edu/~ece533/images/cat.png"};

        ArrayList<category> imageUrlList = new ArrayList<category>();
        for (int i = 0; i < imageUrls.length; i++) {
            category imageUrl = new category();
            imageUrl.setImageUrl(imageUrls[i]);
            imageUrlList.add(imageUrl);
        }
        Log.d("AccessoriesFragment", "List count: " + imageUrlList.size());
        return imageUrlList;
    }

    private ArrayList<category> prepareTitles() {
        // here you should give your image titles and that can be a link from the Internet
        String itemTitles[] = {
//                "أبل",
//                "سامسونج",
//                "هواوي",
                "اتش تي سي",
                "سوني",
                "ال جي",
//                "الكاتيل",
//                "اسوس",
//                "نوكيا",
//                "شاومي",
//                "اوبو",
//                "لينوفو",
                "بلاك بيري",
        };

        ArrayList<category> imageTitleList = new ArrayList<>();
        for (int i = 0; i < itemTitles.length; i++) {
            category imageTitle = new category();
            imageTitle.setName(itemTitles[i]);
            imageTitleList.add(imageTitle);
        }
        Log.d("OthersFragment", "List count: " + imageTitleList.size());
        return imageTitleList;

    }

}
