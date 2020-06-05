package com.rocioarroyo.futfemapp.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.rocioarroyo.futfemapp.R;

public class InformativoDialog extends DialogFragment {

    private String mensaje;

    public InformativoDialog(){}

    public InformativoDialog(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity(), R.style.custom_dialog);
        ventana.setTitle(getString(R.string.title_cambiar_pass));
        ventana.setMessage(mensaje);
        ventana.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return ventana.create();
    }
}
