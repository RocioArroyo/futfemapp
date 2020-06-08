package com.rocioarroyo.futfemapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.ui.PrincipalActivity;


public class NoticiasFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final String TAG = NoticiasFragment.class.getName();

    private String mParam1;
    private String mParam2;

    FragmentActivity listener;

    public NoticiasFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoticiasFragment.
     */
    public static NoticiasFragment newInstance(String param1, String param2) {
        NoticiasFragment fragment = new NoticiasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * onAttach
     * @param context
     */
    @Override
    public void onAttach (Context context) {
        super.onAttach(context);
        if (context instanceof PrincipalActivity) {
            this.listener= (FragmentActivity) context;
        }
    }

    /**
     * Método apra crear el fragmentp
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     * Método para crear la vista
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noticias, container, false);
    }

    /**
     * Método que se genera tras crear la vista
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        WebView webView = view.findViewById(R.id.idNoticias);
        webView.loadUrl("https://primeraiberdrola.es/noticias/");
        webView.getSettings().setJavaScriptEnabled(true);
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Método que se lanza despues de cambiar de fragmento
     */
    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    /**
     * Método que se lanza al crear la actividad que sostiene el fragmento
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
