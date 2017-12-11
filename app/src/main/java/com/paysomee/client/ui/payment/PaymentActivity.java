package com.paysomee.client.ui.payment;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.paysomee.client.Storage;
import com.paysomee.client.paysomee.R;
import com.paysomee.client.protocol.models.RefreshTokensOnCardRequestBody;
import com.paysomee.client.protocol.service.ApiProvider;
import com.paysomee.client.protocol.utils.LoadingDialogCallback;
import com.paysomee.client.utils.DeviceIdProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
public class PaymentActivity extends AppCompatActivity {

    private static final String CARD_NUMBER_BUNDLE_KEY = "CARD_NUMBER_BUNDLE_KEY";

    private static boolean isActive = false;

    /**
     * Intent param.
     */
    private static String cardNumber;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cardInfo)
    TextView cardInfo;

    @BindView(R.id.updateTokens)
    Button updateTokens;

    public static void start(@NonNull Context context, @NonNull String cardNumber) {
        Intent payment = new Intent(context, PaymentActivity.class);
        payment.putExtra(CARD_NUMBER_BUNDLE_KEY, cardNumber);
        context.startActivity(payment);
    }

    /**
     * Is activity in foreground
     */
    static boolean isActive() {
        return isActive;
    }

    static String getCardNumber() {
        return cardNumber;
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

        setUpUpdateTokensButton();
    }

    private void setUpUpdateTokensButton() {
        int tokensLeft = new Storage(this).loadTokens(cardNumber).size();
        updateTokens.setText(String.format(getString(R.string.payment_update_tokens_pattern), tokensLeft));
    }

    @Override
    public void onResume() {
        super.onResume();
        isActive = true;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokensEnded(TokensEndedEvent event) {
        Toast.makeText(this, R.string.payment_tokens_ended, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTokenRemoved(TokenRemovedEvent event) {
        setUpUpdateTokensButton();
    }

    @OnClick(R.id.updateTokens)
    void onUpdateTokensClick() {
        RefreshTokensOnCardRequestBody body = new RefreshTokensOnCardRequestBody(cardNumber, DeviceIdProvider.getDeviceId(this));
        ApiProvider.getTokensApi().refreshTokensOnCard(body).enqueue(new RefreshTokensOnCardsRequestCallback(this));
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
                Toast.makeText(getContext(), R.string.refresh_tokens_success, Toast.LENGTH_SHORT).show();
                setUpUpdateTokensButton();
            } else {
                Toast.makeText(getContext(), R.string.refresh_tokens_failure, Toast.LENGTH_SHORT).show();
            }

        }
    }

}
