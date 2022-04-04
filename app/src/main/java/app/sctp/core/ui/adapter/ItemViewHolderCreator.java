package app.sctp.core.ui.adapter;

import android.view.ViewGroup;

import app.sctp.core.model.Diffable;

public interface ItemViewHolderCreator<T extends Diffable> {
    ItemViewHolder<T> createViewHolder(ViewGroup parent, int viewType);
}
