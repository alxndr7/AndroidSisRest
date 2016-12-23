package com.sisrest.bypsi.appsisrest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.onClick;
import static android.content.ContentValues.TAG;
import static com.sisrest.bypsi.appsisrest.R.id.appbar;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private TableRow trConsumos, trPagos;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //appbar = (Toolbar)findViewById(R.id.appbar);
        //setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        View viewBar = (View) findViewById(R.id.fab);
        viewBar.setOnClickListener(onClick);

        //setToolbar(); // Añadir la toolbar

        // Setear adaptador al viewpager.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        // Preparar las pestañas
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        tabs.setupWithViewPager(mViewPager);

        trConsumos = (TableRow) findViewById(R.id.rowConsumos);
        trPagos = (TableRow) findViewById(R.id.rowPagos);
        trPagos.setVisibility(View.GONE);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    trPagos.setVisibility(View.GONE);
                    trConsumos.setVisibility(View.VISIBLE);
                }
                if(position == 1){
                    trPagos.setVisibility(View.VISIBLE);
                    trConsumos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navView = (NavigationView)findViewById(R.id.navview);

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;
                        Intent i;
                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                i = new Intent(getApplicationContext(),
                                        MiCuenta.class);
                                startActivity(i);
                                break;
                            case R.id.menu_seccion_2:
                                i = new Intent(getApplicationContext(),
                                        Comensales.class);
                                startActivity(i);
                                break;
                            case R.id.menu_seccion_3:
                                i = new Intent(getApplicationContext(),
                                        Defaults.class);
                                startActivity(i);
                                break;
                            case R.id.menu_opcion_1:
                                finish();
                                break;
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });
    }


    public View.OnClickListener onClick= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            showSnackBar("Actualizando...");
            // Do something in response to button click
            setupViewPager(mViewPager);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
       // MenuItem item = menu.findItem(R.id.action_shop);

        //initCustomSpinner();

        // Obtener drawable del item
       // LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Actualizar el contador
        //Utils.setBadgeCount(this, icon, 3);

        ArrayList<String> dnis = new ArrayList<String>();

        for(int i=0;i<Constantes.getDNIS().length;i++){
            Log.e(TAG, "DNIS INIT: " + Constantes.getDNIS()[i]);
            dnis.add(Constantes.getDNIS()[i]);
        }
        //Spinner spinnerCustom= (Spinner) findViewById(R.id.spinner);
        final String [] adapterValues = Constantes.getDNIS();// new String[]{Constantes.getDniDefault()};
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adapterValues);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,adapterValues);
        MenuItem item2 = menu.findItem(R.id.spinner);
        //CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(MainActivity.this,dnis);
        spinner = (Spinner) MenuItemCompat.getActionView(item2);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(Constantes.getDniDefault()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "POSITION SPINNER  : " + position);
                Constantes.setDniSpinner(adapterValues[position]);
                setupViewPager(mViewPager);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(Gravity.LEFT); //CLOSE Nav Drawer!
                }else{
                    drawerLayout.openDrawer(Gravity.LEFT); //OPEN Nav Drawer!
                }
                return true;
            //...
        }

        return super.onOptionsItemSelected(item);
       /*int id = item.getItemId();

        if (id == R.id.action_shop) {
            showSnackBar("Carrito de compras");
            return true;
        }
*/
    }

    /**
     * Muestra una {@link Snackbar} prefabricada
     *
     * @param msg Mensaje a proyectar
     */
    private void showSnackBar(String msg) {
        Snackbar.make(findViewById(R.id.fab), msg, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Establece la toolbar como action bar
     */
/*
    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }
*/
    /**
     * Crea una instancia del view pager con los datos
     * predeterminados
     *
     * @param viewPager Nueva instancia
     */
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(GridFragment.newInstance(1), getString(R.string.title_section1));
        adapter.addFragment(GridFragment.newInstance(2), getString(R.string.title_section2));
        viewPager.setAdapter(adapter);
    }

    /**
     * Método onClick() del FAB
     *
     * @param v View presionado
     */
    /*
    public void onFabClick(View v) {
        Log.e(TAG, "UPDATE  : " +"llego");


    }*/

    /**
     * Un {@link FragmentPagerAdapter} que gestiona las secciones, fragmentos y
     * títulos de las pestañas
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }


    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context,ArrayList<String> asr) {
            this.asr=asr;
            activity = context;
        }



        public int getCount()
        {
            return asr.size();
        }

        public Object getItem(int i)
        {
            return asr.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }



        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(MainActivity.this);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#ffffff"));
            return  txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(MainActivity.this);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

    }

}