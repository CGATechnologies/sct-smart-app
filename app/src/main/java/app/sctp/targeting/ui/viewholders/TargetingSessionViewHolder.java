package app.sctp.targeting.ui.viewholders;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.core.ui.adapter.ItemAdapter;
import app.sctp.core.ui.adapter.ItemViewHolder;
import app.sctp.databinding.ItemTargetingSessionBinding;
import app.sctp.targeting.models.TargetingSession;

public class TargetingSessionViewHolder extends ItemViewHolder<TargetingSession> {
    private final ItemTargetingSessionBinding binding;

    public TargetingSessionViewHolder(@NonNull ItemTargetingSessionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    protected void bind(ItemAdapter<TargetingSession> adapter, int position, List<Object> changePayload) {
        binding.setSession(adapter.getItemAt(position));
    }
}
