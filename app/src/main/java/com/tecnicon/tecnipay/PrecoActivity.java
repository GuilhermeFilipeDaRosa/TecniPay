package com.tecnicon.tecnipay;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PrecoActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Informe o preço");

        setContentView(R.layout.activity_preco_layout);

        EditText campoPreco = findViewById(R.id.activity_preco_valor);

        adicionarFocusEhAbrirTeclado(campoPreco);
        adicionarEventListerns(campoPreco);

    }

    private void abrirActivityFormaPagamento(EditText campoPreco) {
        Intent intent = new Intent(PrecoActivity.this, FormaPagamentoActivity.class);
        intent.putExtra("preco", campoPreco.getAlpha());

        startActivity(intent);
    }

    private void adicionarFocusEhAbrirTeclado(EditText campoPreco) {
        campoPreco.requestFocus();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void adicionarEventListerns(EditText campoPreco) {
        campoPreco.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                abrirActivityFormaPagamento(campoPreco);
            }

            return true;
        });
    }
}
