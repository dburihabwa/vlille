package com.burihabwa.vlille;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;


public class MainActivity extends Activity {
    private StationStore store = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                MainActivity.this.store = new StationStore();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                StationAdapter adapter = new StationAdapter(getApplicationContext(), R.id.stationsView, MainActivity.this.store);
                ListView listView = (ListView) findViewById(R.id.stationsListView);
                listView.setAdapter(adapter);
            }
        };
        task.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
