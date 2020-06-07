package com.rocioarroyo.futfemapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.adaptadores.ClasificacionAdapter;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.ui.PrincipalActivity;

import java.util.ArrayList;

public class ClasificacionFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String TAG = ClasificacionFragment.class.getName();

    private ArrayList<EquipoDTO> mParam1;
    private String mParam2;

    public static ClasificacionAdapter adaptador;
    FragmentActivity listener;
    private ListView lv;
    private TextView tvPosicion, tvNombre, tvPuntos, tvParJug, tvParGan, tvParEmp, tvParPer, tvGolFav, tvGolCont, tvGolesDif;

    public ClasificacionFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClasificacionFragment.
     */
    public static ClasificacionFragment newInstance(ArrayList<EquipoDTO> param1, String param2) {
        ClasificacionFragment fragment = new ClasificacionFragment();
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
        return inflater.inflate(R.layout.fragment_clasificacion, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lvClasificacion);
        tvPosicion = ((Activity)listener).findViewById(R.id.tvPosicionT);
        tvNombre = ((Activity)listener).findViewById(R.id.tvNombreT);
        tvPuntos = ((Activity)listener).findViewById(R.id.tvPuntosT);
        tvParJug = ((Activity)listener).findViewById(R.id.tvPartidosJugadosT);
        tvParEmp = ((Activity)listener).findViewById(R.id.tvPartidosEmpatadosT);
        tvParGan = ((Activity)listener).findViewById(R.id.tvPartidosGanadosT);
        tvParPer = ((Activity)listener).findViewById(R.id.tvPartidosPerdidosT);
        tvGolFav = ((Activity)listener).findViewById(R.id.tvGolesFavorT);
        tvGolCont = ((Activity)listener).findViewById(R.id.tvGolesContraT);
        tvGolesDif = ((Activity)listener).findViewById(R.id.tvGolesDiferenciaT);
        InputMethodManager inputMethodManager = (InputMethodManager) listener.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(tvPosicion.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(tvNombre.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(tvParJug.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(tvParGan.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(tvParEmp.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(tvParPer.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(tvPuntos.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(tvGolFav.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(tvGolCont.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(tvGolesDif.getWindowToken(), 0);
        adaptador = new ClasificacionAdapter(listener, mParam1, mParam2);
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
