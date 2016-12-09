package com.sisrest.bypsi.appsisrest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.sisrest.bypsi.appsisrest.Constantes.DELETE_COMENSAL;

/**
 * {@link BaseAdapter} personalizado para el gridview
 */
public class GridAdapterComensales extends BaseAdapter {

    private final Context mContext;
    private final mComensal[] items;
    private Activity activity;
    private mComensal item;
    private int cod;
    private boolean flagEliminar;

    public GridAdapterComensales(Context c, mComensal[] items) {
        //Log.d(TAG,"ITEMS : " +  items.length);
        mContext = c;
        this.items = items;
    }

    @Override
    public int getCount() {
        // Decremento en 1, para no contar el header view

        return items.length;
    }

    @Override
    public mComensal getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        Log.d(TAG,"ITEMS :  LLEGO" );

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.tabitem, null, false);

        }
        item = getItem(position);

        // Seteando Imagen
       // ImageView image = (ImageView) view.findViewById(R.id.imagen);
        //Glide.with(image.getContext()).load(item.getIdThumbnail()).into(image);

        // Seteando Nombre
        TextView name = (TextView) view.findViewById(R.id.itemTxtNomCom);
        name.setText(item.getcNomCom() + " " + item.getcApeCom());

        // Seteando Descripción
        TextView descripcion = (TextView) view.findViewById(R.id.itemTxtDniCom);
        descripcion.setText(item.getcDniCom());

        Button btn=(Button)view.findViewById(R.id.btnEliminar);
        Button.OnClickListener mOkOnClickListener = new Button.OnClickListener()
        {
            public void onClick(View v) {
                Log.v("ttttttt", ""+ items[position].getnCodUsuCom() );

                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("ALERTA !!");
                alert.setMessage("Esta seguro de eliminar este comensal? Ya no podrá ver información relacionada.");
                alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        cod = items[position].getnCodUsuCom();
                        new eliminarComensal().execute();
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();
                //Toast.makeText(Context,""+item.getcApeCom(), Toast.LENGTH_LONG).show();
            }
        };
        btn.setOnClickListener(mOkOnClickListener);

        // Button button = (Button) view.findViewById(R.id.btnEliminar);
        //button.setOnClickListener(new view.OnClickListener(){});

        // Seteando Rating
       // RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating);
        //ratingBar.setRating(item.getCantidad());

        return view;
    }

    private class eliminarComensal extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity = (Activity) mContext;
            Toast.makeText(mContext,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = DELETE_COMENSAL + cod + "/" + Constantes.getCodigoUsuario() ;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(ContentValues.TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray JsonResp = jsonObj.getJSONArray("respuesta");

                    if(JsonResp.length() != 0){
                        JSONObject rpta = JsonResp.getJSONObject(0);
                        //rpta.getBoolean("TRUE");
                        flagEliminar = true;
                    }

                    else
                        flagEliminar = false;


                } catch (final JSONException e) {
                    Log.e(ContentValues.TAG, "Json parsing error: " + e.getMessage());
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext,
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(ContentValues.TAG, "Couldn't get json from server.");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext,
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(!flagEliminar){
                Toast.makeText(mContext,
                        "Error al eliminar Comensal!", Toast.LENGTH_LONG)
                        .show();
            }
            else {
                /**************** Create Custom Adapter *********/
                Intent refresh = new Intent(mContext, Comensales.class);
                activity.startActivity(refresh);
                activity.finish();
            }

        }

           /* super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(Login.this, contactList,
                    R.layout.list_item, new String[]{ "email","mobile"},
                    new int[]{R.id.email, R.id.mobile});
            lv.setAdapter(adapter);*/

    }



}
