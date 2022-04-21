package app.sctp.core.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import app.sctp.R;
import app.sctp.core.model.Diffable;

public class GenericAdapter<T extends Diffable> extends RecyclerView.Adapter<ItemViewHolder<T>> implements ItemAdapter<T> {
    private final List<T> items;
    private final boolean allowItemSelection;
    private final View.OnClickListener onClickListener;
    private final ItemViewHolderCreator<T> viewHolderCreator;
    private ItemSelectionListener<T> delegateSelectionListener;
    private final ItemSelectionListener<T> itemSelectionListener;

    public GenericAdapter(@NonNull ItemViewHolderCreator<T> viewHolderCreator) {
        this(viewHolderCreator, true);
    }

    public GenericAdapter(@NonNull ItemViewHolderCreator<T> viewHolderCreator, boolean allowItemSelection) {
        super();
        this.items = new LinkedList<>();
        this.viewHolderCreator = viewHolderCreator;
        this.allowItemSelection = allowItemSelection;
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
        this.onClickListener = v -> {
            ItemViewHolder<?> itemViewHolder = (ItemViewHolder<?>) v.getTag(R.id.view_holder_self_reference_tag);
            itemSelectionListener.onItemSelected(getItemAt(itemViewHolder.getAdapterPosition()));
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
        if (allowItemSelection) {
            holder.itemView.setOnClickListener(this.onClickListener);
            holder.itemView.setTag(R.id.view_holder_self_reference_tag, holder);
        } else {
            holder.itemView.setOnClickListener(null);
            holder.itemView.setTag(R.id.view_holder_self_reference_tag, null);
        }
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

    public void clearItems() {
        int count = items.size();
        ;
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
