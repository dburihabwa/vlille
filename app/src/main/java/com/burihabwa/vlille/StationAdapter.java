package com.burihabwa.vlille;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by dorian on 9/27/14.
 */
public class StationAdapter extends ArrayAdapter<Station> {
    private StationStore stationStore;
    private List<Station> stations;
    private LayoutInflater inflater;

    public StationAdapter(Context context, int resource, StationStore stationStore) {
        super(context, resource);
        this.stationStore = stationStore;
        this.stations = new ArrayList<Station>(stationStore.getStations());
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AsyncTask<Void, Void, Void> stationsUpdater = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                for (Station station : StationAdapter.this.stations) {
                    StationAdapter.this.stationStore.getStation(station.getId());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                StationAdapter.this.notifyDataSetChanged();
            }
        };
        stationsUpdater.execute();

    }

    @Override
    public int getCount() {
        return stations.size();
    }

    @Override
    public Station getItem(int i) {
        return stations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, final View view, final ViewGroup viewGroup) {
        if (i >= stations.size()) {
            return null;
        }
        final int id = stations.get(i).getId();
        final Station station = stations.get(i);
        Calendar sixtySecondsAgo = new GregorianCalendar();
        sixtySecondsAgo.setTimeInMillis(sixtySecondsAgo.getTimeInMillis() - 60000L);
        if (station.getLastUpdate() == null || station.getLastUpdate().before(sixtySecondsAgo)) {
            AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    StationAdapter.this.stations.get(i);
                    stationStore.getStation(id);
                    return null;
                }
            };

            task.execute();
        }
        GridLayout layout;
        if (view != null && view instanceof GridLayout) {
            layout = (GridLayout) view;
        } else {
            layout = (GridLayout) inflater.inflate(R.layout.station_view, viewGroup, false);
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                Intent intent = new Intent(context, StationDetail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("station", station);
                context.startActivity(intent);
            }
        });

        TextView tvName = (TextView) layout.findViewById(R.id.stationName);
        tvName.setText(station.getName());
        TextView tvBikes = (TextView) layout.findViewById(R.id.stationBikes);
        tvBikes.setText(String.format("\uD83D\uDEB2" + " %-3d" + "\u2205" + " %-3d", station.getBikes(), station.getFreeSockets()));
        ImageView imageView = (ImageView) layout.findViewById(R.id.carteBleueImageView);
        if (station.hasCreditCardTerminal()) {
            Log.d("creditcardtermnial", station.getName() + " (" + id + ") " + " has a credit card terminal");
            imageView.setVisibility(View.VISIBLE);
        } else {
            Log.d("creditcardtermnial", id + " does not have a credit card terminal");
            imageView.setVisibility(View.INVISIBLE);
        }

        return layout;
    }
}
