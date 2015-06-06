package com.example.angel.jsonparsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;


public class vista_individual extends Activity {

    private final String KEY_N_SERIE = "n_serie";
    private final String KEY_MARCA = "marca";
    private final String KEY_MODELO = "modelo";
    private final String KEY_COLOR = "color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_individual);

        Intent in = getIntent();
        // Get JSON values from previous intent
        String serie = in.getStringExtra(KEY_N_SERIE);
        String marca = in.getStringExtra(KEY_MARCA);
        String modelo = in.getStringExtra(KEY_MODELO);
        String color = in.getStringExtra(KEY_COLOR);

        // Displaying all values on the screen
        TextView lblserie = (TextView) findViewById(R.id.n_serie);
        TextView lblmarca = (TextView) findViewById(R.id.txt_marca);
        TextView lblmodelo = (TextView) findViewById(R.id.txt_modelo);
        TextView lblcolor = (TextView) findViewById(R.id.txt_color);

        lblserie.setText(Html.fromHtml("<b>Serie:</b> " + serie));
        lblmarca.setText(Html.fromHtml("<b>Marca:</b> " + marca));
        lblmodelo.setText(Html.fromHtml("<b>Modelo:</b> " + modelo));
        lblcolor.setText(Html.fromHtml("<b>Color:</b> " + color));
        }
}
