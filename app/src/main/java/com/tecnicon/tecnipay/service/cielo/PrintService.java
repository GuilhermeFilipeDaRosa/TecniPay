package com.tecnicon.tecnipay.service.cielo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.HashMap;

import cielo.sdk.order.PrinterListener;
import cielo.sdk.printer.PrinterManager;
import cielo.orders.domain.PrinterAttributes;

public class PrintService {
    public static void imprimirComprovante(Context contexto) {

        PrinterListener printerListener = new PrinterListener() {
            @Override
            public void onPrintSuccess() {
                Log.d("printerListener", "onPrintSuccess");
            }

            @Override
            public void onError(@Nullable Throwable throwable) {
                Log.d("printerListener", "onError");
            }

            @Override
            public void onWithoutPaper() {
                Log.d("printerListener", "onWithoutPaper");
            }
        };

        PrinterManager printerManager = new PrinterManager(contexto);

        HashMap<String, Integer> alignCenter = new HashMap<>();

        alignCenter.put(PrinterAttributes.KEY_ALIGN, PrinterAttributes.VAL_ALIGN_CENTER);
        alignCenter.put(PrinterAttributes.KEY_TYPEFACE, 1);
        alignCenter.put(PrinterAttributes.KEY_TEXT_SIZE, 20);

        printerManager.printText("Texto para imprimir", alignCenter, printerListener);//testar
    }
}
