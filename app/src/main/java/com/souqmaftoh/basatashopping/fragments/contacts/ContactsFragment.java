package com.souqmaftoh.basatashopping.fragments.contacts;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.souqmaftoh.basatashopping.ChatActivity;
import com.souqmaftoh.basatashopping.Interface.Contacts;
import com.souqmaftoh.basatashopping.Interface.Messages;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.design.DividerItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ContactsFragment extends Fragment {

    private ContactsViewModel mViewModel;
    private View ContactsView;
    private RecyclerView myContactsList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DatabaseReference ContacsRef, UsersRef,MessagesRef;
    private FirebaseAuth mAuth;
    private String currentUserID;
    LinearLayoutManager mLayoutManager;



    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ContactsView= inflater.inflate(R.layout.contacts_fragment, container, false);

        myContactsList=(RecyclerView) ContactsView.findViewById(R.id.contacts_list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        myContactsList.setLayoutManager(mLayoutManager);
        myContactsList.setItemAnimator(new DefaultItemAnimator());
        Drawable dividerDrawable = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.divider_drawable);
        myContactsList.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            currentUserID = mAuth.getCurrentUser().getUid();


            ContacsRef = FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserID);
        }
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        MessagesRef = FirebaseDatabase.getInstance().getReference().child("Messages");



        return ContactsView;

    }

    @Override
    public void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Contacts>()
                        .setQuery(ContacsRef, Contacts.class)
                        .build();


        final FirebaseRecyclerAdapter<Contacts, ContactsViewHolder> adapter
                = new FirebaseRecyclerAdapter<Contacts, ContactsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ContactsViewHolder holder, int position, @NonNull Contacts model)
            {
                final String userIDs = getRef(position).getKey();

                UsersRef.child(userIDs).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
//                            if (dataSnapshot.child("userState").hasChild("state"))
//                            {
//                                String state = dataSnapshot.child("userState").child("state").getValue().toString();
//                                String date = dataSnapshot.child("userState").child("date").getValue().toString();
//                                String time = dataSnapshot.child("userState").child("time").getValue().toString();
//
//                                if (state.equals("online"))
//                                {
//                                    holder.onlineIcon.setVisibility(View.VISIBLE);
//                                }
//                                else if (state.equals("offline"))
//                                {
//                                    holder.onlineIcon.setVisibility(View.INVISIBLE);
//                                }
//                            }
//                            else
//                            {
//                                holder.onlineIcon.setVisibility(View.INVISIBLE);
//                            }


//                            if (dataSnapshot.hasChild("image"))
//                            {
////                                String userImage = dataSnapshot.child("image").getValue().toString();
//                                String profileName = dataSnapshot.child("name").getValue().toString();
//                                String profileEmail = dataSnapshot.child("email").getValue().toString();
//
//                                holder.userName.setText(profileName);
//                                holder.userEmail.setText(profileEmail);
////                                Picasso.get().load(userImage).placeholder(R.drawable.profile_image).into(holder.profileImage);
//                            }
//                            else
                            if (dataSnapshot.hasChild("email"))
                            {
                                String profileName = dataSnapshot.child("name").getValue(String.class);
                                String profileEmail = dataSnapshot.child("email").getValue(String.class);
                                String receiverId=dataSnapshot.child("id").getValue(String.class);

                                getLastMessage(receiverId,holder);


                                holder.userName.setText(profileName);
                                holder.userEmail.setText(profileEmail);
                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent chatIntent=new Intent(getContext(), ChatActivity.class);
                                        chatIntent.putExtra("visit_user_id",userIDs);
                                        chatIntent.putExtra("visit_user_name",profileName);
                                        startActivity(chatIntent);
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
            {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_display_layout, viewGroup, false);
                ContactsViewHolder viewHolder = new ContactsViewHolder(view);
                return viewHolder;
            }
        };

        myContactsList.setAdapter(adapter);
        adapter.startListening();
    }

    private void getLastMessage(String receiverId, ContactsViewHolder holder) {
        DatabaseReference ref = MessagesRef.child(currentUserID).child(receiverId);


        ref.orderByChild(currentUserID).limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot)
            {

                for(DataSnapshot child: dataSnapshot.getChildren()) {
                    String message = child.child("message").getValue().toString();

                //                if (dataSnapshot.exists()) {
//                    Log.e("last_message", String.valueOf(dataSnapshot));
////                    if (dataSnapshot.hasChild("message")) {
//                        Log.e("last_mes", String.valueOf(dataSnapshot.child("message").getValue()));
//                        Object lastMessage = dataSnapshot.child("messageID");
////                    String message = dataSnapshot.child("message").getValue().toString();
                    if (message != null) {
                        Log.e("last_message", message);
                        holder.lastMessage.setText(message);
                    }
                }
//                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public static class ContactsViewHolder extends RecyclerView.ViewHolder
    {
        TextView userName, userEmail,lastMessage;
//        CircleImageView profileImage;
//        ImageView onlineIcon;


        public ContactsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            userName = itemView.findViewById(R.id.user_profile_name);
            userEmail = itemView.findViewById(R.id.user_email);
            lastMessage=itemView.findViewById(R.id.user_last_message);
//            profileImage = itemView.findViewById(R.id.users_profile_image);
//            onlineIcon = (ImageView) itemView.findViewById(R.id.user_online_status);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ContactsViewModel.class);
        // TODO: Use the ViewModel
    }

}
