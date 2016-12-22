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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.sisrest.bypsi.appsisrest.mUsuario;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sisrest.bypsi.appsisrest.Constantes.LOGIN;

/**
 * Created by Alxndr on 02/12/2016.
 */

public class Login extends Activity {

    private Button btnLogin;
    private Button btnLinkToRegister;
    private String usuario;
    private String contrasena;
    private EditText inputEmail;
    private EditText inputPassword;
    private CheckBox checkRecordar;
    private ProgressDialog pDialog;
    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    private String usu, passw;
    private boolean flagLogin=false;
    private SecurePreferences preferences;
    private boolean validarUsuario = false, validarPassword = false;

    ArrayList<HashMap<String, String>> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        checkRecordar = (CheckBox) findViewById(R.id.checkRecordar);

        preferences = new SecurePreferences(this, "user-info", "YourSecurityKey", true);
// Get
        //String flagCheck = preferences.getString("recordar");

            checkRecordar.setChecked(true);
            if(preferences.getString("usuario").length() != 0 && preferences.getString("password").length() != 0 )
            {
                validarUsuario = true;
                validarPassword = true;
            }
            inputEmail.setText(preferences.getString("usuario"));
            inputPassword.setText(preferences.getString("password"));

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        inputEmail.addTextChangedListener(new TextValidator(inputEmail) {
            @Override
            public void validate(EditText editText, String text) {
                //Implementamos la validación que queramos
                if( !text.matches("^[a-z\\d_]{4,20}$") ){
                    validarUsuario = false;
                    inputEmail.setError( "El usuario solo puede contener letras minúsculas y números y un mínimo de 4 caracteres." );
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
                    validarPassword = false;
                    inputPassword.setError( "La contraseña es muy corta" );
                }

                else  if( text.length() >10 )
                {
                    validarPassword = false;
                    inputPassword.setError( "La contraseña  puede contener máximo 10 caracteres." );
                }
                else
                    validarPassword = true;
            }

        });

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                usu = inputEmail.getText().toString().trim();
                passw = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!usu.isEmpty() && !passw.isEmpty()) {

                        if(validarUsuario && validarPassword)
                            new GetContacts().execute();

                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Por favor ingresa tus credenciales!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        Register.class);
                startActivity(i);
                finish();
            }
        });


    }


/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }*/

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


    private class GetContacts extends AsyncTask<Void, Void, Void> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Login.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = LOGIN + usu +'/'+ passw;
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray JsonUsu = jsonObj.getJSONArray("usuario");

                    if(JsonUsu.length() != 0){
                        JSONObject c = JsonUsu.getJSONObject(0);
                        Constantes.setCodigoUsuario(c.getInt("nCodUsu"));
                        Constantes.setNombreUsuario(c.getString("cNomUsu"));
                        Constantes.setDniDefault(c.getString("cDniCom"));
                        Constantes.setDniSpinner(c.getString("cDniCom"));
                        Constantes.setNombreComensalDefault(c.getString("cNomCom") + ' ' + c.getString("cApeCom"));
                        Constantes.setLoginUsu(c.getString("cLoginUsu"));
                        flagLogin = true;
                    }
                    else
                        flagLogin = false;

                    JSONArray JsonDnis = jsonObj.getJSONArray("dnis");
                    if(JsonDnis.length() != 0){
                        String [] dnis = new String[JsonDnis.length()];
                        for (int i = 0; i < JsonDnis.length(); i++) {
                            JSONObject c = JsonDnis.getJSONObject(i);
                            dnis[i] = c.getString("cDniCom");
                        }
                        Constantes.setDNIS(dnis);
                    }


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

            if(!flagLogin){
                Toast.makeText(getApplicationContext(),
                        "Usuario o contraseña incorrecta, intentelo nuevamente!", Toast.LENGTH_LONG)
                        .show();
            }
            else {
                if(checkRecordar.isChecked()){
                    Log.e(TAG, "guardar " + checkRecordar.isChecked());
                    String usuario  = inputEmail.getText().toString();
                    String passw  = inputPassword.getText().toString();
                    String checkReco  = "1";
                    // Put (all puts are automatically committed)
                    preferences.put("usuario", usuario);
                    preferences.put("password", passw);
                    preferences.put("recordar", checkReco);
                }
                else{

                    preferences.put("usuario", "");
                    preferences.put("password", "");
                    preferences.put("recordar", "0");
                }

                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
                finish();
            }

            }

           /* super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(Login.this, contactList,
                    R.layout.list_item, new String[]{ "email","mobile"},
                    new int[]{R.id.email, R.id.mobile});
            lv.setAdapter(adapter);*/

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
    private TextView lblGotoRegister;
    private Button btnLogin;
    private EditText inputEmail;
    private EditText inputPassword;
    private TextView loginErrorMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        inputEmail = (EditText) findViewById(R.id.txtEmail);
        inputPassword = (EditText) findViewById(R.id.txtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                mUsuario usuario = new mUsuario();

                usuario.setOnLoginUsuario(new OnLoginUsuario() {
                    @Override
                    public void onLoginWrong(String msg) {loginErrorMsg.setText(msg);}
                    @Override
                    public void onLoginCorrect(JSONObject json, String msg) {
                        loginErrorMsg.setText("");
                        Intent itemintent = new Intent(Login.this, MainActivity.class);
                        Login.this.startActivity(itemintent);
                    }
                });
                usuario.login(Login.this, email, password);
            }
        });

        lblGotoRegister = (TextView) findViewById(R.id.link_to_register);
        lblGotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Intent itemintent = new Intent(Login.this, Register.class);
                Login.this.startActivity(itemintent);}
        });
    }
*/
}