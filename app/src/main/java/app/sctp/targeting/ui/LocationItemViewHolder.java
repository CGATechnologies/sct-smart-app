package app.sctp.targeting.ui;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.core.ui.adapter.GenericAdapter;
import app.sctp.core.ui.adapter.ItemAdapter;
import app.sctp.core.ui.adapter.ItemViewHolder;
import app.sctp.databinding.ItemLocationBinding;
import app.sctp.targeting.models.GeoLocation;

public class LocationItemViewHolder extends ItemViewHolder<GeoLocation> {
    private final ItemLocationBinding binding;

    public LocationItemViewHolder(@NonNull ItemLocationBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    protected void bind(ItemAdapter<GeoLocation> adapter, int position, List<Object> changePayload) {
        binding.setLocation(adapter.getItemAt(position));
    }
}
