package com.wzy.leftswiperemove;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class resultPage extends AppCompatActivity {
    String result = "";
    int numOfSugg = 0;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        //Title Bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titlebar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffa500")));

        // getting the array from main
        Intent receive = getIntent();
        final ArrayList<String> data = new ArrayList<>();
        numOfSugg = receive.getIntExtra("numOfSugg", 0);
        final Random random = new Random();
        //link
        final TextView tvChosen = (TextView) findViewById(R.id.textViewChosen);
        Button btnFamous = (Button) findViewById(R.id.buttonFamous);
        ImageButton imgRefresh = (ImageButton) findViewById(R.id.imageButtonRefresh);
        Button btnNearby = (Button) findViewById(R.id.buttonNearby);


        //adding the mainactivity array to another array
        for (int i = 0; i < numOfSugg; i++) {
            data.add(receive.getStringArrayListExtra("suggestions").get(i));

        }

        result = data.get(random.nextInt(data.size()));
        tvChosen.setText(result);
        btnNearby.setText("Nearest " + result);

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = data.get(random.nextInt(data.size()));

                tvChosen.setText(result);


            }

        });


        btnFamous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlString = "https://www.google.com.sg/search?hl=en&ei=Ap_-WYLiAYiNvQSV6bwo&q=" + result + " is+famous+for+&oq=kfc+is+famous+for+&gs_l=psy-ab.3..0i22i30k1l2.7291.9231.0.9298.15.14.0.0.0.0.119.938.13j1.14.0....0...1.1.64.psy-ab..1.14.933...0j35i39k1j0i67k1j0i20i263k1.0.7e4ab1zEOZc";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    startActivity(intent);
                }
            }

        });

        btnNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();


            }


        });


    }

    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                String geoUri = "https://www.google.com.sg/maps/search/" + result + "/@" + longi + "," + latti + ",10z";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);

            } else {

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }
}
