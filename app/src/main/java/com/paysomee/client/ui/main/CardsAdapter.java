package com.paysomee.client.ui.main;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.paysomee.client.paysomee.R;
import com.paysomee.client.protocol.models.CardMto;
import com.paysomee.client.ui.payment.PaymentActivity;
import com.paysomee.client.ui.utils.BaseRecyclerAdapter;

/**
 * Adapter for cards RecyclerView.
 *
 * @author antonid08
 */
public class CardsAdapter extends BaseRecyclerAdapter<CardMto, BaseRecyclerAdapter.ItemViewHolder<CardMto>> {

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardViewHolder(parent);
    }

    static class CardViewHolder extends BaseRecyclerAdapter.ItemViewHolder<CardMto> {

        @BindView(R.id.number)
        TextView number;

        @BindView(R.id.amount)
        TextView amount;

        private CardMto card;

        CardViewHolder(ViewGroup parent) {
            super(parent, R.layout.card_item);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(CardMto item) {
            number.setText(item.getNumber());
            amount.setText(String.format(itemView.getContext().getString(R.string.amount_pattern), item.getAmount()));

            card = item;
        }

        @OnClick
        void onClick(View view) {
            PaymentActivity.start(view.getContext(), card.getNumber());
        }
    }
}
