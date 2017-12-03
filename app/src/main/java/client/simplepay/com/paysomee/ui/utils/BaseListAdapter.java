package client.simplepay.com.paysomee.ui.utils;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Base class for list adapters.
 * Using ViewHolder pattern.
 *
 * @param <T> data type
 *
 * @author antdoni08
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    private List<T> itemsList = new ArrayList<>();

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public T getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<T> list) {
        itemsList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    /**
     * Returns view with binded data from source.
     * Reusing view included.
     *
     * @param position position of element in data source
     * @param view {@code View} view (if exists)
     * @param viewGroup parent {@code View}
     * @param itemLayoutResourceId layout resource id
     *
     * @return view with binded value.
     */
    protected final View getViewOnPosition(int position, View view, ViewGroup viewGroup,
                                           int itemLayoutResourceId) {
        ItemViewHolder<T> holder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayoutResourceId,
                viewGroup, false);

            holder = createViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ItemViewHolder<T>) view.getTag();
        }

        holder.bind(getItem(position));
        return view;
    }

    /**
     * Creates ViewHolder.
     *
     * @param convertView view of element
     * @return implementation {@link ItemViewHolder <T>}
     */
    protected abstract ItemViewHolder<T> createViewHolder(View convertView);

    /**
     * ViewHolder for data.
     *
     * @param <T> data type.
     */
    protected interface ItemViewHolder<T> {

        void bind(final T item);
    }
}

