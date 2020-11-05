package com.souqmaftoh.basatashopping.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.souqmaftoh.basatashopping.Interface.SliderItem;
import com.souqmaftoh.basatashopping.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends PagerAdapter {
    Context context;
    public   ImageView imageView;
    private LayoutInflater inflater;
    private String[] imageUrls;


    public SliderAdapter(Context context, String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.e("garbaging", "garbaging imageview at: " + position);

        container.removeView((ImageView) object);

    }


    @Override
    public int getCount() {
        return imageUrls.length;
//        return GalImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        imageView = new ImageView(context);
        Picasso.get()
                .load(imageUrls[position])
                .fit()
                .centerCrop()
                .into(imageView);


        container.addView(imageView, 0);

        return imageView;
    }



    @Override
    public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        super.restoreState(state, loader);
    }
}

