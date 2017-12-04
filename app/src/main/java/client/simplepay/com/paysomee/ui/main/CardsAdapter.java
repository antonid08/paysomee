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

        @BindView(R.id.bankName)
        TextView bankName;

        @BindView(R.id.number)
        TextView number;

        @BindView(R.id.holderName)
        TextView holderName;

        private CardMto card;

        CardViewHolder(ViewGroup parent) {
            super(parent, R.layout.card_item);

            ButterKnife.bind(this, itemView);
        }

        @Override
        public void bind(CardMto item) {
            bankName.setText(item.getBankName());
            number.setText(item.getNumber());
            holderName.setText(item.getHolderName());

            card = item;
        }

        @OnClick
        void onClick() {

        }
    }
}
