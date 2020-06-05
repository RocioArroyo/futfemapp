package com.rocioarroyo.futfemapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;

public class CambioContrasenaDialog extends DialogFragment {

    private Datos datos;
    private Context context;
    private String user_name;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        datos = (CambioContrasenaDialog.Datos) getActivity();
    }

    public CambioContrasenaDialog() {}

    public CambioContrasenaDialog(Context context, String user_name) {
        this.context = context;
        this.user_name = user_name;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        ventana.setTitle(getString(R.string.title_cambiar_pass));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View vistaVentana = inflater.inflate(R.layout.dialog_cambio_pass, null);
        ventana.setView(vistaVentana)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextInputEditText tietNuevaPass = vistaVentana.findViewById(R.id.idNuevaPass);
                TextInputEditText tietRepNuePass = vistaVentana.findViewById(R.id.idRepNuePass);
                TextView tvError = vistaVentana.findViewById(R.id.idErrorCamPass);
                if (tietNuevaPass.getText().toString().isEmpty() || tietRepNuePass.getText().toString().isEmpty()) {
                    tvError.setVisibility(TextView.VISIBLE);
                    tvError.setText(getString(R.string.errorCampoVacio));
                } else if (tietNuevaPass.getText().toString().equalsIgnoreCase(tietRepNuePass.getText().toString())) {
                    tvError.setVisibility(TextView.VISIBLE);
                    tvError.setText(getString(R.string.errorPass));
                } else {
                    datos.datosPassword(tietNuevaPass.getText().toString());
                }
            }
        });
        return ventana.create();
    }

    public interface Datos {
        public void datosPassword(String nuevaPass);
    }
}

