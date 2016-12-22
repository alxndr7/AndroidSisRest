package com.sisrest.bypsi.appsisrest;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * {@link BaseAdapter} personalizado para el gridview
 */
public class GridAdapterPago extends BaseAdapter {

    private final Context mContext;
    private final Pago[] items;

    public GridAdapterPago(Context c, Pago[] items) {
        mContext = c;
        this.items = items;
    }

    @Override
    public int getCount() {
        // Decremento en 1, para no contar el header view
        return items.length;
    }

    @Override
    public Pago getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.grid_item_pago, null, false);
        }

        Pago item = getItem(position);

        // Seteando Imagen
       // ImageView image = (ImageView) view.findViewById(R.id.imagen);
        //Glide.with(image.getContext()).load(item.getIdThumbnail()).into(image);

        // Seteando Nombre
        TextView name = (TextView) view.findViewById(R.id.nomPago);
        name.setText(item.getNombre());

        // Seteando Descripción
        //TextView descripcion = (TextView) view.findViewById(R.id.dniPago);
        //descripcion.setText(item.getDni());

        // Seteando Precio
        TextView fechaPago = (TextView) view.findViewById(R.id.fechaPago);
        fechaPago.setText(item.getFecha());

        TextView pago = (TextView) view.findViewById(R.id.pagoCom);
        pago.setText(item.getPago());

        TextView cantidadPago = (TextView) view.findViewById(R.id.cantPago);
        cantidadPago.setText(item.getCantidadPago());

        TextView cantidadTotal = (TextView) view.findViewById(R.id.cantDispo);
        cantidadTotal.setText(item.getCantidadDisponible());
        // Seteando Rating
       // RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating);
        //ratingBar.setRating(item.getCantidad());

        return view;
    }
}
