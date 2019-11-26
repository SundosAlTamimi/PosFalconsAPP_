package com.falconssoft.app_pos;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class location_branch extends FragmentActivity implements OnMapReadyCallback {
    //    //AIzaSyC_FdHM0UTVEEbUo8-Wrqh18_a6pUNEBz8
    //    //AIzaSyD-NG9v3imQCKzbl7Y8oppO3Fh_apPYOAc
    LocationManager locationManager;
    LocationListener locationListener;
    double latitude,longitude;
    private GoogleMap mMap;
    //    private GoogleMap mMap;
    private LatLng mOrigin;
    private LatLng mDestination;
    private Polyline mPolyline;
    ArrayList<LatLng> mMarkerPoints;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_branch);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
//        mMarkerPoints = new ArrayList<>();


       ///////////////////////////////////////////**********************************
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(location_branch.this);
//                fusedLocationClient.getLastLocation()
//                        .addOnSuccessListener(location_branch.this, new OnSuccessListener<Location>() {
//                                    @Override
//                                    public void onSuccess(Location location) {
//                                        // Got last known location. In some rare situations this can be null.
//                                        if (location != null) {
//                                            latitude = location.getLatitude();
//                                            longitude = location.getLongitude();
//                                            Log.e("loca", "lati" + latitude + "\t" + longitude);
//                                            Geocoder geocoder = new Geocoder(getApplicationContext());
//                                            try {
//                                                List<Address> addresses =
//                                                        geocoder.getFromLocation(latitude, longitude, 1);
//                                                Log.e("location**", addresses.toString());
//                                                String result = addresses.get(0).getLocality() + ": " + "tahani";
//                                                //result += addresses.get(0).getCountryName();
//                                                LatLng latLng = new LatLng(latitude, longitude);
//                                                mMap.addMarker(new MarkerOptions().position(latLng).title(result).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//                                                mMap.setMaxZoomPreference(20);
//                                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
//
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            }
//
//                                        }
//                                        // Logic to handle location object
//
//                                    }
//                                });


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMaxZoomPreference(20);
        PolylineOptions polylineOptions = new PolylineOptions();
//        LatLng latLng = new LatLng(31.971232, 35.912408);
        LatLng first= new LatLng(31.971232, 35.912408);
        LatLng second = new LatLng(31.963114, 35.923617);
        mMap.addMarker(new MarkerOptions().position(first).title("Me").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        polylineOptions.add(first)
                .color(Color.RED)
                .width(2);
        mMap.addPolyline(polylineOptions);
        mMap.addMarker(new MarkerOptions().position(second).title(" Branch 1").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first, 14.0f));

        polylineOptions.add(second)
                .color(Color.RED)
                .width(2);
        mMap.addPolyline(polylineOptions);



    }
}
