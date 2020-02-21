package com.souqmaftoh.basatashopping.fragments.addAdv;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.souqmaftoh.basatashopping.Adapter.addAdvAdapter;
import com.souqmaftoh.basatashopping.Api.RetrofitClient;
import com.souqmaftoh.basatashopping.Interface.Advertise;
import com.souqmaftoh.basatashopping.Interface.addAdvImageModelClass;
import com.souqmaftoh.basatashopping.LoginActivity;
import com.souqmaftoh.basatashopping.MainActivity;
import com.souqmaftoh.basatashopping.Models.DefaultResponse;
import com.souqmaftoh.basatashopping.Interface.User;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.Storage.SharedPrefManager;
import com.souqmaftoh.basatashopping.design.CurvedBottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AddAdvFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private SlideshowViewModel addAdvViewModel;
    CurvedBottomNavigationView mView;
    MainActivity mainActivity;
    CardView card_telephone,card_confirm;
    ImageView addProductImg;
    private static final int PICK_PHOTO_FOR_AVATAR = 0;
    private static final int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;
    ArrayList<addAdvImageModelClass> items = new ArrayList<>();
    addAdvAdapter adapter;
    EditText et_advName,et_AdvDescription,et_AdvAddress,et_telephone;
    String encodedImage,token;
    String item_condition,category;
    int sub_category;

//    List<category> historicList = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MaterialButton btn_addAdv;
    Spinner   spinnerCat,spin_sub_cat,spin_spin_con;

    public AddAdvFragment() {
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
    public static AddAdvFragment newInstance(String param1, String param2) {
        AddAdvFragment fragment = new AddAdvFragment();
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
        addAdvViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_addadv, container, false);
        setHasOptionsMenu(true);

        et_advName=root.findViewById(R.id.et_advName);
        et_AdvDescription=root.findViewById(R.id.et_AdvDescription);
        et_AdvAddress=root.findViewById(R.id.et_AdvAddress);
        et_telephone=root.findViewById(R.id.et_telephone);

        btn_addAdv=root.findViewById(R.id.btn_addAdv);
        btn_addAdv.setOnClickListener(this);



        User user= SharedPrefManager.getInstance(getActivity()).getUser();
        if(user!=null) {
            if (user.getToken() != null && !user.getToken().isEmpty()) {
                token = user.getToken();

            }
        }


        // Spinner element
        spinnerCat = root.findViewById(R.id.spinner_cat);
        spin_sub_cat = root.findViewById(R.id.spin_sub_cat);
        spin_spin_con = root.findViewById(R.id.spin_spin_con);


        spinnerCategories();
        spinnerSunCategories();
        spinnerCondition();







        adapter = new addAdvAdapter(getActivity(), items);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));

        recyclerView.setAdapter(adapter);

// let's create 10 random items

        for (int i = 0; i < 10; i++) {
//            items.add(new addAdvImageModelClass(R.drawable.ic_add_image));
//            adapter.notifyDataSetChanged();
        }



        addProductImg=root.findViewById(R.id.addProductImg);
        addProductImg.setOnClickListener(this);

        card_telephone=root.findViewById(R.id.card_telephone);
        card_confirm=root.findViewById(R.id.card_AdvAddress);
        mView = root.findViewById(R.id.customBottomBar);
        mView.setOnNavigationItemSelectedListener(AddAdvFragment.this);

        String item;
//        if (getArguments() != null) {
//           item= getArguments().getString("fragment");
//            if(item!=null&&item.equalsIgnoreCase("item")){
//                mView.setVisibility(View.VISIBLE);
//                card_telephone.setVisibility(View.GONE);
//                card_confirm.setVisibility(View.GONE);
//
//            }
//        }


