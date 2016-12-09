package com.sisrest.bypsi.appsisrest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MiCuenta extends AppCompatActivity {
    private TextView nomUsu,loginUsu, nomCom, dniCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("TAG","CONSTANTES: " + Constantes.getNombreUsuario());
        setContentView(R.layout.activity_mi_cuenta);
        nomUsu = (TextView) findViewById(R.id.texto_nombre);
        nomUsu.setText(Constantes.getNombreUsuario());
        loginUsu = (TextView) findViewById(R.id.texto_dni);
        loginUsu.setText(Constantes.getLoginUsu());

        nomCom = (TextView) findViewById(R.id.texto_nombre_comensal);
        nomCom.setText(Constantes.getNombreComensalDefault());
        dniCom = (TextView) findViewById(R.id.texto_dni_comensal);
        dniCom.setText(Constantes.getDniDefault());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
