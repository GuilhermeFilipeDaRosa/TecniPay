package com.tecnicon.tecnipay.service.getnet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.getnet.posdigital.PosDigital;

public class GetnetService {
    public static void connectPosDigitalService(Context contexto) {
        ProgressDialog progressDialog = ProgressDialog.show(contexto, "Processando", "Por favor, aguarde...", true);
        PosDigital.BindCallback bindCallback = new PosDigital.BindCallback() {
            @Override
            public void onError(Exception e) {
                Log.i("Getnet", "onError");
                progressDialog.dismiss();
                new AlertDialog.Builder(contexto)
                        .setTitle("Falha")
                        .setMessage(e.getMessage())
                        //.setPositiveButton("Sim", null)
                        //.setNegativeButton("Não", null)
                        .show();
            }

            @Override
            public void onConnected() {
                Log.i("Getnet", "onConnected");
                progressDialog.dismiss();
            }

            @Override
            public void onDisconnected() {
                Log.i("Getnet", "onDisconnected");
                progressDialog.dismiss();
                new AlertDialog.Builder(contexto)
                        .setTitle("Desconectado")
                        .setMessage("Deseja tentar conectar novamente?")
                        //.setPositiveButton("Sim", null)
                        //.setNegativeButton("Não", null)
                        .show();
            }
        };

        if (PosDigital.getInstance().isInitiated()) {
            PosDigital.unregister(contexto);//teste
        }

        PosDigital.register(contexto, bindCallback);

        if (PosDigital.getInstance().isInitiated()) {
            Log.i("Getnet", "isInitiated");
        } else {
            Log.i("Getnet", "not isInitiated");
            progressDialog.dismiss();
        }
    }
}
