package com.sisrest.bypsi.appsisrest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sisrest.bypsi.appsisrest.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.sisrest.bypsi.appsisrest.Constantes.INSERT_USUARIO;
import static com.sisrest.bypsi.appsisrest.Constantes.LOGIN;

/**
 * Created by Alxndr on 02/12/2016.
 */

public class Register extends Activity {

    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputNombre;
    private EditText inputLogin;
    private EditText inputPassword;
    private EditText dniComensal;
    private ProgressDialog pDialog;
    private boolean fCrearUsuario;
    private String nombre, usu, passw,dni;
    private boolean validarNombre = false, validarUsuario = false, validaPassw = false, validarDni = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        inputNombre = (EditText) findViewById(R.id.cNomUsu);
        inputLogin = (EditText) findViewById(R.id.cLoginUsu);
        inputPassword = (EditText) findViewById(R.id.cPasswUsu);
        dniComensal = (EditText) findViewById(R.id.dniCom);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        inputNombre.addTextChangedListener(new TextValidator(inputNombre) {
            @Override
            public void validate(EditText editText, String text) {
                //Implementamos la validación que queramos
                if( !text.matches("^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s_]{4,20}$") ){
                    validarNombre = false;
                    inputNombre.setError( "Solo puedes usar letras." );
                }

                else
                    validarNombre=true;

            }

        });

        inputLogin.addTextChangedListener(new TextValidator(inputLogin) {
            @Override
            public void validate(EditText editText, String text) {
                //Implementamos la validación que queramos
                if( !text.matches("^[a-z\\d_]{4,20}$") ){
                    validarUsuario = false;
                    inputLogin.setError( "El usuario solo puede contener letras minúsculas y números y un mínimo de 4 caracteres." );
                }

                else
                    validarUsuario=true;

            }

        });

        inputPassword.addTextChangedListener(new TextValidator(inputPassword) {
            @Override
            public void validate(EditText editText, String text) {
                //Implementamos la validación que queramos
                if( text.length() < 4 ){
                    validaPassw = false;
                    inputPassword.setError( "La contraseña es muy corta" );
                }

                else  if( text.length() >10 )
                {
                    validaPassw = false;
                    inputPassword.setError( "La contraseña  puede contener máximo 10 caracteres." );
                }
                else
                    validaPassw = true;

            }

        });

        dniComensal.addTextChangedListener(new TextValidator(dniComensal) {
            @Override
            public void validate(EditText editText, String text) {
                //Implementamos la validación que queramos
                if( !text.matches("^[\\d_]{8}$") ){
                    validarDni = false;
                    dniComensal.setError( "Error." );
                }

                else
                    validarDni=true;

            }

        });

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Login.class);
                startActivity(i);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                AsyncTask pagosTask = new nuevoUsuarioAndroid().execute();
            }
        });

    }


    public abstract class TextValidator implements TextWatcher {
        private final EditText editText;

        public TextValidator(EditText editText) {
            this.editText = editText;
        }

        public abstract void validate(EditText editText, String text);

        @Override
        final public void afterTextChanged(Editable s) {
            String text = editText.getText().toString();
            validate(editText, text);
        }

        @Override
        final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

        @Override
        final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
    }

    private class nuevoUsuarioAndroid extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {

            nombre = inputNombre.getText().toString().replace(" ", "%20");
            usu = inputLogin.getText().toString();
            passw = inputPassword.getText().toString();
            dni = dniComensal.getText().toString();

            super.onPreExecute();
            Toast.makeText(Register.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response

            String url = INSERT_USUARIO + nombre +'/'+ usu + '/' + passw + '/' + dni;

            Log.e(TAG, "URL : " + url);

            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray JsonUsu = jsonObj.getJSONArray("respuesta");

                    if(JsonUsu.length() != 0){/*
                        JSONObject c = JsonUsu.getJSONObject(0);
                        Constantes.setCodigoUsuario(c.getInt("nCodUsu"));
                        Constantes.setNombreUsuario(c.getString("cNomUsu"));
                        Constantes.setDniDefault(c.getString("cDniCom"));*/
                        fCrearUsuario = true;
                    }

                    else
                        fCrearUsuario = false;


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

            if(!fCrearUsuario){
                Toast.makeText(getApplicationContext(),
                        "Ocurrio un error!", Toast.LENGTH_LONG)
                        .show();
            }
            else {
                Intent i = new Intent(getApplicationContext(),
                        Login.class);
                startActivity(i);
                finish();

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


/*
    private TextView lblGotoLogin;
    private Button btnRegister;
    private EditText inputFullName;
    private EditText inputEmail;
    private EditText inputPassword;
    private TextView registerErrorMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        inputFullName = (EditText) findViewById(R.id.txtUserName);
        inputEmail = (EditText) findViewById(R.id.txtEmail);
        inputPassword = (EditText) findViewById(R.id.txtPass);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        registerErrorMsg = (TextView) findViewById(R.id.register_error);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                Usuario usuario = new Usuario();

                usuario.setOnRegisterUsuario(new OnRegisterUsuario() {
                    @Override
                    public void onRegisterFinish(JSONObject json, String msg) {
                        registerErrorMsg.setText("");
                        Intent itemintent = new Intent(Login.this, ActivityPrincipal.class);
                        Register.this.startActivity(itemintent);>
                    }
                    @Override
                    public void onRegisterFail(String msg) {registerErrorMsg.setText(msg);}
                    @Override
                    public void onRegisterException(Exception e, String msg) {registerErrorMsg.setText(msg);}
                });
                usuario.register(Register.this, name, email, password);
            }
        });

        lblGotoLogin = (TextView) findViewById(R.id.link_to_login);
        lblGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Intent itemintent = new Intent(Register.this, Login.class);
                Register.this.startActivity(itemintent);}
        });

    }
*/
}