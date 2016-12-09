package com.sisrest.bypsi.appsisrest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.sisrest.bypsi.appsisrest.Constantes.AGREGAR_COMENSAL_USUARIO;
import static com.sisrest.bypsi.appsisrest.Constantes.BUSCAR_COMENSAL_DNI;
public class NuevoComensal extends AppCompatActivity {

    private TextView txtError,txtNombre, txtDniCom,txtTelefono;
    private LinearLayout l;
    private Button btnBuscar,btnAgregar;
    private boolean flagBuscar,flagAgregar;
    private EditText txtDni;
    private mComensal com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_comensal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnBuscar = (Button) findViewById(R.id.btnBuscarDni);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUSCAR ", "ENTRO BUSCAR" );
                new buscarComensal().execute();
            }
        });

        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new agregarComensal().execute();
            }
        });

        txtDni = (EditText) findViewById(R.id.txtDni);
        txtNombre = (TextView) findViewById(R.id.itemTxtNomCom);
        txtDniCom = (TextView) findViewById(R.id.itemTxtDniCom);
        txtTelefono = (TextView) findViewById(R.id.itemTxtTelefono);
        txtError = (TextView) findViewById(R.id.msjError);
        txtError.setVisibility(View.GONE);

        l = (LinearLayout) findViewById(R.id.cardComensal);
        l.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private class buscarComensal extends AsyncTask<Void, Void, Void> {
        String dni;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             dni = txtDni.getText().toString();
            Toast.makeText(NuevoComensal.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = BUSCAR_COMENSAL_DNI + dni;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(ContentValues.TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray JsonResp = jsonObj.getJSONArray("comensal");

                    if(JsonResp.length() != 0){
                        JSONObject c = JsonResp.getJSONObject(0);
                        //rpta.getBoolean("TRUE");
                        com = new mComensal(c.getInt("nCodCom"), c.getString("cNomCom"), c.getString("cApeCom"), c.getString("cDniCom"),c.getString("cTelCom"));
                        flagBuscar = true;
                    }

                    else{
                        flagBuscar = false;
                    }



                } catch (final JSONException e) {
                    Log.e(ContentValues.TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NuevoComensal.this,
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(ContentValues.TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NuevoComensal.this,
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(!flagBuscar){
                l.setVisibility(View.GONE);
                txtError.setVisibility(View.VISIBLE);
                Toast.makeText(NuevoComensal.this,
                        "Error al eliminar Comensal!", Toast.LENGTH_LONG)
                        .show();
            }
            else {
                /**************** Create Custom Adapter *********/
                txtNombre.setText(com.getcNomCom() + " " + com.getcApeCom());
                txtDniCom.setText(com.getcDniCom());
                txtTelefono.setText(com.getcTelCom());
                txtError.setVisibility(View.GONE);
                l.setVisibility(View.VISIBLE);
            }
        }
    }


    private class agregarComensal extends AsyncTask<Void, Void, Void> {
        String dni;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(NuevoComensal.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = AGREGAR_COMENSAL_USUARIO + Constantes.getCodigoUsuario() + "/" + com.getnCodUsuCom();
            String jsonStr = sh.makeServiceCall(url);

            Log.e(ContentValues.TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray JsonResp = jsonObj.getJSONArray("respuesta");

                    if(JsonResp.length() != 0){
                        JSONObject c = JsonResp.getJSONObject(0);
                        //rpta.getBoolean("TRUE");
                        //com = new mComensal(c.getInt("nCodCom"), c.getString("cNomCom"), c.getString("cApeCom"), c.getString("cDniCom"),c.getString("cTelCom"));
                        flagAgregar = true;
                    }

                    else{
                        flagAgregar = false;
                    }



                } catch (final JSONException e) {
                    Log.e(ContentValues.TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NuevoComensal.this,
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(ContentValues.TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(NuevoComensal.this,
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(!flagAgregar){
                Toast.makeText(NuevoComensal.this,
                        "Error al agregar Comensal!", Toast.LENGTH_LONG)
                        .show();
            }
            else {
                /**************** Create Custom Adapter *********/
                Intent refresh = new Intent(NuevoComensal.this, Comensales.class);
                startActivity(refresh);
                finish();
            }
        }
    }

}
