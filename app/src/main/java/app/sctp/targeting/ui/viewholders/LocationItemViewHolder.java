package app.sctp.targeting.ui.viewholders;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.core.ui.adapter.ItemAdapter;
import app.sctp.core.ui.adapter.ItemSelectionListener;
import app.sctp.core.ui.adapter.ItemViewHolder;
import app.sctp.databinding.ItemLocationBinding;
import app.sctp.targeting.models.GeoLocation;

public class LocationItemViewHolder extends ItemViewHolder<GeoLocation> {
    private final ItemLocationBinding binding;
    private final ItemSelectionListener<GeoLocation> listener;

    public LocationItemViewHolder(@NonNull ItemLocationBinding binding, @NonNull ItemSelectionListener<GeoLocation> listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    @Override
    protected void bind(ItemAdapter<GeoLocation> adapter, int position, List<Object> changePayload) {
        GeoLocation location = adapter.getItemAt(position);

        binding.setLocation(location);

        binding.locationCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongSelected(location);
                return true;
            }
        });

        binding.locationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemSelected(location);
            }

        });
    }
}
