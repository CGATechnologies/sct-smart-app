package app.sctp.targeting.ui.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.core.ui.adapter.ItemAdapter;
import app.sctp.core.ui.adapter.ItemViewHolder;
import app.sctp.core.ui.adapter.ItemViewHolderCreator;
import app.sctp.databinding.ItemHouseholdMemberBinding;
import app.sctp.targeting.models.Individual;

public class IndividualViewHolderCreator implements ItemViewHolderCreator<Individual> {
    @Override
    public ItemViewHolder<Individual> createViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new IndividualViewHolder(ItemHouseholdMemberBinding.inflate(inflater, parent, false));
    }

    public static class IndividualViewHolder extends ItemViewHolder<Individual> {
        private final ItemHouseholdMemberBinding binding;

        public IndividualViewHolder(@NonNull ItemHouseholdMemberBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        protected void bind(ItemAdapter<Individual> adapter, int position, List<Object> changePayload) {
            binding.setIndividual(adapter.getItemAt(position));
        }
    }
}
