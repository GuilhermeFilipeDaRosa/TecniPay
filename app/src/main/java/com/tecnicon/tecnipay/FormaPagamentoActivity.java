package com.tecnicon.tecnipay;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getnet.posdigital.PosDigital;

import cielo.orders.domain.Credentials;
import cielo.sdk.order.OrderManager;
import cielo.sdk.order.ServiceBindListener;

public class FormaPagamentoActivity extends AppCompatActivity {
    private final String CLIENT_ID = "JddcuL6WjnXZm8pUwIy0LbAHEiA7ou927Np9esj5xu27nsyI7S";
    private final String ACCESS_TOKEN = "eZ0ZMlSHWqEesjHPgRtV6D3oG6q4kDEkjRmqeqmP7vOABUVT9E";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Selecione a forma de pagamento");

        setContentView(R.layout.activity_forma_pagamento_layout);

        //inicializarCieloPagamento(); está com problema
        //Caused by: android.content.pm.PackageManager$NameNotFoundException: cielo.launcher

        connectPosDigitalService(); //Getnet

        //adicionarEventListeners(); ToDo
    }

    private void adicionarEventListeners() {
        Button buttonDebito = findViewById(R.id.activity_forma_pagamento_debito);

        buttonDebito.setOnClickListener(v -> {

        });
    }

    private void inicializarCieloPagamento() {
        Credentials credentials = new Credentials(CLIENT_ID, ACCESS_TOKEN);
        OrderManager orderManager = new OrderManager(credentials, this);

        ServiceBindListener serviceBindListener = new ServiceBindListener() {

            @Override public void onServiceBoundError(Throwable throwable) {
                //Ocorreu um erro ao tentar se conectar com o serviço OrderManager
                Log.e("cielo", "onServiceBoundError: " + throwable.getMessage());
            }

            @Override
            public void onServiceBound() {
                //Você deve garantir que sua aplicação se conectou com a LIO a partir desse listener
                //A partir desse momento você pode utilizar as funções do OrderManager, caso contrário uma exceção será lançada.
                Log.i("cielo", "onServiceBound: deu boa");
            }

            @Override
            public void onServiceUnbound() {
                // O serviço foi desvinculado
                Log.i("cielo", "onServiceUnbound: desconectado");
            }
        };

        orderManager.bind(this, serviceBindListener);
    }

    private void connectPosDigitalService() {
        PosDigital.BindCallback bindCallback = new PosDigital.BindCallback() {
            @Override
            public void onError(Exception e) {
                Log.i("Getnet", "onError");
            }

            @Override
            public void onConnected() {
                Log.i("Getnet", "onConnected");
            }

            @Override
            public void onDisconnected() {
                Log.i("Getnet", "onDisconnected");
            }
        };

        PosDigital.register(FormaPagamentoActivity.this, bindCallback);

        if (PosDigital.getInstance().isInitiated()){
            Log.i("Getnet", "isInitiated");
        } else {
            Log.i("Getnet", "not isInitiated");
        }
    }
}
