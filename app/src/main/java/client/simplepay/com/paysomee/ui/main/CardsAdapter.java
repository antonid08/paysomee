package client.simplepay.com.paysomee.ui.main;

import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import client.simplepay.com.paysomee.R;
import client.simplepay.com.paysomee.protocol.models.CardMto;
import client.simplepay.com.paysomee.ui.utils.BaseRecyclerAdapter;

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
        void onClick() {

        }
    }
}
