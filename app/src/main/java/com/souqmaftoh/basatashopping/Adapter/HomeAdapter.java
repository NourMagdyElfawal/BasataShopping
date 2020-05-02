package com.souqmaftoh.basatashopping.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.souqmaftoh.basatashopping.Interface.category;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.fragments.ItemsRecyclerFragment.ItemsRecyclerFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> categoryList;
    private List<String> imageTitleList;
    private List<Integer> imageIdList;

    private String categoryType="";

    public <E> HomeAdapter(ArrayList<E> es) {
    }

    public HomeAdapter(FragmentActivity mContext, ArrayList<String> categoryList, ArrayList<String> imageTitleList, ArrayList<Integer> imageIdList) {
        this.mContext = mContext;
        this.categoryList = categoryList;
        this.imageTitleList=imageTitleList;
        this.imageIdList=imageIdList;

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public RelativeLayout vRoot;

        public MyViewHolder(View view) {
            super(view);
            vRoot = (RelativeLayout) view.findViewById(R.id.rl_root);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }

//    public HomeAdapter(Context mContext, List<category> categoryList, List<category> imageTitleList) {
//        this.mContext = mContext;
//        this.categoryList = categoryList;
//        this.imageTitleList=imageTitleList;
//    }
//
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        Glide.with(mContext).load(categoryList.get(position).getImageUrl()).into(holder.thumbnail);

        final String title = imageTitleList.get(position);
        final int subCategoryId = imageIdList.get(position);

        holder.title.setText(title);
        Glide.with(mContext).load(categoryList.get(position)).into(holder.thumbnail);
//        Glide.with(mContext).load(category.getImage()).into(holder.thumbnail);

        holder.vRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ItemsRecyclerFragment itemsRecyclerFragment = new ItemsRecyclerFragment();
                Bundle args = new Bundle();
                args.putInt("subCategoryId", subCategoryId);
                itemsRecyclerFragment.setArguments(args);

                activity.getSupportFragmentManager().beginTransaction().add(R.id.container, itemsRecyclerFragment).addToBackStack( "HomeAdapter" ).commit();

//                Fragment fragment = new tasks();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.content_frame, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

//                Log.e("imageTitleList","imageTitleList");
//                Intent intent = new Intent(mContext, DetailsActivity.class);
//                intent.putExtra("category", categoryList.get(position));
//                mContext.startActivity(intent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return categoryList.size();
    }


}
