package com.rocioarroyo.futfemapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.dto.PartidoDTO;
import com.rocioarroyo.futfemapp.fragments.ClasificacionFragment;
import com.rocioarroyo.futfemapp.fragments.EquipoFragment;
import com.rocioarroyo.futfemapp.fragments.JornadaFragment;
import com.rocioarroyo.futfemapp.fragments.NoticiasFragment;

import java.util.ArrayList;


public class PrincipalActivity extends AppCompatActivity {

    Context context;
    BottomNavigationView btnNavigation;
    ArrayList<EquipoDTO> listaEquipos;
    ArrayList<PartidoDTO> listaPartidos;

    public PrincipalActivity() {
    }

    public PrincipalActivity(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnNavigation = findViewById(R.id.idBottomNavigation);
        btnNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        btnNavigation.setSelectedItemId(R.id.iClasificacion);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String user_name = getIntent().getStringExtra("user_name");
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
                    openFragment(EquipoFragment.newInstance(listaEquipos, user_name));
                    return true;
                case R.id.iNoticias:
                    openFragment(NoticiasFragment.newInstance(user_name, ""));
                    return true;
            }
            return false;
        }
    };

}
