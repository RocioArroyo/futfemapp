package com.rocioarroyo.futfemapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.fragments.ClasificacionFragment;

import java.util.ArrayList;


public class PrincipalActivity extends AppCompatActivity {

    Context context;
    BottomNavigationView btnNavigation;
    ArrayList<EquipoDTO> listaEquipos;

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

        listaEquipos = getIntent().getParcelableArrayListExtra("listaEquipos");


        btnNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(ClasificacionFragment.newInstance(listaEquipos, ""));
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.iClasificacion:
                        listaEquipos = getIntent().getParcelableArrayListExtra("listaEquipos");
                        openFragment(ClasificacionFragment.newInstance(listaEquipos, ""));
                        return true;
                    case R.id.iJornada:
                        //openFragment(SmsFragment.newInstance("", ""));
                        return true;
                    case R.id.iEquipos:
                        //openFragment(NotificationFragment.newInstance("", ""));
                        return true;
                    case R.id.iNoticias:
                        //openFragment(NotificationFragment.newInstance("", ""));
                        return true;
                }
                return false;
            }
        };

}
