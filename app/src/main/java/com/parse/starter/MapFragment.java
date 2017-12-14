package com.parse.starter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Onique on 2017-11-29.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView googleMap;
    View mView;

    LocationManager locationManager;
    LocationListener locationListener;

    private final static int MY_PERMISSION_FINE_LOCATION = 101;

    public static final String TAG = "MapFragment";
    private Button chatHistoryButton;
    private Button pinLocationButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.map_fragment, container, false);

        chatHistoryButton = (Button) mView.findViewById(R.id.chatHistoryButton);
        chatHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserListActivity.class);
                startActivity(intent);
            }
        });

        pinLocationButton = (Button) mView.findViewById(R.id.pinLocationButton);
        pinLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PinLocationActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        googleMap = (MapView) mView.findViewById(R.id.map);
        if (googleMap != null) {
            googleMap.onCreate(null);
            googleMap.onResume();
            googleMap.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        mGoogleMap.clear();

        //TODO: Permissions
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            mGoogleMap.setMyLocationEnabled(true);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }
        }
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
            //default camera view
            LatLng userLocation = new LatLng(43.9458718, -78.8967375);

            //mGoogleMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            ParseQuery<ParseObject> mapMarkersQuery = new ParseQuery<ParseObject>("UserMapLocation");

            mapMarkersQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
            mapMarkersQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null){
                        if (objects.size() > 0){
                            for (ParseObject UserMapLocation : objects){
                                String courseCode = UserMapLocation.getString("courseCode");
                                String user = UserMapLocation.getString("username");
                                LatLng markerLocation = new LatLng(UserMapLocation.getDouble("latitude"),UserMapLocation.getDouble("longitude"));

                                mGoogleMap.addMarker(new MarkerOptions().position(markerLocation).title(user + " : " + courseCode));
                                Log.i("marker course codes", courseCode);
                                Log.i("latlng", markerLocation.toString());
                            }
                        }
                    }
                }
            });
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                }else {
                    Toast.makeText(getActivity(),"This app requires location permissions to be granted", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                }
                break;
        }
    }
}
