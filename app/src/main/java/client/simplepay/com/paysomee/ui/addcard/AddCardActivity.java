package client.simplepay.com.paysomee.ui.addcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import client.simplepay.com.paysomee.R;
import client.simplepay.com.paysomee.protocol.service.ApiProvider;
import client.simplepay.com.paysomee.ui.main.CardsAdapter;

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

}
