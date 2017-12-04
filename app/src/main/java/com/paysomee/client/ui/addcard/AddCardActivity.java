package com.paysomee.client.ui.addcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.paysomee.client.paysomee.R;
import com.paysomee.client.protocol.models.AddCardRequestBody;
import com.paysomee.client.protocol.service.ApiProvider;
import com.paysomee.client.protocol.utils.LoadingDialogCallback;
import com.paysomee.client.utils.DeviceIdProvider;
import retrofit2.Call;
import retrofit2.Response;

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

    @BindView(R.id.bankName)
    Spinner bankName;

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

        bankName.setAdapter(new BankSpinnerAdapter());
    }

    @OnClick(R.id.add)
    void onAddClicked() {
        AddCardRequestBody body = new AddCardRequestBody(number.getText().toString(), Integer.valueOf(cvv.getText().toString()),
            holderName.getText().toString(), DeviceIdProvider.getDeviceId(this));

        ApiProvider.getCardsApi().addCard(body).enqueue(new AddCardRequestCallback());
    }

    private class AddCardRequestCallback extends LoadingDialogCallback<Void> {

        AddCardRequestCallback() {
            super(AddCardActivity.this);
        }

        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            super.onResponse(call, response);
            ConfirmCodeActivity.start(getContext(), number.getText().toString());
        }
    }

}
