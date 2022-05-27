package app.sctp.targeting.ui.viewholders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.core.ui.adapter.ItemAdapter;
import app.sctp.core.ui.adapter.ItemViewHolder;
import app.sctp.core.ui.adapter.ItemViewHolderCreator;
import app.sctp.databinding.ItemHouseholdBinding;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.utils.PlatformUtils;

public class HouseholdViewHolderCreator implements ItemViewHolderCreator<TargetedHousehold> {
    @Override
    public ItemViewHolder<TargetedHousehold> createViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new HouseholdViewHolder(ItemHouseholdBinding.inflate(inflater, parent, false));
    }

    public static class HouseholdViewHolder extends ItemViewHolder<TargetedHousehold> {
        private final ItemHouseholdBinding binding;

        public HouseholdViewHolder(@NonNull ItemHouseholdBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        protected void bind(ItemAdapter<TargetedHousehold> adapter, int position, List<Object> changePayload) {
            PlatformUtils.debugLog("hh %s@%d ", adapter.getItemAt(position), position);
            binding.setHousehold(adapter.getItemAt(position));
        }
    }
}
