package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class PinLocationActivity extends AppCompatActivity {

    LocationManager locationManager;
    EditText courseCodeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_location);
    }

    public void pinLocationButtonClick(View view) {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());
            Log.i("UserLocation", location.toString());

            ParseObject userMapLocation = new ParseObject("UserMapLocation");

            courseCodeEditText = (EditText) findViewById(R.id.courseCodeEditText);
            final String courseCode = courseCodeEditText.getText().toString();

            userMapLocation.put("username", ParseUser.getCurrentUser().getUsername());
            userMapLocation.put("latitude", location.getLatitude());
            userMapLocation.put("longitude", location.getLongitude());
            userMapLocation.put("courseCode", courseCode);

            userMapLocation.saveInBackground();

        }catch (SecurityException e){
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("activityID", "UserListActivity");
        startActivity(intent);
    }
}
