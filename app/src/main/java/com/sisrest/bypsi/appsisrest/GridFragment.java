package com.sisrest.bypsi.appsisrest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

import static android.content.ContentValues.TAG;

/**
 * Un fragmento que contiene una grilla de productos
 */
public class GridFragment extends Fragment {

    private Product[] jsonItemsConsumos;
    private Pago[] jsonItemsPagos;

    /**
     * Argumento que representa el número sección al que pertenece
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Creación prefabricada de un {@link GridFragment}
     */
    public static GridFragment newInstance(int sectionNumber) {
        GridFragment fragment = new GridFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public GridFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Obtención del grid view

        AsyncTask pagosTask = new GetPagosConsumos().execute();

        Log.e(TAG, "ESTADO  : " + pagosTask.getStatus());
/*
        // Inicializar el grid view
        if ( pagosTask != null && pagosTask.getStatus() == AsyncTask.Status.FINISHED) {
            //START YOUR NEW TASK HERE
            Log.e(TAG, "Antes de mandar a Grid : " + jsonItemsConsumos[0].getNombre());

            return rootView;
        }
        else*/
            return rootView;

    }


    /**
     * Infla el grid view del fragmento dependiendo de la sección
     *
     * @param grid Instancia del grid view
     */
    private void setUpGridView(GridViewWithHeaderAndFooter grid) {

        int section_number = getArguments().getInt(ARG_SECTION_NUMBER);
        switch (section_number) {
            case 1:
               // grid.addHeaderView(createHeaderView(6, Products.getTelefonos()));
                //grid.setAdapter(new GridAdapter(getActivity(), Products.getTelefonos()));
               // Log.e(TAG, "Antes de mandar a Grid : " + jsonItemsConsumos);
                grid.setNumColumns(1);
                grid.setAdapter(new GridAdapter(getActivity(), jsonItemsConsumos));
                break;
            case 2:
                //grid.addHeaderView(createHeaderView(5, Products.getTablets()));
                //grid.setAdapter(new GridAdapter(getActivity(), Products.getTablets()));
               // grid.setAdapter(new GridAdapter(getActivity(), Products.getTablets()));
               // new GetPagosConsumos().execute();
                //Log.e(TAG, "Antes de mandar a Grid : " + jsonItemsConsumos);
                grid.setNumColumns(1);
                grid.setAdapter(new GridAdapterPago(getActivity(), jsonItemsPagos));
                break;
            case 3:
                grid.addHeaderView(createHeaderView(6, Products.getTelefonos()));
                grid.setAdapter(new GridAdapter(getActivity(), Products.getTelefonos()));
                break;
            case 4:
                //grid.addHeaderView(createHeaderView(6, Products.getPortatiles()));
                //grid.setAdapter(new GridAdapter(getActivity(), Products.getPortatiles()));
                break;
        }
    }

    /**
     * Crea un view de cabecera para mostrarlo en el principio del grid view.
     *
     * @param position Posición del item que sera el grid view dentro de {@code items}
     * @param items    Array de productos
     * @return Header View
     */
    private View createHeaderView(int position, Product[] items) {

        View view;

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.grid_header, null, false);

        Product item = items[position];

        // Seteando Imagen
        //ImageView image = (ImageView) view.findViewById(R.id.imagen);
        //Glide.with(image.getContext()).load(item.getIdThumbnail()).into(image);

        // Seteando Nombre
        TextView name = (TextView) view.findViewById(R.id.nombre);
        name.setText(item.getNombre());

        // Seteando Descripción
        TextView descripcion = (TextView) view.findViewById(R.id.descripcion);
        descripcion.setText(item.getDni());

        // Seteando Precio
        TextView precio = (TextView) view.findViewById(R.id.precio);
        precio.setText(item.getFecha());

        TextView cantidad = (TextView) view.findViewById(R.id.cantidad);
        precio.setText(item.getCantidad());


        // Seteando Rating
        //RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating);
        //ratingBar.setRating(item.getRating());

        return view;
    }



    private class GetPagosConsumos extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(this.MainActivity,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://192.168.0.102:8080/tesis0.0/public/index.php/wsultimosConsumos/85265475";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray JsonUsu = jsonObj.getJSONArray("ultimosconsumos");
                    jsonItemsConsumos = new Product[JsonUsu.length()];
                    // looping through All Contacts
                    for (int i = 0; i < JsonUsu.length(); i++) {
                        JSONObject c = JsonUsu.getJSONObject(i);
                        String nombre = c.getString("cNomCom");
                        String dni = c.getString("cDniCom");
                        String fecha = c.getString("dFecDet");
                        String cantMenu = c.getString("nCantMenu");
                        jsonItemsConsumos[i] = new Product(nombre,dni,fecha,cantMenu);
                        Log.e(TAG, "Parse JSON consumos : " + nombre + '/' + dni + '/' + fecha + '/' + cantMenu );
/*
                        Log.e(TAG, "Parse JSON : " + nombre + '/' + dni + '/' + fecha + '/' + cantMenu );

                        Log.e(TAG, "Modelo  : " + jsonItemsConsumos[i].getNombre());

                        jsonItemsConsumos[i].setNombre(c.getString("cNomCom"));
                        jsonItemsConsumos[i].setDni(c.getString("cDniCom"));
                        jsonItemsConsumos[i].setFecha(c.getString("dFecDet"));
                        jsonItemsConsumos[i].setFecha(c.getString("nCantMenu"));*/
                    }

                    // Getting JSON Array node
                    JSONArray JsonPagos = jsonObj.getJSONArray("ultimosPagos");
                    jsonItemsPagos = new Pago[JsonPagos.length()];
                    // looping through All Contacts
                    for (int i = 0; i < JsonPagos.length(); i++) {
                        JSONObject c = JsonPagos.getJSONObject(i);
                        String nombre = c.getString("cNomCom");
                        String dni = c.getString("cDniCom");
                        String pago = c.getString("fPagoCom");
                        String fecha = c.getString("dFecPago");
                        String cantMenu = c.getString("nNumMenuCom");
                        String cantDisponible = c.getString("nCantMenu");

                        Log.e(TAG, "Parse JSON pagos : " + nombre + '/' + dni + '/' + fecha + '/' + cantMenu +'/' +pago +'/' + cantMenu + '/' +cantDisponible);

                        jsonItemsPagos[i] = new Pago(nombre,dni,pago,fecha,cantDisponible,cantMenu);

                        /*
                        jsonItemsConsumos[i].setNombre(c.getString("cNomCom"));
                        jsonItemsConsumos[i].setDni(c.getString("cDniCom"));
                        jsonItemsConsumos[i].setFecha(c.getString("dFecDet"));
                        jsonItemsConsumos[i].setFecha(c.getString("nCantMenu"));*/
                    }





                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
*/
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
               /*  runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });*/
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            //View rootView = inflater.inflate(R.layout.fragment_main);

            //ListView mListView = (ListView) findViewById(android.R.id.list);

            Log.e(TAG, "Nombre item pago : " + jsonItemsPagos[0].getNombre());

            GridViewWithHeaderAndFooter grid = (GridViewWithHeaderAndFooter)getView().findViewById(R.id.gridview);
            setUpGridView(grid);
           /* super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(Login.this, contactList,
                    R.layout.list_item, new String[]{ "email","mobile"},
                    new int[]{R.id.email, R.id.mobile});
            lv.setAdapter(adapter);*/
        }
    }


}
