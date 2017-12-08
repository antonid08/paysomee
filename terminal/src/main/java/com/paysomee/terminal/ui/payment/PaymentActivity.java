package com.paysomee.terminal.ui.payment;

import java.io.IOException;

import com.paysomee.terminal.R;
import com.paysomee.terminal.protocol.models.PayRequestBody;
import com.paysomee.terminal.protocol.service.ApiProvider;
import com.paysomee.terminal.protocol.utils.HandleErrorsCallback;
import com.paysomee.terminal.support.nfc.ApduCommandSelect;
import com.paysomee.terminal.support.nfc.Util;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Activity which reads payment tokens by NFC and communicates with payments server.
 *
 * @author antonid08
 */
public class PaymentActivity extends NfcReaderActivity {

    private static final String NFC_PAYMENT_TOKEN_REQUEST_CODE = "325041592E5359532E44444630";

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

    private static float unpackSum(Intent data) {
        return data.getFloatExtra(PAYMENT_SUM_BUNDLE_KEY, PAYMENT_SUM_DEFAUT_VALUE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.payment_activity);

        sum = unpackSum(getIntent());
        if (sum == PAYMENT_SUM_DEFAUT_VALUE) {
            throw new IllegalStateException("Sum was not passed!");
        }
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        IsoDep isoDep = IsoDep.get(tag);

        if (isoDep == null) {
            Toast.makeText(this, R.string.device_interaction_error, Toast.LENGTH_SHORT).show();
            return;
        }


        byte[] deviceResponse;

        try {
            isoDep.connect();
            deviceResponse = isoDep.transceive(new ApduCommandSelect(Util.hexToBytes(NFC_PAYMENT_TOKEN_REQUEST_CODE)).toBytes());
            isoDep.close();
        }
        catch (IOException e) {
            Toast.makeText(this, R.string.device_interaction_error, Toast.LENGTH_SHORT).show();
            return;
        }

        String token = new String(deviceResponse);

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
            }
            catch (NullPointerException e) {
                result = false;
            }

            Toast.makeText(getContext(), result ? R.string.transaction_ok : R.string.transaction_failed,
                Toast.LENGTH_SHORT).show();

            finish();
        }
    }
}
