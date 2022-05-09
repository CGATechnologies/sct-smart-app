package app.sctp.targeting.ui.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import app.sctp.core.ui.adapter.ItemViewHolderCreator;
import app.sctp.databinding.ItemPreEligibilityVerificationSessionBinding;
import app.sctp.targeting.models.PreEligibilityVerificationSession;
import app.sctp.targeting.models.SessionView;

public class PreEligibilityVerificationSessionViewHolderCreator implements ItemViewHolderCreator<SessionView> {
    @Override
    public PreEligibilityVerificationSessionViewHolder createViewHolder(ViewGroup parent, int viewType) {

        return new PreEligibilityVerificationSessionViewHolder(
                ItemPreEligibilityVerificationSessionBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }
}
