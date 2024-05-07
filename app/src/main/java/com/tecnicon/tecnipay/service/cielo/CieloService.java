package com.tecnicon.tecnipay.service.cielo;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import cielo.orders.domain.CheckoutRequest;
import cielo.orders.domain.Credentials;
import cielo.orders.domain.Order;
import cielo.sdk.order.OrderManager;
import cielo.sdk.order.ServiceBindListener;
import cielo.sdk.order.payment.PaymentCode;
import cielo.sdk.order.payment.PaymentError;
import cielo.sdk.order.payment.PaymentListener;

public class CieloService {
    private static final String CLIENT_ID = "JddcuL6WjnXZm8pUwIy0LbAHEiA7ou927Np9esj5xu27nsyI7S";
    private static final String ACCESS_TOKEN = "eZ0ZMlSHWqEesjHPgRtV6D3oG6q4kDEkjRmqeqmP7vOABUVT9E";

    private static OrderManager orderManager;

    private static Credentials credentials;

    private Context contexto;

    public CieloService(Context contexto) {
        credentials = new Credentials(CLIENT_ID, ACCESS_TOKEN);
        orderManager = new OrderManager(credentials, contexto);
        this.contexto = contexto;
    }

    @NonNull
    private Order criarOrder() {
        Order order = orderManager.createDraftOrder("PEDIDO_GUILHERME");

        // Identificação do produto (Stock Keeping Unit)
        String sku = "2891820317391823";
        String name = "Coca-cola lata";

        // Preço unitário em centavos
        int unitPrice = 550;
        int quantity = 3;

        // Unidade de medida do produto String
        String unityOfMeasure = "UNIDADE";

        order.addItem(sku, name, unitPrice, quantity, unityOfMeasure);

        return order;
    }

    private void liberarPedidoPagamento(Order order) {
        orderManager.placeOrder(order);//lnão da para colocar mais itens
    }

    private PaymentListener iniciarPaymentListener() {
        PaymentListener paymentListener = new PaymentListener() {
            @Override
            public void onStart() {
                Log.d("SDKClient", "O pagamento começou.");
            }

            @Override
            public void onPayment(@NotNull Order order) {
                Log.d("SDKClient", "Um pagamento foi realizado.");

                order.markAsPaid();

                orderManager.updateOrder(order);

                //chama o imprimir comprovante
                PrintService.imprimirComprovante(contexto);
            }

            @Override public void onCancel() {
                Log.d("SDKClient", "A operação foi cancelada.");
            }

            @Override public void onError(@NotNull PaymentError paymentError) {
                Log.d("SDKClient", "Houve um erro no pagamento." + paymentError.getDescription());
            }
        };

        return paymentListener;
    }

    public void fazerPagamento(PaymentCode paymentCode) {
        ProgressDialog progressDialog = ProgressDialog.show(contexto, "Processando", "Por favor, aguarde...", true);
        ServiceBindListener serviceBindListener = new ServiceBindListener() {

            @Override
            public void onServiceBoundError(Throwable throwable) {
                //Ocorreu um erro ao tentar se conectar com o serviço OrderManager
                Log.e("cielo", "onServiceBoundError: " + throwable.getMessage());
                progressDialog.dismiss();
            }

            @Override
            public void onServiceBound() {
                //Você deve garantir que sua aplicação se conectou com a LIO a partir desse listener
                //A partir desse momento você pode utilizar as funções do OrderManager, caso contrário uma exceção será lançada.
                Log.i("cielo", "onServiceBound: deu boa");

                PaymentListener paymentListener = iniciarPaymentListener();

                Order order = criarOrder();

                liberarPedidoPagamento(order);

                CheckoutRequest request = new CheckoutRequest.Builder()
                        .orderId(order.getId()) /* Obrigatório */
                        .amount(order.getPrice()) /* Opcional */
                        //.ec("999999999999999") /* Opcional (precisa estar habilitado na LIO) */
                        .email("guilhermefdarosa15@gmail.com") /* Opcional */
                        .paymentCode(paymentCode) /* Opcional */
                        .build();

                progressDialog.dismiss();

                orderManager.checkoutOrder(request, paymentListener);
            }

            @Override
            public void onServiceUnbound() {
                // O serviço foi desvinculado
                Log.i("cielo", "onServiceUnbound: desconectado");
                progressDialog.dismiss();
            }
        };

        orderManager.bind(contexto, serviceBindListener);
    }
}
