package com.rocioarroyo.futfemapp.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.rocioarroyo.futfemapp.R;
import com.rocioarroyo.futfemapp.db.BackgroundWorker;
import com.rocioarroyo.futfemapp.ui.LoginActivity;

public class DarBajaDialog extends DialogFragment {

    private Context context;
    private String user_name;
    String mensaje;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public DarBajaDialog() {}

    public DarBajaDialog(Context context, String user_name, String mensaje) {
        this.context = context;
        this.user_name = user_name;
        this.mensaje=mensaje;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity(), R.style.custom_dialog);
        ventana.setTitle(getString(R.string.title_baja));
        ventana.setMessage(mensaje);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View vistaVentana = inflater.inflate(R.layout.dialog_informativo, null);
        ventana.setView(vistaVentana)
                .setNegativeButton(context.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton(context.getString(R.string.si), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BackgroundWorker backgroundWorker = new BackgroundWorker(context, user_name);
                backgroundWorker.execute(context.getString(R.string.type_baja));
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                dialog.cancel();
            }
        });
        return ventana.create();
    }
}
