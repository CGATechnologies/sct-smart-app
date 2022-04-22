package app.sctp.targeting.ui.viewholders;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import app.sctp.core.ui.adapter.ItemViewHolder;
import app.sctp.core.ui.adapter.ItemViewHolderCreator;
import app.sctp.targeting.models.Household;

public class HouseholdViewHolderCreator implements ItemViewHolderCreator<Household> {
    @Override
    public ItemViewHolder<Household> createViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public static class HouseholdViewHolder extends ItemViewHolder<Household> {

        public HouseholdViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
