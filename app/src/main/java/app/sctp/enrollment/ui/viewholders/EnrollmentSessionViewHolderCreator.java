package app.sctp.enrollment.ui.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import app.sctp.core.ui.adapter.ItemViewHolderCreator;
import app.sctp.databinding.ItemEnrollmentSessionBinding;
import app.sctp.enrollment.models.EnrollmentSession;

public class EnrollmentSessionViewHolderCreator implements ItemViewHolderCreator<EnrollmentSession> {
    @Override
    public EnrollmentSessionViewHolder createViewHolder(ViewGroup parent, int viewType) {

        return new EnrollmentSessionViewHolder(
                ItemEnrollmentSessionBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }
}
