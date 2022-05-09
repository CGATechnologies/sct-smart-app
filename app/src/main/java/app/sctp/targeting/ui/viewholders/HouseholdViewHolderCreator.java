package app.sctp.targeting.ui.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.core.ui.adapter.ItemAdapter;
import app.sctp.core.ui.adapter.ItemViewHolder;
import app.sctp.core.ui.adapter.ItemViewHolderCreator;
import app.sctp.databinding.ItemHouseholdBinding;
import app.sctp.targeting.models.Household;

public class HouseholdViewHolderCreator implements ItemViewHolderCreator<Household> {
    @Override
    public ItemViewHolder<Household> createViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new HouseholdViewHolder(ItemHouseholdBinding.inflate(inflater, parent, false));
    }

    public static class HouseholdViewHolder extends ItemViewHolder<Household> {
        private ItemHouseholdBinding binding;

        public HouseholdViewHolder(@NonNull ItemHouseholdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        protected void bind(ItemAdapter<Household> adapter, int position, List<Object> changePayload) {
            binding.setHousehold(adapter.getItemAt(position));
        }
    }
}
