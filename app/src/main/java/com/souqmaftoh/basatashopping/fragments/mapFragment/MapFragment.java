package com.souqmaftoh.basatashopping.fragments.mapFragment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
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
import com.google.android.gms.maps.model.Marker;
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
    private static final int REQUEST_CODE = 101;
    private GoogleMap mMap;
    private double longitude, latitude;
    Geocoder geocoder;
    public HashMap<String, String> mapstep2 = new HashMap<>();
    TextView mptxt;
    private Context context;
    Boolean flag = false;
    String email, name, password, repPassword;
    String market_name, address, phone, description;
    private Context globalContext = null;
    private Marker myMarker;


    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalContext = this.getActivity();
        if (getArguments() != null && getArguments().getSerializable("step2") != null) {
            mapstep2 = (HashMap<String, String>) getArguments().getSerializable("step2");
            Log.e("mapstep2", String.valueOf(mapstep2));
            flag = true;
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        mptxt = view.findViewById(R.id.mptxt);

        statusCheck();
//        fetchLastLocation();
        MapView mapView = (MapView) view.findViewById(R.id.google_map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment

//        setupAutoCompleteFragment()

        return view;

    }


    public void statusCheck() {

        if ( ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( getActivity(), new String[] {  Manifest.permission.ACCESS_FINE_LOCATION  },
                    REQUEST_CODE );
            return;
        }

        final LocationManager manager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);

        if (manager != null && !manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }else {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
            fetchLastLocation();
        }

        if (manager != null) {
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.e("requestLocationUpdates", String.valueOf(location.getLatitude()));
                    latitude =location.getLatitude();
                    longitude =location.getLongitude();

                    fetchMap(latitude,longitude);
                    addMarker(latitude,longitude);
                    putAddress(latitude,longitude);
                    mapstep2.put("lat", String.valueOf(latitude));
                    mapstep2.put("lng", String.valueOf(longitude));
                    Log.e("mapstep2", String.valueOf(mapstep2));

//                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
//                    fetchLastLocation();

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.e("requestLocationUpdates","onStatusChanged");
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.e("requestLocationUpdates","onProviderEnabled");
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.e("requestLocationUpdates","onProviderDisabled");

                }
            });


        }

    }

    private void fetchMap(double latitude, double longitude) {
        if (mMap!=null) {
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
        }

    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }







    private void fetchLastLocation() {

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

                }
                }
        });

    }



    private void putAddress(double latitude, double longitude) {

        geocoder = new Geocoder(globalContext, Locale.getDefault());
        try {
            if (Geocoder.isPresent()) {
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
            if(mMap!=null) {
                fetchMap(latitude, longitude);
                addMarker(latitude, longitude);
            }

    }

    private void addMarker( double lat, double lng) {
        if (mMap != null) {
            if (myMarker == null) {
                myMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng))
                        .title("My Event").snippet("Event Address"));
            } else {
                myMarker.remove();
                myMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng))
                        .title("My Event").snippet("Event Address"));
            }
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

        Log.e("onResume","onResume");

        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
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
                        Objects.requireNonNull(getActivity()).setResult(RESULT_OK,intent);
                        getActivity().finish();
                        Log.e("mapstep2", String.valueOf(mapstep2));
                        startActivity(intent);

                    }else {
                        Intent intent = new Intent(getActivity(), MapFragment.class);
                        intent.putExtra("Lat", String.valueOf(latitude));
                        intent.putExtra("Lng", String.valueOf(longitude));
                        Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
                        if (getFragmentManager() != null) {
                            getFragmentManager().popBackStack();
                        }
                    }


                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onStart","onstart");


    }


}
