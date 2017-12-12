package com.paysomee.client.ui.main;

import java.util.List;

import com.paysomee.client.paysomee.R;
import com.paysomee.client.protocol.models.CardMto;
import com.paysomee.client.protocol.service.ApiProvider;
import com.paysomee.client.protocol.utils.LoadingDialogCallback;
import com.paysomee.client.ui.addcard.AddCardActivity;
import com.paysomee.client.ui.utils.Dialogs;
import com.paysomee.client.utils.DeviceIdProvider;
import com.scottyab.rootbeer.RootBeer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    public static void start(Context context) {
        Intent mainActivity = new Intent(context, MainActivity.class);
        context.startActivity(mainActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        cards.setLayoutManager(new LinearLayoutManager(this));
        cards.setAdapter(cardsAdapter = new CardsAdapter());

        showRootWarning();
    }

    private void showRootWarning() {
        RootBeer rootBeer = new RootBeer(this);
        if (rootBeer.isRooted()) {
            Dialogs.showOkDialog(this, R.string.root_warning_dialog_title, R.string.root_warning_dialog_text);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        public void onSuccess(List<CardMto> response) {
            cardsAdapter.setData(response);
        }
    }
}
