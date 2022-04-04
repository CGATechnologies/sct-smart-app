package app.sctp.core.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import app.sctp.core.model.Diffable;

public class GenericPagedAdapter<T extends Diffable> extends PagedListAdapter<T, ItemViewHolder<T>> implements ItemAdapter<T> {
    private final ItemViewHolderCreator<T> viewHolderCreator;
    private ItemSelectionListener<T> delegateSelectionListener;
    private final ItemSelectionListener<T> itemSelectionListener;

    public GenericPagedAdapter(@NonNull ItemViewHolderCreator<T> viewHolderCreator) {
        super(new DiffUtil.ItemCallback<T>() {
            @Override
            public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
                return Objects.equals(oldItem.getDiffValue(), newItem.getDiffValue());
            }

            @Override
            public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
                return oldItem.contentsSameAs(newItem);
            }

            @Nullable
            @Override
            public Object getChangePayload(@NonNull T oldItem, @NonNull T newItem) {
                return super.getChangePayload(oldItem, newItem);
            }
        });
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

    public T getItemAt(int position) {
        return getItem(position);
    }

}
