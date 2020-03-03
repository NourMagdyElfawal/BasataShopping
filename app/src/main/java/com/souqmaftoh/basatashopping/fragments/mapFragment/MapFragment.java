package com.souqmaftoh.basatashopping.fragments.mapFragment;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;
import com.souqmaftoh.basatashopping.MainActivity;
import com.souqmaftoh.basatashopping.R;
import com.souqmaftoh.basatashopping.RegistrationActivityTow;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapViewModel mViewModel;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=101;
    private  GoogleMap mMap;
    private double longitude,latitude;
    Geocoder geocoder;
    public HashMap<String,String> mapstep2= new HashMap<>();
    TextView mptxt;
    private Context context;
    Boolean flag=false;
    String email,name,password,repPassword;
    String market_name,address,phone,description;




    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null &&getArguments().getSerializable("step2")!=null) {
            mapstep2 = (HashMap<String,String>)getArguments().getSerializable("step2");
            Log.e("mapstep2", String.valueOf(mapstep2));
            flag=true;
        }


//                name = getArguments().getString("name");
//                email = getArguments().getString("email");
//                password = getArguments().getString("password");
//                repPassword = getArguments().getString("repPassword");
//
//                market_name = getArguments().getString("market_name");
//                address = getArguments().getString("address");
//                phone = getArguments().getString("phone");
//                description = getArguments().getString("description");


//            bitmap = (Bitmap) getIntent().getParcelableExtra("bitmap");


//            latitude = Double.parseDouble(getArguments().getString("Lat"));
//            longitude = Double.parseDouble(getArguments().getString("Long"));
//
//            Log.e("lat", String.valueOf(latitude));
//            Log.e("lng", String.valueOf(longitude));
//
//        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.map_fragment, container, false);
        mptxt=(TextView)view.findViewById(R.id.mptxt);
       fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        fetchLastLocation();
       MapView mapView = (MapView) view.findViewById(R.id.google_map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment

//        setupAutoCompleteFragment();





        return view;

    }

    private void fetchLastLocation() {
        if ( ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( getActivity(), new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  },
                    REQUEST_CODE );
            return;
        }

        Task<Location> task=fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    currentLocation = location;
//                    Toast.makeText(getContext(), (int) currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    Log.e("lat", String.valueOf(currentLocation.getLatitude()));
                    Log.e("lng", String.valueOf(currentLocation.getLongitude()));
                    latitude=currentLocation.getLatitude();
                    longitude=currentLocation.getLongitude();

                    putAddress(latitude,longitude);
                    mapstep2.put("lat", String.valueOf(latitude));
                    mapstep2.put("lng", String.valueOf(longitude));


//                    SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
//                    FragmentTransaction fragmentTransaction =
//                           getChildFragmentManager().beginTransaction();
//                    fragmentTransaction.add(R.id.google_map, mMapFragment);
//                    fragmentTransaction.commit();
//                    mMapFragment.getMapAsync(con);


//                    SupportMapFragment supportMapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.google_map);
//                    supportMapFragment.getMapAsync(this);
                }
                }
        });

    }


//    private void setupAutoCompleteFragment() {
//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                sydney = place.getLatLng();
//                mapFragment.getMapAsync(this);
//            }
//
//            @Override
//            public void onError(Status status) {
//                Log.e("Error", status.getStatusMessage());
//            }
//        });
//    }



    private void putAddress(double latitude, double longitude) {
        geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            if (geocoder.isPresent()) {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (addresses != null&&addresses.size()>0 ) {
                    Address returnedAddress = addresses.get(0);
                    StringBuilder strReturnedAddress = new StringBuilder("");
                    Log.e("MaxAddressLine", String.valueOf(returnedAddress.getMaxAddressLineIndex()));
                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                        strReturnedAddress.append(returnedAddress.getAddressLine(i));
                        Log.e("count", String.valueOf(i));
                    }
                    Log.e("Myaddress",strReturnedAddress.toString());
                    mptxt.setText(strReturnedAddress.toString());
                } else {
                    Log.e("Myaddress", "No Address returned!");
                }
                String locdescSt = mptxt.getText().toString();
                //    mapstep2.put("locdesc", Tools.encodeStr(locdescSt.replaceAll("\\r"," ").replaceAll("\\n"," ")).replace("+", "%20"));
            }
        } catch(IOException e){
            Log.e("MyCurrentaddress", "Canont get Address!");
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        if (mMap!=null) {
            LatLng latLng = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("My Location");
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
            googleMap.addMarker(markerOptions);
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
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    Toast.makeText(getActivity(), "Back press", Toast.LENGTH_SHORT).show();
                    if(flag) {
                        Intent intent = new Intent(getActivity(), RegistrationActivityTow.class);
                        intent.putExtra("mapstep2",mapstep2);

//                        String lat= String.valueOf(latitude);
//                        String lng=String.valueOf(longitude);
//                        intent.putExtra("Lat", lat);
//                        intent.putExtra("Lng", lng);
                        Objects.requireNonNull(getActivity()).setResult(RESULT_OK,intent);
                        getActivity().finish();
                        Log.e("mapstep2", String.valueOf(mapstep2));
//                        if(lat!=null&&!lat.isEmpty()){
//                            intent.putExtra("Lat", lat);
//                        }
//                        if(lng!=null&&!lng.isEmpty()){
//                            intent.putExtra("Lng",lng );
//                        }
//                        if(name!=null&&!name.isEmpty()){
//                            intent.putExtra("name",name);
//                        }
//                        if(email!=null&&!email.isEmpty()){
//                            intent.putExtra("email",email);
//                        }
//                        if(password!=null&&!password.isEmpty()){
//                            intent.putExtra("password",password);
//                        }
//                        if(repPassword!=null&&!repPassword.isEmpty()){
//                            intent.putExtra("repPassword",repPassword);
//                        }
//
//
//                        if(market_name!=null&&!market_name.isEmpty()){
//                            intent.putExtra("market_name",market_name);
//                        }
//                        if(address!=null&&!address.isEmpty()){
//                            intent.putExtra("address",address);
//                        }
//                        if(phone!=null&&!phone.isEmpty()){
//                            intent.putExtra("phone",phone);
//                        }
//                        if(description!=null&&!description.isEmpty()){
//                            intent.putExtra("description",description);
//                        }

                        startActivity(intent);

                    }else {
                        Intent intent = new Intent(getActivity(), MapFragment.class);
                        intent.putExtra("Lat", String.valueOf(latitude));
                        intent.putExtra("Lng", String.valueOf(longitude));
                        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                        getFragmentManager().popBackStack();
                    }


                    return true;
                }
                return false;
            }
        });

    }


}
