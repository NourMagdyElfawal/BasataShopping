package com.souqmaftoh.basatashopping.Adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.souqmaftoh.basatashopping.Interface.addAdvImageModelClass;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.fragments.addAdv.AddAdvFragment;

import java.util.ArrayList;

public class addAdvAdapter extends RecyclerView.Adapter<addAdvAdapter.CustomViewHolder> {

        private Context context;
        private ArrayList<addAdvImageModelClass> items;
        AddAdvFragment addAdvFragment;

        public addAdvAdapter(Context context, ArrayList<addAdvImageModelClass> items) {
            this.context = context;
            this.items = items;
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_addadv_image, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
//            holder.itemTitle.setText(items.get(position).getTitle());
            holder.itemImage.setImageResource(items.get(position).getImage());
            if(position==0){
            holder.itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                        pickImage();
                    Log.e("selectedImage", "0");

                }
                });

            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder {

            private ImageView itemImage;
            private TextView itemTitle;

            public CustomViewHolder(View view) {
                super(view);
                itemImage = view.findViewById(R.id.item_image);
//                itemTitle = view.findViewById(R.id.item_title);
            }
        }
    }
