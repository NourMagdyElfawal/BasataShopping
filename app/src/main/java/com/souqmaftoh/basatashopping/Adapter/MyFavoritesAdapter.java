package com.souqmaftoh.basatashopping.Adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.souqmaftoh.basatashopping.Interface.Items;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.fragments.ItemDetailsFragment;
import com.souqmaftoh.basatashopping.recycleViewViewHolder.ItemsViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFavoritesAdapter extends RecyclerView.Adapter<ItemsViewHolder> {
    private static final String TAG = "MyItemsAdapter";
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;
    public HashMap<String, String> hashMapItem = new HashMap<>();

    private ItemsAdapter.Callback mCallback;
    private List<Items> mSportList;


    public MyFavoritesAdapter(ArrayList<Items> sportList) {
        mSportList = sportList;

    }


    public void setCallback(ItemsAdapter.Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new MyFavoritesAdapter.ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.list_my_favorites, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new MyFavoritesAdapter.EmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mSportList != null && mSportList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mSportList != null && mSportList.size() > 0) {
            return mSportList.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<Items> sportList) {
        mSportList.addAll(sportList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }

    public class ViewHolder extends ItemsViewHolder {

        @BindView(R.id.thumbnail)
        ImageView coverImageView;

        @BindView(R.id.price)
        TextView priceTextView;

        @BindView(R.id.address_my_ads)
        TextView addressTextView;

        @BindView(R.id.category_my_ads)
        TextView categoryTextView;

        @BindView(R.id.img_offer)
        ImageView img_offer;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {
            coverImageView.setImageDrawable(null);
            priceTextView.setText("");
            addressTextView.setText("");
            categoryTextView.setText("");

        }

        public void onBind(int position) {
            super.onBind(position);

            final Items mSport = mSportList.get(position);

            if (mSport.getmAdKey() != null) {
                hashMapItem.put("ad_key",mSport.getmAdKey());
                Log.e("ad_key",mSport.getmAdKey());
            }


            if (mSport.getImageUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(mSport.getImageUrl())
                        .into(coverImageView);
                hashMapItem.put("ImageUrl",mSport.getImageUrl());
            }

//            if (!mSport.getPrice()) {
                String price= String.valueOf(mSport.getPrice());
            if(price!=null){
                priceTextView.setText(price);
                hashMapItem.put("Title", price);
            }

            if (mSport.getTitle() != null) {
                addressTextView.setText(mSport.getTitle());
                hashMapItem.put("SubTitle",mSport.getTitle());
            }

            if (mSport.getDescription() != null) {
                categoryTextView.setText(mSport.getDescription());
                hashMapItem.put("Info", String.valueOf(mSport.getDescription()));
            }

            if (mSport.getItem_condition() != null) {
                hashMapItem.put("Item_condition",mSport.getItem_condition());

            }




            if (mSport.getSub_category() != null) {
                hashMapItem.put("Sub_category",mSport.getSub_category());

            }


            if (mSport.getStatus() != null) {
                hashMapItem.put("Status",mSport.getStatus());

            }




//                itemView.setOnClickListener(v -> {
//                    if (mSport.getImageUrl() != null) {
//                        try {
//                            Intent intent = new Intent();
//                            intent.setAction(Intent.ACTION_VIEW);
//                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
//                            intent.setData(Uri.parse(mSport.getImageUrl()));
//                            itemView.getContext().startActivity(intent);
//                        } catch (Exception e) {
//                            Log.e(TAG, "onClick: Image url is not correct");
//                        }
//                    }
//                });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    ItemDetailsFragment itemDetailsFragment = new ItemDetailsFragment();
                    Bundle args = new Bundle();
                    Log.e("hashMapMyFavItem", String.valueOf(hashMapItem));
                    args.putSerializable("hashMapMyFavItem",hashMapItem);
                    args.putString("fragment", "itemsAdapter");
                    args.putString("ad_key",mSport.getmAdKey());
                    itemDetailsFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().add(R.id.items_my_fav,itemDetailsFragment ).addToBackStack( "ItemsRecyclerFragment" ).commit();


                }
            });
        }
    }

    public class EmptyViewHolder extends ItemsViewHolder {

        @BindView(R.id.tv_message)
        TextView messageTextView;
        @BindView(R.id.buttonRetry)
        TextView buttonRetry;

        EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            buttonRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onEmptyViewRetryClick();
                }
            });
        }

        @Override
        protected void clear() {

        }

    }
}