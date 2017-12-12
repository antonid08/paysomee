package com.paysomee.client.ui.main;

import com.paysomee.client.paysomee.R;
import com.paysomee.client.protocol.models.CardMto;
import com.paysomee.client.protocol.models.DeleteCardRequestBody;
import com.paysomee.client.protocol.models.ErrorMto;
import com.paysomee.client.protocol.service.ApiProvider;
import com.paysomee.client.protocol.utils.HandleErrorsCallback;
import com.paysomee.client.ui.payment.PaymentActivity;
import com.paysomee.client.ui.utils.BaseRecyclerAdapter;
import com.paysomee.client.ui.utils.Dialogs;
import com.paysomee.client.utils.DeviceIdProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Adapter for cards RecyclerView.
 *
 * @author antonid08
 */
class CardsAdapter extends BaseRecyclerAdapter<CardMto, BaseRecyclerAdapter.ItemViewHolder<CardMto>> {

    private CardsContainer cardsContainer;

    CardsAdapter(CardsContainer cardsContainer) {
        this.cardsContainer = cardsContainer;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewHolder(parent, cardsContainer);
    }

    static class CardViewHolder extends BaseRecyclerAdapter.ItemViewHolder<CardMto> {

        @BindView(R.id.number)
        TextView number;

        @BindView(R.id.amount)
        TextView amount;

        @BindView(R.id.holderName)
        TextView holderName;

        private CardsContainer cardsContainer;

        private CardMto card;

        CardViewHolder(ViewGroup parent, CardsContainer cardsContainer) {
            super(parent, R.layout.card_item);

            ButterKnife.bind(this, itemView);

            this.cardsContainer = cardsContainer;
        }

        @Override
        public void bind(CardMto item) {
            number.setText(item.getNumber());
            amount.setText(String.format(itemView.getContext().getString(R.string.amount_pattern), item.getAmount()));
            holderName.setText(item.getHolderName());

            card = item;
        }

        @OnClick
        void onClick(View view) {
            PaymentActivity.start(view.getContext(), card.getNumber());
        }

        @OnLongClick
        boolean onLongClick(View view) {
            Dialogs.showOkNoDialog(view.getContext(), R.string.main_activity_delete_card_confirmation,
                R.string.dialog_ok, R.string.dialog_cancel, new OnCardRemoveDialogOkClickListener(view.getContext()));

            return true;
        }

        private class OnCardRemoveDialogOkClickListener implements DialogInterface.OnClickListener {

            private Context context;

            OnCardRemoveDialogOkClickListener(Context context) {
                this.context = context;
            }

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteCardRequestBody body =
                    new DeleteCardRequestBody(card.getNumber(), card.getHolderName(), DeviceIdProvider.getDeviceId(context));

                ApiProvider.getCardsApi().deleteCard(body).enqueue(new DeleteCardRequestCallback(context));
            }

        }

        private class DeleteCardRequestCallback extends HandleErrorsCallback<Void> {

            DeleteCardRequestCallback(@NonNull Context context) {
                super(context);
            }

            @Override
            public void onSuccess(@Nullable Void response) {
                cardsContainer.update();
            }

            @Override
            public void onError(ErrorMto error) {
                Toast.makeText(getContext(), R.string.api_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
