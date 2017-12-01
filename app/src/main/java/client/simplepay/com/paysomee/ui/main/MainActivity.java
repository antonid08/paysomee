package client.simplepay.com.paysomee.ui.main;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import client.simplepay.com.paysomee.R;
import client.simplepay.com.paysomee.protocol.models.CardMto;
import client.simplepay.com.paysomee.protocol.service.ApiProvider;
import client.simplepay.com.paysomee.protocol.utils.LoadingDialogCallback;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    private Toolbar toolbar;

    @BindView(R.id.cards)
    private RecyclerView cards;

    private CardsAdapter cardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        cards.setLayoutManager(new LinearLayoutManager(this));
        cards.setAdapter(cardsAdapter = new CardsAdapter());

        ApiProvider.getCardsApi().getCards().enqueue(new GetCardsRequestCallback());
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
