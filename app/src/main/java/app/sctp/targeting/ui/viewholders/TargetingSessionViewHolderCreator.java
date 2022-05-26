package app.sctp.targeting.ui.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import app.sctp.core.ui.adapter.ItemViewHolderCreator;
import app.sctp.databinding.ItemTargetingSessionBinding;
import app.sctp.targeting.models.TargetingSession;

public class TargetingSessionViewHolderCreator implements ItemViewHolderCreator<TargetingSession> {
    @Override
    public TargetingSessionViewHolder createViewHolder(ViewGroup parent, int viewType) {

        return new TargetingSessionViewHolder(
                ItemTargetingSessionBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }
}
