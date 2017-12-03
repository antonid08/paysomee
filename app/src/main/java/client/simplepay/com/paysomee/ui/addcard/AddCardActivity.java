package client.simplepay.com.paysomee.ui.addcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

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
    private Toolbar toolbar;

    @BindView(R.id.number)
    private EditText number;

    @BindView(R.id.holderName)
    private EditText holderName;

    @BindView(R.id.cvv)
    private EditText cvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_activity);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        //ApiProvider.getCardsApi().getCards().enqueue(new GetCardsRequestCallback());
    }

}
