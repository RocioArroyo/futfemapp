package com.rocioarroyo.futfemapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rocioarroyo.futfemapp.CambioContrasenaDialog;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.dao.EquipoDAO;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.dto.PartidoDTO;
import com.rocioarroyo.futfemapp.fragments.ClasificacionFragment;
import com.rocioarroyo.futfemapp.fragments.EquipoFragment;
import com.rocioarroyo.futfemapp.fragments.JornadaFragment;
import com.rocioarroyo.futfemapp.fragments.NoticiasFragment;

import java.util.ArrayList;


public class PrincipalActivity extends AppCompatActivity implements CambioContrasenaDialog.Datos {

    private Context context = this;
    private BottomNavigationView btnNavigation;
    private ArrayList<EquipoDTO> listaEquipos;
    private ArrayList<PartidoDTO> listaPartidos;
    private String user_name;

    public PrincipalActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        user_name = getIntent().getStringExtra("user_name");

        btnNavigation = findViewById(R.id.idBottomNavigation);
        btnNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        btnNavigation.setSelectedItemId(R.id.iClasificacion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

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
                ccd.show(fm, "Cambio de contraseña");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

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

    @Override
    public void datosPassword(String nuevaPass) {
        BackgroundWorker backgroundWorker = new BackgroundWorker(context, user_name);
        backgroundWorker.execute(getString(R.string.type_cambiar_pass), nuevaPass);
    }

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
}
