package com.rocioarroyo.futfemapp.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.rocioarroyo.futfemapp.dao.EquipoDAO;
import com.rocioarroyo.futfemapp.dao.PartidoDAO;
import com.rocioarroyo.futfemapp.dao.UsuarioDAO;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.ui.PrincipalActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BackgroundWorker extends AsyncTask <String, Integer, ArrayList> {

    private Context context;
    private String user_name;
    private final String TAG= BackgroundWorker.class.getName();
    private String tipo;
    private UsuarioDAO usuarioDAO;
    private EquipoDAO equipoDAO;
    private PartidoDAO partidoDAO;
    private ArrayList<EquipoDTO> listaEquipos;

    public BackgroundWorker(Context context, String user_name){
        this.context = context;
        this.user_name = user_name;
    }

    public BackgroundWorker(Context context, ArrayList<EquipoDTO> listaEquipos, String user_name) {
        this.context=context;
        this.listaEquipos=listaEquipos;
        this.user_name = user_name;
    }

    @Override
    protected ArrayList doInBackground(String... strings) {
        String type = strings[0];
        usuarioDAO = new UsuarioDAO(context, user_name);
        equipoDAO = new EquipoDAO(context, user_name);
        partidoDAO = new PartidoDAO(context, user_name);
        Log.i(TAG, "doInBackground: ENTRADA");
        String login_url="http://192.168.1.149";
        if (type.equalsIgnoreCase(context.getString(R.string.type_login)) || type.equalsIgnoreCase(context.getString(R.string.type_registro))) {
            tipo = "loginregistro";
            String pass_word = strings[1];
            return usuarioDAO.mandarEmailPass(type, pass_word, login_url);
        } else if (type.equalsIgnoreCase(context.getString(R.string.type_pass))) {
            tipo = "recuperapass";
            return usuarioDAO.recibirPassword(type, login_url);
        } else if (type.equalsIgnoreCase(context.getString(R.string.type_clasificacion))) {
            tipo="clasificacion";
            return equipoDAO.recibirClasificacion(type, login_url);
        } else if (type.equalsIgnoreCase(context.getString(R.string.type_jornada))) {
            tipo = "jornada";
            return partidoDAO.recibirJornadas(type, login_url);
        } else if (type.equalsIgnoreCase(context.getString(R.string.type_fav))) {
            tipo="favorito";
            String equ_id = strings[1];
            return equipoDAO.controlFavoritos(context.getString(R.string.type_fav), equ_id, login_url);
        } else if (type.equalsIgnoreCase(context.getString(R.string.type_cambiar_pass))) {
            tipo="cambiarpass";
            String nueva_pass = strings[1];
            return usuarioDAO.cambiarContrasena(context.getString(R.string.type_cambiar_pass), nueva_pass, login_url);
        }
        Log.i(TAG, "doInBackground: NO SE HAN PODIDO OBTENER LOS DATOS");
        Log.i(TAG, "doInBackground: SALIDA");
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList s) {
        usuarioDAO = new UsuarioDAO(context, user_name);
        equipoDAO = new EquipoDAO(context, user_name);
        Log.i(TAG, "onPostExecute: ENTRADA");
        if (tipo.equalsIgnoreCase("loginregistro")) {
            usuarioDAO.validarLoginRegistro(s);
        } else if (tipo.equalsIgnoreCase("recuperapass")) {
            usuarioDAO.validarPassword(s);
        } else if (tipo.equalsIgnoreCase("clasificacion")){
            equipoDAO.validarClasificacion(s);
        } else if (tipo.equalsIgnoreCase("jornada")) {
            partidoDAO.validarPartidos(s, listaEquipos);
        } else if (tipo.equalsIgnoreCase("favorito")) {
            equipoDAO.validarFavorito(s);
        }
    }

}
