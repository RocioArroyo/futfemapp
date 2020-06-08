package com.rocioarroyo.futfemapp.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.DialogFragment;

import com.rocioarroyo.futfemapp.R;

public class CargaDialog extends DialogFragment {

    private Context context;
    private String user_name;

    /**
     * Constructor vacio
     */
    public CargaDialog(){}

    /**
     * Constructor con las constantes de la aplicacion
     * @param context
     * @param user_name
     */
    public CargaDialog(Context context, String user_name) {
        this.context=context;
        this.user_name=user_name;
    }

    /**
     * Método que se ejecuta al crear el dialogo
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity(), R.style.custom_dialog);
        ventana.setTitle(getString(R.string.title_carga));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View vistaVentana = inflater.inflate(R.layout.dialog_carga, null);
        ventana.setView(vistaVentana);
        return ventana.create();
    }

    /**
     * Método que se ejecuta al cerrar una aplicacion
     * @param dialog
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        View view = getActivity().getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        dialog.dismiss();
    }

}
