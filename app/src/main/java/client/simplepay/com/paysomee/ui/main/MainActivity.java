package client.simplepay.com.paysomee.ui.main;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import client.simplepay.com.paysomee.R;
import client.simplepay.com.paysomee.protocol.models.CardMto;
import client.simplepay.com.paysomee.protocol.service.ApiProvider;
import client.simplepay.com.paysomee.protocol.utils.LoadingDialogCallback;
import client.simplepay.com.paysomee.ui.addcard.AddCardActivity;
import client.simplepay.com.paysomee.utils.DeviceIdProvider;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Main activity with list of cards.
 *
 * @author antonid08
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cards)
    RecyclerView cards;

    private CardsAdapter cardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        cards.setLayoutManager(new LinearLayoutManager(this));
        cards.setAdapter(cardsAdapter = new CardsAdapter());

        ApiProvider.getCardsApi().getCards(DeviceIdProvider.getDeviceId(this)).enqueue(new GetCardsRequestCallback());
    }

    @OnClick(R.id.addCard)
    void onAddCardClicked() {
        AddCardActivity.start(this);
    }

    private class GetCardsRequestCallback extends LoadingDialogCallback<List<CardMto>> {

        GetCardsRequestCallback() {
            super(MainActivity.this);
        }

        @Override
        public void onResponse(Call<List<CardMto>> call, Response<List<CardMto>> response) {
            super.onResponse(call, response);

            cardsAdapter.setData(response.body());
        }
    }
}
