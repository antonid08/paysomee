package com.paysomee.client.ui.addcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.paysomee.client.paysomee.R;
import com.paysomee.client.protocol.models.AddCardRequestBody;
import com.paysomee.client.protocol.models.ErrorMto;
import com.paysomee.client.protocol.service.ApiProvider;
import com.paysomee.client.protocol.utils.LoadingDialogCallback;
import com.paysomee.client.utils.DeviceIdProvider;
import com.paysomee.client.utils.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Activity to add card.
 *
 * @author antonid08
 */
public class AddCardActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.number)
    EditText number;

    @BindView(R.id.holderName)
    EditText holderName;

    @BindView(R.id.cvv)
    EditText cvv;

    public static void start(@NonNull Context context) {
        Intent addCard = new Intent(context, AddCardActivity.class);
        context.startActivity(addCard);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_activity);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.add)
    void onAddClicked() {
        List<String> validationErrors = validateFields();

        if (validationErrors.isEmpty()) {
            AddCardRequestBody body = new AddCardRequestBody(number.getText().toString(), Integer.valueOf(cvv.getText().toString()),
                holderName.getText().toString(), DeviceIdProvider.getDeviceId(this));

            ApiProvider.getCardsApi().addCard(body).enqueue(new AddCardRequestCallback());
        } else {
            Toast.makeText(this, Utils.listToString(validationErrors, "\n"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Validates all fields and returns list of errors.
     */
    private List<String> validateFields() {
        List<String> errors = new ArrayList<>();

        if (number.getText().toString().isEmpty()) {
            errors.add(getString(R.string.add_card_empty_number_error));
        }

        if (holderName.getText().toString().isEmpty()) {
            errors.add(getString(R.string.add_card_empty_holder_error));
        }

        if (cvv.getText().toString().isEmpty()) {
            errors.add(getString(R.string.add_card_empty_cvv_error));
        } else if (cvv.getText().toString().length() != getResources().getInteger(R.integer.cvv_length)) {
            errors.add(
                String.format(getString(R.string.add_card_length_cvv_error), getResources().getInteger(R.integer.cvv_length)));
        }

        return errors;
    }

    private class AddCardRequestCallback extends LoadingDialogCallback<Void> {

        private Map<String, Integer> errorsMap = new HashMap<>();

        {
            errorsMap.put("Credentials are invalid", R.string.add_card_invalid_credentials_error);
            errorsMap.put("cvv or owner is invalid", R.string.add_card_invalid_credentials_error);
            errorsMap.put("card is not exist", R.string.add_card_not_exists_error);
            errorsMap.put("Card already connected", R.string.add_card_already_added_error);
        }

        AddCardRequestCallback() {
            super(AddCardActivity.this);
        }

        @Override
        public void onSuccess(Void response) {
            ConfirmCodeActivity.start(getContext(), number.getText().toString());
        }

        @Override
        public void onError(ErrorMto error) {
            String message = error.getMessage();

            Toast.makeText(getContext(), errorsMap.get(message), Toast.LENGTH_SHORT).show();
        }
    }

}
