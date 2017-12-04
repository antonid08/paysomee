package client.simplepay.com.paysomee.ui.addcard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import client.simplepay.com.paysomee.R;
import client.simplepay.com.paysomee.ui.utils.BaseListAdapter;

/**
 * Adapter for spinner with list of {@link Bank}
 *
 * @author antonid08
 */
class BankSpinnerAdapter extends BaseListAdapter<Bank> {

    BankSpinnerAdapter() {
        setData(Arrays.asList(Bank.values()));
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public final View getView(int position, View view, ViewGroup viewGroup) {
        return getViewOnPosition(position, view, viewGroup, R.layout.bank_spinner_item);
    }

    @Override
    protected ItemViewHolder<Bank> createViewHolder(View convertView) {
        return new StatusViewHolder(convertView);
    }

    static class StatusViewHolder implements ItemViewHolder<Bank> {

        private static final Map<Bank, Integer> BANKS_MAP;

        static {
            BANKS_MAP = new HashMap<>();
            BANKS_MAP.put(Bank.BELARUSBANK, R.string.belarusbank_name);
            BANKS_MAP.put(Bank.BELINVESTBANK, R.string.belinvestbank_name);
        }

        @BindView(R.id.name)
        TextView name;

        StatusViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void bind(Bank bank) {
            name.setText(BANKS_MAP.get(bank));
        }

    }
}

