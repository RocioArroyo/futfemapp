package com.rocioarroyo.futfemapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.adaptadores.ClasificacionAdapter;
import com.rocioarroyo.futfemapp.adaptadores.JornadaAdapter;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.dto.PartidoDTO;
import com.rocioarroyo.futfemapp.ui.PrincipalActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JornadaFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String TAG = JornadaFragment.class.getName();

    private ArrayList<PartidoDTO> mParam1;
    private String mParam2;

    public static JornadaAdapter adaptador;
    FragmentActivity listener;

    private ListView lv;
    private Spinner spnJornada;
    private ArrayAdapter<String> comoAdapter;

    public JornadaFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClasificacionFragment.
     */
    public static JornadaFragment newInstance(ArrayList<PartidoDTO> param1, String param2) {
        JornadaFragment fragment = new JornadaFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        if (context instanceof PrincipalActivity) {
            this.listener= (FragmentActivity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jornada, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lvJornadas);
        spnJornada = view.findViewById(R.id.idSpnJornadas);
        List<String> listaJornadas = new ArrayList<>();
        String[] srtJornadas = new String[30];
        for (int i=0; i<srtJornadas.length; i++) {
            int num = i + 1;
            srtJornadas[i] = "JORNADA "+num;
        }
        Collections.addAll(listaJornadas, srtJornadas);
        comoAdapter = new ArrayAdapter<>(this.getContext(), R.layout.custom_spinner, srtJornadas);
        spnJornada.setSelection(0);
        spnJornada.setAdapter(comoAdapter);
        adaptador = new JornadaAdapter(listener, obtenerPartidosJornada(0));
        lv.setAdapter(adaptador);
        Log.i(TAG, "onViewCreated: ADAPTADOR CREADO");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spnJornada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adaptador = new JornadaAdapter(listener, obtenerPartidosJornada(position));
                lv.setAdapter(adaptador);
                Log.i(TAG, "onViewCreated: ADAPTADOR CREADO");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                adaptador = new JornadaAdapter(listener, obtenerPartidosJornada(0));
                lv.setAdapter(adaptador);
                Log.i(TAG, "onViewCreated: ADAPTADOR CREADO");
            }
        });
    }

    private ArrayList<PartidoDTO> obtenerPartidosJornada (int posicion) {
        ArrayList<PartidoDTO> listaPartipoxJornada = new ArrayList<>();
        for (PartidoDTO partidoDTO: mParam1) {
            if ((partidoDTO.getParJornada()-1)==posicion) {
                listaPartipoxJornada.add(partidoDTO);
            }
        }
        return listaPartipoxJornada;
    }

}
