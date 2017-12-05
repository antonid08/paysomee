package com.paysomee.terminal.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.paysomee.terminal.R;
import com.paysomee.terminal.protocol.models.PayRequestBody;
import com.paysomee.terminal.protocol.service.ApiProvider;
import com.paysomee.terminal.protocol.utils.HandleErrorsCallback;

import java.util.List;

import be.appfoundry.nfclibrary.activities.NfcActivity;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Activity which reads payment tokens by NFC and communicates with payments server.
 *
 * @author antonid08
 */
public class PaymentActivity extends NfcActivity {

    private static final String PAYMENT_SUM_BUNDLE_KEY = "PAYMENT_SUM_BUNDLE_KEY";
    private static final float PAYMENT_SUM_DEFAUT_VALUE = -1f;

    /**
     * Intent param
     */
    private float sum;

    public static void start(Context context, float sum) {
        Intent payment = new Intent(context, PaymentActivity.class);
        payment.putExtra(PAYMENT_SUM_BUNDLE_KEY, sum);
        context.startActivity(payment);
    }

    private static float unpackPaymentSum(Intent data) {
        float sum = data.getFloatExtra(PAYMENT_SUM_BUNDLE_KEY, PAYMENT_SUM_DEFAUT_VALUE);

        if (sum == PAYMENT_SUM_DEFAUT_VALUE) {
            throw new IllegalStateException("Sum was not passed!");
        }

        return sum;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sum = unpackPaymentSum(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        List<String> messages = getNfcMessages();
        if (messages.size() != 1) {
            //todo log it
            Toast.makeText(this, R.string.incorrect_nfc_data_text, Toast.LENGTH_SHORT).show();
            return;
        }

        String token = messages.get(0);
        ApiProvider.getCardsApi().pay(new PayRequestBody(token, sum)).enqueue(new PayRequestCallback());
    }

    private class PayRequestCallback extends HandleErrorsCallback<Boolean> {

        PayRequestCallback() {
            super(PaymentActivity.this);
        }

        @Override
        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
            boolean result;
            try {
                result = response.body();
            } catch (NullPointerException e) {
                result = false;
            }

            Toast.makeText(getContext(), result ? R.string.transaction_ok : R.string.transaction_failed,
                    Toast.LENGTH_SHORT).show();

            MainActivity.start(getContext());
        }
    }
}
