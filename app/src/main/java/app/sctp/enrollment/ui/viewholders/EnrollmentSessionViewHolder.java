package app.sctp.enrollment.viewholders;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.core.ui.adapter.ItemAdapter;
import app.sctp.core.ui.adapter.ItemViewHolder;
import app.sctp.databinding.ItemEnrollmentSessionBinding;
import app.sctp.enrollment.models.EnrollmentSession;

public class EnrollmentSessionViewHolder extends ItemViewHolder<EnrollmentSession> {
    private final ItemEnrollmentSessionBinding binding;

    public EnrollmentSessionViewHolder(@NonNull ItemEnrollmentSessionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    protected void bind(ItemAdapter<EnrollmentSession> adapter, int position, List<Object> changePayload) {
        binding.setSession(adapter.getItemAt(position));
    }
}

