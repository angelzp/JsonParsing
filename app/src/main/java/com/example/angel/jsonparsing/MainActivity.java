package com.example.angel.jsonparsing;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;
    // URL to get autos JSON
    private static String url = "http://eulisesrdz.260mb.net/android/json-zapata.json";

    // JSON Node names
    private final String KEY_AUTO = "autos";
    private final String KEY_N_SERIE = "n_serie";
    private final String KEY_MARCA = "marca";
    private final String KEY_MODELO = "modelo";
    private final String KEY_COLOR = "color";
    // autos JSONArray
    JSONArray autos = null;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> autoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoList = new ArrayList<HashMap<String, String>>();
        ListView lv = getListView();
        // Listview on item click listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // getting values from selected ListItem
                String n_serie = ((TextView) view.findViewById(R.id.n_serie)).getText().toString();
                String marca = ((TextView) view.findViewById(R.id.txt_marca)).getText().toString();
                String modelo = ((TextView) view.findViewById(R.id.txt_modelo)).getText().toString();
                String color = ((TextView) view.findViewById(R.id.txt_color)).getText().toString();

                // Starting single auto activity
                Intent in = new Intent(getApplicationContext(),vista_individual.class);
                in.putExtra(KEY_N_SERIE, n_serie);
                in.putExtra(KEY_MARCA, marca);
                in.putExtra(KEY_MODELO, modelo);
                in.putExtra(KEY_COLOR, color);

                startActivity(in);
            }
        });
        // Calling async task to get json
        new GetAutos().execute();
    }
    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetAutos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            Log.d("Response: ", "> " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    autos = jsonObj.getJSONArray(KEY_AUTO);

                    // looping through All Autos
                    for (int i = 0; i < autos.length(); i++) {
                        JSONObject c = autos.getJSONObject(i);
                        String serie = c.getString(KEY_N_SERIE);
                        String marca = c.getString(KEY_MARCA);
                        String modelo = c.getString(KEY_MODELO);
                        String color = c.getString(KEY_COLOR);

                        // tmp hashmap for single auto
                        HashMap<String, String> auto = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        auto.put(KEY_N_SERIE, serie);
                        auto.put(KEY_MARCA, marca);
                        auto.put(KEY_MODELO, modelo);
                        auto.put(KEY_COLOR, color);

                        // adding contact to contact list
                        autoList.add(auto);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, autoList,
                    R.layout.lista_autos, new String[] {KEY_N_SERIE, KEY_MARCA, KEY_MODELO, KEY_COLOR},
                    new int[] { R.id.n_serie,R.id.txt_marca,R.id.txt_modelo,R.id.txt_color});
            setListAdapter(adapter);
        }
    }
}
