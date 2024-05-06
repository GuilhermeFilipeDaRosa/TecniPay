package com.tecnicon.tecnipay;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

        adicionarEventListeners();
    }

    private void adicionarEventListeners() {
        Button buttonDebito = findViewById(R.id.activity_forma_pagamento_debito);

        buttonDebito.setOnClickListener(v -> {
            inicializarCieloPagamento(FormaPagamentoActivity.this);
        });
    }

    private void inicializarCieloPagamento(Context context) {
        Credentials credentials = new Credentials(CLIENT_ID, ACCESS_TOKEN);
        OrderManager orderManager = new OrderManager(credentials, context);

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

        orderManager.bind(context, serviceBindListener);
    }
}
