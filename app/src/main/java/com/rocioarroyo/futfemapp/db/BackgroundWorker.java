package com.rocioarroyo.futfemapp.db;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import androidx.fragment.app.FragmentManager;

import com.rocioarroyo.futfemapp.dao.EquipoDAO;
import com.rocioarroyo.futfemapp.dao.PartidoDAO;
import com.rocioarroyo.futfemapp.dao.UsuarioDAO;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.dialogos.CargaDialog;
import com.rocioarroyo.futfemapp.dto.EquipoDTO;
import com.rocioarroyo.futfemapp.excepcion.FutFemAppException;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class BackgroundWorker extends AsyncTask <String, Integer, ArrayList> {

    private Context context;
    private String user_name;
    private final String TAG= BackgroundWorker.class.getName();
    private String tipo;
    private UsuarioDAO usuarioDAO;
    private EquipoDAO equipoDAO;
    private PartidoDAO partidoDAO;
    private ArrayList<EquipoDTO> listaEquipos;
    private FragmentManager fm;
    private CargaDialog ccd;

    /**
     * Contructor basico
     * @param context
     * @param user_name
     */
    public BackgroundWorker(Context context, String user_name){
        this.context = context;
        this.user_name = user_name;
    }

    /**
     * Constructor para las jornadas
     * @param context
     * @param listaEquipos
     * @param user_name
     */
    public BackgroundWorker(Context context, ArrayList<EquipoDTO> listaEquipos, String user_name) {
        this.context=context;
        this.listaEquipos=listaEquipos;
        this.user_name = user_name;
    }

    /**
     * Constructor para la muestra de dialogs
     * @param context
     * @param user_name
     * @param fm
     */
    public BackgroundWorker(Context context, String user_name, FragmentManager fm) {
        this.context=context;
        this.user_name=user_name;
        this.fm = fm;
        ccd=new CargaDialog(context, user_name);
    }

    /**
     * Método para generar los hilos de conexión con el servidor
     * @param strings
     * @return
     */
    @Override
    protected ArrayList doInBackground(String... strings) {
        String type = strings[0];
        usuarioDAO = new UsuarioDAO(context, user_name);
        equipoDAO = new EquipoDAO(context, user_name);
        partidoDAO = new PartidoDAO(context, user_name);
        Log.i(TAG, "doInBackground: ENTRADA");
        String login_url="http://192.168.1.149";
        try {
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
            } else if (type.equalsIgnoreCase(context.getString(R.string.type_baja))){
                tipo="baja";
                return usuarioDAO.darBaja(context.getString(R.string.type_baja), login_url);
            } else {
                throw new FutFemAppException("No se ha recoocido el tipo de llamada");
            }
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: ERROR: ", e);
        } catch (FutFemAppException e) {
            Log.e(TAG, "doInBackground: ERROR: ", e);
        } catch (JSONException e) {
            Log.e(TAG, "doInBackground: ERROR: ", e);
        }
        Log.i(TAG, "doInBackground: NO SE HAN PODIDO OBTENER LOS DATOS");
        Log.i(TAG, "doInBackground: SALIDA");
        return null;
    }

    /**
     * Método de acciones antesde lanzar el hilo
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (ccd!=null) {
            ccd.show(fm, "Carga datos");
        }
    }

    /**
     * Método de acciones despues de lanzar el hilo
     * @param s
     */
    @Override
    protected void onPostExecute(ArrayList s) {
        usuarioDAO = new UsuarioDAO(context, user_name);
        equipoDAO = new EquipoDAO(context, user_name);
        Log.i(TAG, "onPostExecute: ENTRADA");
        try {
            if (tipo.equalsIgnoreCase("loginregistro")) {
                Log.i(TAG, "onPostExecute: Valida login o registro");
                usuarioDAO.validarLoginRegistro(s, ccd);
            } else if (tipo.equalsIgnoreCase("recuperapass")) {
                Log.i(TAG, "onPostExecute: valida recuperacin de contraseña");
                usuarioDAO.validarPassword(s);
                if (ccd!=null) {
                    ccd.dismiss();
                }
            } else if (tipo.equalsIgnoreCase("clasificacion")){
                Log.i(TAG, "onPostExecute: Valida recuperacion de clasificacion");
                equipoDAO.validarClasificacion(s, ccd);
            } else if (tipo.equalsIgnoreCase("jornada")) {
                Log.i(TAG, "onPostExecute: valida recuperacion de todos los partidos");
                partidoDAO.validarPartidos(s, listaEquipos);
                if (ccd!=null) {
                    ccd.dismiss();
                }
            } else if (tipo.equalsIgnoreCase("favorito")) {
                Log.i(TAG, "onPostExecute: valida modificacion de favoritos va correctamente");
                equipoDAO.validarFavorito(s);
                if (ccd!=null) {
                    ccd.dismiss();
                }
            } else if (tipo.equalsIgnoreCase("cambiarpass")) {
                Log.i(TAG, "onPostExecute: valida el cambio de la contraseña");
                usuarioDAO.validarCambioContrasena(s);
                if (ccd!=null) {
                    ccd.dismiss();
                }
            } else if (tipo.equalsIgnoreCase("baja")) {
                Log.i(TAG, "onPostExecute: valida la baja de un usuario");
                usuarioDAO.validaBaja(s);
                if(ccd!=null) {
                    ccd.dismiss();
                }
            }
            else {
                throw new FutFemAppException("ERROR AL RECUPERAR DATOS DEL SERVIDOR");
            }
        } catch (FutFemAppException e) {
            Log.e(TAG, "onPostExecute: ERROR: ", e);
        }
        Log.i(TAG, "onPostExecute: SALIDA");
    }

}