//        addAdvViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private void spinnerCondition() {

        // Spinner click listener
        spin_spin_con.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.e("position", String.valueOf(position));
                if(position==0){
                    item_condition="new";

                }else if(position==1){
                    item_condition="old";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                item_condition="new";

            }
        });

        // Spinner Drop down elements
        List<String> item_condition = new ArrayList<String>();
        item_condition.add("جديد");
        item_condition.add("مستعمل");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, item_condition);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_spin_con.setAdapter(dataAdapter);



    }

    private void spinnerSunCategories() {
        // Spinner click listener
        spin_sub_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.e("position", String.valueOf(position));

                switch (position){
                    case 0:
                        sub_category=0;
                        break;
                    case 1:
                        sub_category=1;
                        break;
                    case 2:
                        sub_category=2;
                        break;
                    case 3:
                        sub_category=3;
                        break;


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sub_category=0;

            }
        });

        // Spinner Drop down elements
        List<String> sub_categories = new ArrayList<String>();
        sub_categories.add("samsung");
        sub_categories.add("iphon");
        sub_categories.add("nokia");
        sub_categories.add("infinix");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sub_categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_sub_cat.setAdapter(dataAdapter);



    }

    private void spinnerCategories() {
        // Spinner click listener
        spinnerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.e("position", String.valueOf(position));
//                Log.e("category", String.valueOf(view));


                switch (position){
                    case 0:
                        category="جوالات";
                        break;
                    case 1:
                        category="تابلت";
                        break;
                    case 2:
                        category="اكسسوارات";
                        break;
                    case 3:
                        category="أخرى";
                        break;


                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category="جوالات";

            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("جوالات");
        categories.add("تابلت");
        categories.add("اكسسوارات");
        categories.add("أخرى");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCat.setAdapter(dataAdapter);


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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addProductImg:
               pickImage() ;
                break;

            case R.id.btn_addAdv:
                AddAdvertiseByApi();
                break;
            }

        }

    public void pickImage() {
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_PHOTO_FOR_AVATAR);
            } else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_PHOTO_FOR_AVATAR:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore
                            .Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_PHOTO_FOR_AVATAR);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
//            if (data == null) {
//                //Display an error
//                return;
//            }
//            InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
//            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
//        }
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//
//            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                    filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            Log.e("picturePath",picturePath);
//            addProductImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                    addProductImg.setImageURI(selectedImage);

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(), selectedImage);
                Bitmap converetdImage = getResizedBitmap(bitmap, 500);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                converetdImage.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();

                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }

//            Bitmap bm = BitmapFactory.decodeFile(String.valueOf(selectedImage));




            items.add(new addAdvImageModelClass(selectedImage));
            adapter.notifyDataSetChanged();
//            items.add(new addAdvImageModelClass(selectedImage));
//            adapter.notifyDataSetChanged();



        }



    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    private void AddAdvertiseByApi() {

        String title=et_advName.getText().toString();
        int price= Integer.parseInt(et_AdvAddress.getText().toString());
        String description=et_AdvDescription.getText().toString();
//        int sub_category= Integer.parseInt(et_advName.getText().toString());
        String main_image=encodedImage;
//        String item_condition="new";



        Call call= RetrofitClient.
                getInstance()
                .getApi()
                .createAd(title,price,description,1,item_condition,main_image);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if(response!=null) {

                    if(response.body()!=null){
                        Log.e("gson:create_ad", new Gson().toJson(response.body()) );
                        try {
                            JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                            String message = jsonObject.getString("message");
                            if (message != null) {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                                Intent intent_log =new Intent(getActivity(), MainActivity.class);
                                intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent_log);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (response.errorBody() != null) {
                        try {
                            Log.e("gson_Error:create_ad", response.errorBody().string());
                            if(response.errorBody().string().equalsIgnoreCase("Unauthenticated.")){
                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                startActivity(i);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    if (response.isSuccessful()) {
                        Log.e("res:create_ad", "isSuccessful");
                    }

                }
//                DefaultResponse dr=response.body();
//                if (dr != null && dr.getMessage() != null) {
//                    Toast.makeText(getActivity(), dr.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.e("res:create_ad", "Data"+dr.getData()+" "+"message"+dr.getMessage());
//                    Advertise advertise=new Advertise(title,price,description,main_image,sub_category);
//                    Intent intent_log =new Intent(getActivity(), MainActivity.class);
//                    intent_log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent_log);
//
//
//                }
            }


            @Override
            public void onFailure(Call call, Throwable t) {
//                Toast.makeText(RegistrationActivityOne.this, t, Toast.LENGTH_SHORT).show();
                Log.e("AddAdv:onFailure", String.valueOf(t));

            }
        });
    }



}
