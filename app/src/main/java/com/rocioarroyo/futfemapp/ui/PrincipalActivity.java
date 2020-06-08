package com.rocioarroyo.futfemapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rocioarroyo.futfemapp.dialogos.CambioContrasenaDialog;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.dao.EquipoDAO;
import com.rocioarroyo.futfemapp.dialogos.DarBajaDialog;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.dto.PartidoDTO;
import com.rocioarroyo.futfemapp.fragments.ClasificacionFragment;
import com.rocioarroyo.futfemapp.fragments.EquipoFragment;
import com.rocioarroyo.futfemapp.fragments.JornadaFragment;
import com.rocioarroyo.futfemapp.fragments.NoticiasFragment;

import java.util.ArrayList;


public class PrincipalActivity extends AppCompatActivity {

    private Context context = this;
    private BottomNavigationView btnNavigation;
    private ArrayList<EquipoDTO> listaEquipos;
    private ArrayList<PartidoDTO> listaPartidos;
    private String user_name;

    /**
     * Constructor vacio
     */
    public PrincipalActivity() {
    }

    /**
     * Método para crear la actividad
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        user_name = getIntent().getStringExtra("user_name");

        btnNavigation = findViewById(R.id.idBottomNavigation);
        btnNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        btnNavigation.setSelectedItemId(R.id.iClasificacion);
    }

    /**
     * Método para crear las opciones del menu de usuario de la actividad
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    /**
     * Méodo que se ejecuta al hacer clicl en el menu contextual del menu de usuario de la actividad
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iCerrarSesion:
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.iCambiarPass:

                return true;
            case R.id.iBaja:
                return true;
            case R.id.iCambiarPass:
                FragmentManager fm = getSupportFragmentManager();
                CambioContrasenaDialog ccd = new CambioContrasenaDialog(context, user_name);
                ccd.show(fmp, "Cambio de contraseña");
                return true;
            case R.id.iBaja:
                FragmentManager fmb = getSupportFragmentManager();
                DarBajaDialog dbd = new DarBajaDialog(context, user_name, context.getString(R.string.dar_baja));
                dbd.show(fmb, "Dar Baja");
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Método que se utiliza para abrir los fragmentos de la actividad
     * @param fragment
     */
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    /**
     * Método que se ejecuta al pulsar los botones del navegador inferior
     */
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.iClasificacion:
                    listaEquipos = getIntent().getParcelableArrayListExtra("listaEquipos");
                    openFragment(ClasificacionFragment.newInstance(listaEquipos, user_name));
                    return true;
                case R.id.iJornada:
                    listaPartidos = getIntent().getParcelableArrayListExtra("listaPartidos");
                    openFragment(JornadaFragment.newInstance(listaPartidos, user_name));
                    return true;
                case R.id.iEquipos:
                    ArrayList<EquipoDTO> listaOrdenada = new EquipoDAO().reordenarLista(listaEquipos);
                    openFragment(EquipoFragment.newInstance(listaOrdenada, user_name));
                    return true;
                case R.id.iNoticias:
                    openFragment(NoticiasFragment.newInstance(user_name, ""));
                    return true;
            }
            return false;
        }
    };

    /**
     * Método que se ejecuta al pulsar el boton de retroceso del sistema operativo
     * Cierra la aplicacion si el fragmento abierto es el de al calsificacion
     */
    @Override
    public void onBackPressed() {
        int selectedItemId = btnNavigation.getSelectedItemId();
        if (R.id.iClasificacion != selectedItemId) {
            btnNavigation.setSelectedItemId(R.id.iClasificacion);
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
    }

    /**
     * getUser_name
     * @return user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * setUser_name
     * @param user_name
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
