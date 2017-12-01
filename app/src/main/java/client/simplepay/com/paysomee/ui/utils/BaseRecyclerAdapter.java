package client.simplepay.com.paysomee.ui.utils;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Base adapter for RecyclerView.
 *
 * <p>Types:</p>
 * <ol>
 *     <li>{@code T}, data type</li>
 *     <li>{@code T}, view holder type</li>
 * </ol>
 *
 * @author antonid08
 */
public abstract class BaseRecyclerAdapter<T, VH extends BaseRecyclerAdapter.ItemViewHolder<T>> extends RecyclerView.Adapter<VH> {

    private List<T> itemsList = new ArrayList<>();

    @Override
    public final void onBindViewHolder(VH holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setData(List<T> list) {
        itemsList = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    private T getItem(int position) {
        return itemsList.get(position);
    }

    /**
     * Base view holder.
     *
     * <p>Purposes:</p>
     * <ol>
     *     <li>Create ViewHolder from layout id</li>
     *     <li>Capability to bind model to view</li>
     * </ol>
     *
     *
     * T: data type.
     */
    public abstract static class ItemViewHolder<T> extends RecyclerView.ViewHolder {

        protected ItemViewHolder(ViewGroup parent, @LayoutRes int layoutId) {
            super(UiUtils.inflate(parent, layoutId));
        }

        /**
         * Binds item to itemView.
         * @param item item to bind
         */
        public abstract void bind(final T item);
    }
}

