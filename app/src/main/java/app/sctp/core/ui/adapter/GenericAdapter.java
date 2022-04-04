package app.sctp.core.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import app.sctp.core.model.Diffable;

public class GenericAdapter<T extends Diffable> extends RecyclerView.Adapter<ItemViewHolder<T>> implements ItemAdapter<T> {
    private final List<T> items;
    private final ItemViewHolderCreator<T> viewHolderCreator;
    private ItemSelectionListener<T> delegateSelectionListener;
    private final ItemSelectionListener<T> itemSelectionListener;

    public GenericAdapter(@NonNull ItemViewHolderCreator<T> viewHolderCreator) {
        super();
        this.items = new LinkedList<>();
        this.viewHolderCreator = viewHolderCreator;
        this.itemSelectionListener = new ItemSelectionListener<T>() {
            @Override
            public void onItemSelected(T item) {
                if (delegateSelectionListener != null) {
                    delegateSelectionListener.onItemSelected(item);
                }
            }

            @Override
            public void onItemLongSelected(T item) {
                if (delegateSelectionListener != null) {
                    delegateSelectionListener.onItemLongSelected(item);
                }
            }
        };
    }

    public void setItemSelectionListener(ItemSelectionListener<T> listener) {
        this.delegateSelectionListener = listener;
    }

    public ItemSelectionListener<T> getItemSelectionListener() {
        return itemSelectionListener;
    }

    @NonNull
    @Override
    public ItemViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return viewHolderCreator.createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder<T> holder, int position) {
        holder.bind(this, position, Collections.emptyList());
    }

    @Override
    public final void onBindViewHolder(@NonNull ItemViewHolder<T> holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            holder.bind(this, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public T getItemAt(int position) {
        return items.get(position);
    }

    public void clearItems(){
        int count = items.size();;
        items.clear();
        notifyItemRangeRemoved(0, count);
    }

    public void submitList(Collection<T> data) {
        clearItems();
        int currentCount = getItemCount();
        items.addAll(data);
        notifyItemRangeChanged(currentCount, data.size());
    }
}
