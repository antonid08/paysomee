package com.paysomee.client.ui.payment;

import java.util.ArrayList;
import java.util.List;

import com.paysomee.client.Storage;
import com.paysomee.client.paysomee.R;
import com.paysomee.client.protocol.models.RefreshTokensOnCardRequestBody;
import com.paysomee.client.protocol.service.ApiProvider;
import com.paysomee.client.protocol.utils.LoadingDialogCallback;
import com.paysomee.client.utils.DeviceIdProvider;

import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;
import be.appfoundry.nfclibrary.exceptions.InsufficientCapacityException;
import be.appfoundry.nfclibrary.exceptions.ReadOnlyTagException;
import be.appfoundry.nfclibrary.exceptions.TagNotPresentException;
import be.appfoundry.nfclibrary.tasks.interfaces.AsyncOperationCallback;
import be.appfoundry.nfclibrary.utilities.interfaces.NfcWriteUtility;
import be.appfoundry.nfclibrary.utilities.sync.NfcWriteUtilityImpl;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

/**
 * From this activity user can initiate payment by NFC.
 *
 * @author antonid08
 */
public class PaymentActivity extends CustomNfcActivity {

    private static final String CARD_NUMBER_BUNDLE_KEY = "CARD_NUMBER_BUNDLE_KEY";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cardInfo)
    TextView cardInfo;

    /**
     * Intent param.
     */
    private String cardNumber;

    private String currentToken;

    int i = 0;

    public static void start(@NonNull Context context, @NonNull String cardNumber) {
        Intent payment = new Intent(context, PaymentActivity.class);
        payment.putExtra(CARD_NUMBER_BUNDLE_KEY, cardNumber);
        context.startActivity(payment);
    }

    private static String unpackCardNumber(Intent data) {
        String cardNumber = data.getStringExtra(CARD_NUMBER_BUNDLE_KEY);

        if (cardNumber == null) {
            throw new IllegalStateException("Card number must be not null!");
        }

        return cardNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.payment_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        cardNumber = unpackCardNumber(getIntent());

        cardInfo.setText(String.format(getString(R.string.payment_card_info_pattern), cardNumber));
    }

    @OnClick(R.id.pay)
    void onPayClicked() {
/*        List<String> tokens = new Storage(PaymentActivity.this).loadTokens(cardNumber);

        if (!tokens.isEmpty()) {
            String token = tokens.get(tokens.size() - 1);
            new Storage(PaymentActivity.this).saveTokens(cardNumber, tokens.subList(0, tokens.size() - 1));

            currentToken = token;
            //enableBeam();
        } else {
            final RefreshTokensOnCardRequestBody body =
                new RefreshTokensOnCardRequestBody(cardNumber, DeviceIdProvider.getDeviceId(PaymentActivity.this));

            ApiProvider.getTokensApi().refreshTokensOnCard(body).
                enqueue(new RefreshTokensOnCardsRequestCallback(PaymentActivity.this));
        }*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
      /*  setIntent(intent);
        if (getIntent() != null) {
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
                //new Write(this,mAsyncOperationCallback).executeWriteOperation();
                try {
                    new NfcWriteUtilityImpl().writeTextToTagFromIntent("hey", getIntent());
                    Toast.makeText(this, "sent", Toast.LENGTH_SHORT).show();
                }
                catch (FormatException e) {
                    e.printStackTrace();
                }
                catch (TagNotPresentException e) {
                    e.printStackTrace();
                }
                catch (ReadOnlyTagException e) {
                    e.printStackTrace();
                }
                catch (InsufficientCapacityException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

/*
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        return new NdefMessage(
            new NdefRecord[] {
                createMime("application/vnd.com.example.android.beam", currentToken.getBytes())
            });
    }*/

    private class PayCallback implements AsyncOperationCallback {

        @Override
        public boolean performWrite(NfcWriteUtility writeUtility)
            throws ReadOnlyTagException, InsufficientCapacityException, TagNotPresentException, FormatException {

            List<String> tokens = new Storage(PaymentActivity.this).loadTokens(cardNumber);

            if (!tokens.isEmpty()) {
                String token = tokens.get(tokens.size() - 1);
                new Storage(PaymentActivity.this).saveTokens(cardNumber, tokens.subList(0, tokens.size() - 1));

                return writeUtility.writeTextToTagFromIntent(token, getIntent());
            } else {
                final RefreshTokensOnCardRequestBody body =
                    new RefreshTokensOnCardRequestBody(cardNumber, DeviceIdProvider.getDeviceId(PaymentActivity.this));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ApiProvider.getTokensApi().refreshTokensOnCard(body).
                            enqueue(new RefreshTokensOnCardsRequestCallback(PaymentActivity.this));
                    }
                });

                return false;
            }
        }
    }

    private class RefreshTokensOnCardsRequestCallback extends LoadingDialogCallback<List<String>> {

        RefreshTokensOnCardsRequestCallback(@NonNull Context context) {
            super(context);
        }

        @Override
        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
            super.onResponse(call, response);

            List<String> tokens = response.body() != null ? response.body() : new ArrayList<String>();

            if (!tokens.isEmpty()) {
                new Storage(getContext()).saveTokens(cardNumber, response.body());
                onPayClicked();
            } else {
                Toast.makeText(getContext(), R.string.refresh_tokens_failure, Toast.LENGTH_LONG).show();
            }

        }
    }

}
