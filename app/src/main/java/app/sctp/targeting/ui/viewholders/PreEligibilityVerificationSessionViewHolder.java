package app.sctp.targeting.ui.viewholders;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.core.ui.adapter.ItemAdapter;
import app.sctp.core.ui.adapter.ItemViewHolder;
import app.sctp.databinding.ItemPreEligibilityVerificationSessionBinding;
import app.sctp.targeting.models.PreEligibilityVerificationSession;
import app.sctp.targeting.models.SessionView;

public class PreEligibilityVerificationSessionViewHolder extends ItemViewHolder<SessionView> {
    private final ItemPreEligibilityVerificationSessionBinding binding;

    public PreEligibilityVerificationSessionViewHolder(@NonNull ItemPreEligibilityVerificationSessionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    protected void bind(ItemAdapter<SessionView> adapter, int position, List<Object> changePayload) {
        binding.setSession(adapter.getItemAt(position));
    }
}
