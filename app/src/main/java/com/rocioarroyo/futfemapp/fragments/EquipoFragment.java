package com.rocioarroyo.futfemapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.adaptadores.ClasificacionAdapter;
import com.rocioarroyo.futfemapp.adaptadores.EquiposAdapter;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.ui.PrincipalActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EquipoFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String TAG = ClasificacionFragment.class.getName();

    private ArrayList<EquipoDTO> mParam1;
    private String mParam2;

    public static EquiposAdapter adaptador;
    FragmentActivity listener;
    private ListView lv;

    public EquipoFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EquipoFragment.
     */
    public static EquipoFragment newInstance(ArrayList<EquipoDTO> param1, String param2) {
        EquipoFragment fragment = new EquipoFragment();
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
        return inflater.inflate(R.layout.fragment_equipos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lvEquipos);
        adaptador = new EquiposAdapter(listener, mParam1, mParam2);
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
    }

}
