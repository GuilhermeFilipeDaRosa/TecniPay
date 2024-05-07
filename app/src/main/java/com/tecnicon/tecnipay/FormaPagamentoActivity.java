package com.tecnicon.tecnipay;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tecnicon.tecnipay.service.cielo.CieloService;
import com.tecnicon.tecnipay.service.getnet.GetnetService;

import cielo.sdk.order.payment.PaymentCode;

public class FormaPagamentoActivity extends AppCompatActivity {

    private CieloService cieloService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Selecione a forma de pagamento");

        setContentView(R.layout.activity_forma_pagamento_layout);

        //cieloService = new CieloService(this);

        GetnetService.connectPosDigitalService(this); //Getnet, Unable to start service Intent { cmp=com.getnet.posdigital.service/.MainService } U=0: not found

        //adicionarEventListeners();
    }

    private void adicionarEventListeners() {
        Button buttonDebito = findViewById(R.id.activity_forma_pagamento_debito);

        buttonDebito.setOnClickListener(v -> {
            cieloService.fazerPagamento(PaymentCode.DEBITO_AVISTA);
        });

        Button buttonCredito = findViewById(R.id.activity_forma_pagamento_credito);

        buttonCredito.setOnClickListener(v -> {
            cieloService.fazerPagamento(PaymentCode.CREDITO_AVISTA);
        });
    }
}
