package com.paysomee.client.ui.addcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paysomee.client.Storage;
import com.paysomee.client.paysomee.R;
import com.paysomee.client.protocol.models.ConfirmCardRequestBody;
import com.paysomee.client.protocol.models.ErrorMto;
import com.paysomee.client.protocol.models.RefreshTokensOnCardRequestBody;
import com.paysomee.client.protocol.service.ApiProvider;
import com.paysomee.client.protocol.utils.LoadingDialogCallback;
import com.paysomee.client.ui.main.MainActivity;
import com.paysomee.client.utils.DeviceIdProvider;
import com.paysomee.client.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private static String unpackCardNumber(Intent data) {
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
        List<String> errors = validateFields();
        if (errors.isEmpty()) {
            ConfirmCardRequestBody body = new ConfirmCardRequestBody(cardNumber, DeviceIdProvider.getDeviceId(this),
                Integer.valueOf(String.valueOf(code.getText())));

            ApiProvider.getCardsApi().confirmCard(body).enqueue(new ConfirmCardRequestCallback());
        } else {
            Toast.makeText(this, Utils.listToString(errors, "\n"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Validates all fields and returns list of errors.
     */
    private List<String> validateFields() {
        List<String> errors = new ArrayList<>();

        if (code.getText().toString().isEmpty()) {
            errors.add(getString(R.string.confirm_card_code_empty_error));
        } else if (code.getText().toString().length() != getResources().getInteger(R.integer.confirm_code_code_length)) {
            errors.add(String.format(getString(R.string.confirm_card_code_length_error),
                getResources().getInteger(R.integer.confirm_code_code_length)));
        }

        return errors;
    }

    private class ConfirmCardRequestCallback extends LoadingDialogCallback<Void> {

        ConfirmCardRequestCallback() {
            super(ConfirmCodeActivity.this);
        }

        @Override
        public void onSuccess(@Nullable Void response) {
            RefreshTokensOnCardRequestBody body =
                new RefreshTokensOnCardRequestBody(cardNumber, DeviceIdProvider.getDeviceId(getContext()));

            ApiProvider.getTokensApi().refreshTokensOnCard(body).enqueue(new RefreshTokensOnCardsRequestCallback(getContext()));
        }
    }

    private class RefreshTokensOnCardsRequestCallback extends LoadingDialogCallback<List<String>> {

        private Map<String, Integer> errorsMap = new HashMap<>();

        {
            errorsMap.put("PIN is not exist", R.string.confirm_card_code_incorrect_error);
        }

        RefreshTokensOnCardsRequestCallback(@NonNull Context context) {
            super(context);
        }

        @Override
        public void onSuccess(@Nullable List<String> response) {
            new Storage(getContext()).saveTokens(cardNumber, response);
            Toast.makeText(getContext(), R.string.confirm_code_card_added, Toast.LENGTH_SHORT).show();
            MainActivity.start(getContext());
        }

        @Override
        public void onError(ErrorMto error) {
            Toast.makeText(getContext(), errorsMap.get(error.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }
}
