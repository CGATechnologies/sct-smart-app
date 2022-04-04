package app.sctp.core.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import app.sctp.core.model.Diffable;

public abstract class ItemViewHolder<T extends Diffable> extends RecyclerView.ViewHolder {

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected void bind(ItemAdapter<T> adapter, int position, List<Object> changePayload) {

    }
}
