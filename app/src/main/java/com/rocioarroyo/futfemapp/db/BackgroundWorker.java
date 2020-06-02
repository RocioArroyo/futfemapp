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
    private final String TAG= BackgroundWorker.class.getName();
    private String tipo;
    private UsuarioDAO usuarioDAO;
    private EquipoDAO equipoDAO;
    private PartidoDAO partidoDAO;
    private ArrayList<EquipoDTO> listaEquipos;

    public BackgroundWorker(Context context){
        this.context = context;
    }

    public BackgroundWorker(Context context, ArrayList<EquipoDTO> listaEquipos) {
        this.context=context;
        this.listaEquipos=listaEquipos;
    }

    @Override
    protected ArrayList doInBackground(String... strings) {
        usuarioDAO = new UsuarioDAO(context);
        equipoDAO = new EquipoDAO(context);
        partidoDAO = new PartidoDAO(context);
        Log.i(TAG, "doInBackground: ENTRADA");
        String type = strings[0];
        String login_url="http://192.168.1.149";
        if (type.equalsIgnoreCase(context.getString(R.string.type_login)) || type.equalsIgnoreCase(context.getString(R.string.type_registro))) {
            tipo = "loginregistro";
            String user_name = strings[1];
            String pass_word = strings[2];
            return usuarioDAO.mandarEmailPass(type, user_name, pass_word, login_url);
        } else if (type.equalsIgnoreCase(context.getString(R.string.type_pass))) {
            tipo = "recuperapass";
            String user_name = strings[1];
            return usuarioDAO.recibirPassword(type, user_name, login_url);
        } else if (type.equalsIgnoreCase(context.getString(R.string.type_clasificacion))) {
            tipo="clasificacion";
            return equipoDAO.recibirClasificacion(type, login_url);
        } else if (type.equalsIgnoreCase(context.getString(R.string.type_jornada))) {
            tipo = "jornada";
            return partidoDAO.recibirJornadas(type, login_url);
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
        usuarioDAO = new UsuarioDAO(context);
        equipoDAO = new EquipoDAO(context);
        Log.i(TAG, "onPostExecute: ENTRADA");
        if (tipo.equalsIgnoreCase("loginregistro")) {
            usuarioDAO.validarLoginRegistro(s);
        } else if (tipo.equalsIgnoreCase("recuperapass")) {
            usuarioDAO.validarPassword(s);
        } else if (tipo.equalsIgnoreCase("clasificacion")){
            equipoDAO.validarClasificacion(s);
        } else if (tipo.equalsIgnoreCase("jornada")) {
            partidoDAO.validarPartidos(s, listaEquipos);
        }
    }

}
