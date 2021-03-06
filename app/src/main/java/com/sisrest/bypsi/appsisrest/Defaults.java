package com.sisrest.bypsi.appsisrest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static com.sisrest.bypsi.appsisrest.Constantes.GUARDAR_COMENSAL_DEFAULT;
import static com.sisrest.bypsi.appsisrest.Constantes.OBTENER_COMENSALES;

public class Defaults extends AppCompatActivity {

    ListView list;
    private mComensal[] jsonComensales;
    public boolean flagComensales, flagDefault;
    private ProgressDialog pDialog;
    GridAdapterDefault adapter;
    public Defaults CustomListView = null;
    public Button btnGuardar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaults);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CustomListView = this;
        list=(ListView)findViewById(R.id.listDefault);

        new Defaults.GetComensales().execute();

        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Defaults.GuardarDefault().execute();
                Log.d(TAG, "CONSTANTE EN DEFAULT: " + Constantes.getCodComUsu());
            }
        });

    }




    private class GetComensales extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Defaults.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = OBTENER_COMENSALES + Constantes.getCodigoUsuario() ;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray JsonCom = jsonObj.getJSONArray("comensales");

                    if(JsonCom.length() != 0){
                        jsonComensales = new mComensal[JsonCom.length()];
                        for (int i = 0; i < JsonCom.length(); i++) {
                            JSONObject c = JsonCom.getJSONObject(i);
                            jsonComensales[i] = new mComensal(c.getInt("nCodUsuCom"), c.getString("cNomCom"), c.getString("cApeCom"), c.getString("cDniCom"),c.getString("cTelCom"),c.getString("cDefault"));
                            //Log.e(TAG, "Parse JSON consumos :" + c.getString("cNomCom") + '/' + c.getString("cApeCom"));
/*
                        Log.e(TAG, "Parse JSON : " + nombre + '/' + dni + '/' + fecha + '/' + cantMenu );

                        Log.e(TAG, "Modelo  : " + jsonItemsConsumos[i].getNombre());

                        jsonItemsConsumos[i].setNombre(c.getString("cNomCom"));
                        jsonItemsConsumos[i].setDni(c.getString("cDniCom"));
                        jsonItemsConsumos[i].setFecha(c.getString("dFecDet"));
                        jsonItemsConsumos[i].setFecha(c.getString("nCantMenu"));*/
                        }

                        flagComensales = true;
                    }

                    else
                        flagComensales = false;


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(!flagComensales){
                Toast.makeText(getApplicationContext(),
                        "Usuario o contraseña incorrecta, intentelo nuevamente!", Toast.LENGTH_LONG)
                        .show();
            }
            else {
                /**************** Create Custom Adapter *********/
                adapter=new GridAdapterDefault(CustomListView, jsonComensales);
                list.setAdapter(adapter);
            }

        }


    }


    private class GuardarDefault extends AsyncTask<Void, Void, Void> {

        String dniDefault;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Defaults.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = GUARDAR_COMENSAL_DEFAULT + Constantes.getCodigoUsuario()  + '/' + Constantes.getCodComUsu();
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray JsonCom = jsonObj.getJSONArray("respuesta");

                    if(JsonCom.length() != 0){

                            JSONObject c = JsonCom.getJSONObject(0);
                            dniDefault = c.getString("cDniCom");
                            flagDefault = true;
                    }

                    else
                        flagDefault = false;


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(!flagDefault){
                Toast.makeText(getApplicationContext(),
                        "ERROR!", Toast.LENGTH_LONG)
                        .show();
            }
            else {
                /**************** Create Custom Adapter *********/
                Constantes.setDniDefault(dniDefault);
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
            }

        }


    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
