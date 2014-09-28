package com.burihabwa.vlille;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.burihabwa.vlille.Station;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;


public class StationDetail extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);
        Log.d("StationDetail", "In station detail");
        Intent intent = getIntent();

        Station station = (Station) intent.getSerializableExtra("station");
        Log.d("StationDetail", "In station detail");
        TextView tvName = (TextView) findViewById(R.id.name);
        tvName.setText(station.getName());
        TextView tvAddress = (TextView) findViewById(R.id.address);
        tvAddress.setText(station.getAddress());
        TextView tvBikes = (TextView) findViewById(R.id.bikes);
        tvBikes.setText(String.format("\uD83D\uDEB2" + " %-3d" + "\u2205" + " %-3d", station.getBikes(), station.getFreeSockets()));

        MapView mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        GoogleMap map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        MapsInitializer.initialize(this.getApplicationContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.station_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
