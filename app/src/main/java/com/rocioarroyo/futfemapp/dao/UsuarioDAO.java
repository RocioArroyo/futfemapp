package com.rocioarroyo.futfemapp.dao;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;
import com.rocioarroyo.futfemapp.dto.UsuarioDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UsuarioDAO {

    private final String TAG = UsuarioDAO.class.getName();
    private Context context;
    private BackgroundWorker backgroundWorker;

    public UsuarioDAO() {}

    public UsuarioDAO (Context context) {
        this.context = context;
    }

    public void validarLoginRegistro(String resultado) {
        TextView txtError = ((Activity)context).findViewById(R.id.idErrorUsPass);
        if (resultado == null) {
            Log.i(TAG, "validarLoginRegistro: No se ha podido conectar con la base de datos");
            txtError.setText(context.getString(R.string.errorConectionDB));
            txtError.setVisibility(TextView.VISIBLE);
        } else {
            Log.i(TAG, "validarLoginRegistro: Se ha conectado con la base de datos");
            if (resultado.equalsIgnoreCase(context.getString(R.string.login_ok)) || resultado.equalsIgnoreCase(context.getString(R.string.registro_ok))) {
                Log.i(TAG, "validarLoginRegistro: abrimos la pantalla de inicio ya que el login es correcto");
                txtError.setVisibility(TextView.INVISIBLE);
                backgroundWorker = new BackgroundWorker(context);
                backgroundWorker.execute(context.getString(R.string.type_clasificacion));
            } else if (resultado.equalsIgnoreCase(context.getString(R.string.login_fail))) {
                Log.i(TAG, "validarLoginRegistro: inicio de sesion incorrecto");
                txtError.setText(context.getString(R.string.errorUsPass));
                txtError.setVisibility(TextView.VISIBLE);
            } else if (resultado.equalsIgnoreCase(context.getString(R.string.registro_fail))) {
                Log.i(TAG, "validarLoginRegistro: registro incorrecto");
                txtError.setText(context.getString(R.string.errorUsExistente));
                txtError.setVisibility(TextView.VISIBLE);
            }
        }
    }

    public void validarPassword(String s) {
        TextView txtError = ((Activity)context).findViewById(R.id.idErrorUsPass);
        Log.i(TAG, "validarPassword: leemos JSON");
        JSONArray jsonArray = null;
        try {
            if (!s.equalsIgnoreCase(context.getString(R.string.email_fail))) {
                jsonArray = new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                usuarioDTO.setUsrEmail(jsonObject.getString("usr_email"));
                usuarioDTO.setUsrPassword(jsonObject.getString("usr_password"));
                txtError.setText(context.getString(R.string.title_password) + ": " + usuarioDTO.getUsrPassword());
                txtError.setTextColor(Color.parseColor("#F8F40D"));
                txtError.setVisibility(TextView.VISIBLE);
            } else {
                txtError.setText(context.getText(R.string.errorEmail));
                txtError.setTextColor(Color.parseColor("#FC3232"));
                txtError.setVisibility(TextView.VISIBLE);
            }
        } catch (JSONException e) {
            Log.e(TAG, "validarPassword: Error al validar la recuperacion de a contraseña", e);
        }
    }

}
