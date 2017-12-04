package client.simplepay.com.paysomee.ui.addcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import client.simplepay.com.paysomee.R;
import client.simplepay.com.paysomee.Storage;
import client.simplepay.com.paysomee.protocol.models.ConfirmCardRequestBody;
import client.simplepay.com.paysomee.protocol.models.RefreshTokensRequestBody;
import client.simplepay.com.paysomee.protocol.service.ApiProvider;
import client.simplepay.com.paysomee.protocol.utils.LoadingDialogCallback;
import client.simplepay.com.paysomee.ui.main.MainActivity;
import client.simplepay.com.paysomee.utils.DeviceIdProvider;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Activity to confirm code after adding card.
 *
 * @author antonid08
 */
public class ConfirmCodeActivity extends AppCompatActivity {

    private static final String CARD_NUMBER_BUNDLE_KEY = "CARD_NUMBER_BUNDLE_KEY";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.code)
    EditText code;

    /**
     * Intent param.
     */
    private String cardNumber;

    public static void start(@NonNull Context context, @NonNull String cardNumber) {
        Intent confirmCode = new Intent(context, ConfirmCodeActivity.class);
        confirmCode.putExtra(CARD_NUMBER_BUNDLE_KEY, cardNumber);
        context.startActivity(confirmCode);
    }

    public static String unpackCardNumber(Intent data) {
        String cardNumber = data.getStringExtra(CARD_NUMBER_BUNDLE_KEY);

        if (cardNumber == null) {
            throw new IllegalStateException("Card number must be not null!");
        }

        return cardNumber;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.confirm_code_activity);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        cardNumber = unpackCardNumber(getIntent());
    }

    @OnClick(R.id.confirm)
    void onConfirmClick() {
        ConfirmCardRequestBody body = new ConfirmCardRequestBody(cardNumber, DeviceIdProvider.getDeviceId(this),
                Integer.valueOf(String.valueOf(code.getText())));

        ApiProvider.getCardsApi().confirmCard(body).enqueue(new ConfirmCardRequestCallback());
    }

    private class ConfirmCardRequestCallback extends LoadingDialogCallback<Void> {

        ConfirmCardRequestCallback() {
            super(ConfirmCodeActivity.this);
        }

        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            super.onResponse(call, response);

            RefreshTokensRequestBody body =
                    new RefreshTokensRequestBody(cardNumber, DeviceIdProvider.getDeviceId(getContext()));

            ApiProvider.getCardsApi().refreshTokens(body).enqueue(new RefreshTokensRequestCallback(getContext()));
        }
    }

    private class RefreshTokensRequestCallback extends LoadingDialogCallback<List<String>> {

        RefreshTokensRequestCallback(@NonNull Context context) {
            super(context);
        }

        @Override
        public void onResponse(Call<List<String>> call, Response<List<String>> response) {
            super.onResponse(call, response);

            new Storage(getContext()).saveTokens(cardNumber, response.body());
            Toast.makeText(getContext(), R.string.confirm_code_card_added, Toast.LENGTH_SHORT).show();
            MainActivity.start(getContext());
        }
    }
}
