package app.sctp.targeting.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import app.sctp.core.ui.adapter.ItemViewHolder;
import app.sctp.core.ui.adapter.ItemViewHolderCreator;
import app.sctp.databinding.ItemLocationBinding;
import app.sctp.targeting.models.GeoLocation;

public class LocationItemViewHolderCreator implements ItemViewHolderCreator<GeoLocation> {
    @Override
    public ItemViewHolder<GeoLocation> createViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new LocationItemViewHolder(ItemLocationBinding.inflate(inflater, parent, false));
    }
}
