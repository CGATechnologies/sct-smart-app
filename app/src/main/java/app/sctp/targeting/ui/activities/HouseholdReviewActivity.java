package app.sctp.targeting.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import app.sctp.R;
import app.sctp.core.ui.BaseActivity;
import app.sctp.databinding.ActivityTargetingHouseholdSelectionBinding;
import app.sctp.targeting.models.SelectionStatus;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.targeting.viewmodels.HouseholdViewModel;
import app.sctp.utils.UiUtils;

public class HouseholdReviewActivity extends BaseActivity {
    private static final String KEY_HOUSEHOLD = "sctp.targeting.pev.session.household";

    private TargetedHousehold household;
    private ArrayAdapter<SelectionStatus> spinnerAdapter;
    private HouseholdViewModel householdViewModel;
    private ActivityTargetingHouseholdSelectionBinding binding;
    private static final SelectionStatus[] SELECTION_STATUSES = {
            SelectionStatus.PreEligible,
            SelectionStatus.Eligible,
            SelectionStatus.Ineligible
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTargetingHouseholdSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolBar();
        setSubTitle(getText(R.string.title_household_review));

        household = (TargetedHousehold) getIntent().getSerializableExtra(KEY_HOUSEHOLD);
        binding.getRoot().post(() -> binding.setHousehold(household));
        householdViewModel = getViewModel(HouseholdViewModel.class);

        spinnerAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                SELECTION_STATUSES
        );
        binding.selection.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.selection.setSelection(UiUtils.itemAdapterPosition(spinnerAdapter, household.getStatus()));
        binding.btnComposition.setOnClickListener(v -> HouseholdMemberListActivity.viewHouseholdMembers(this, household));
        binding.btnSaveRank.setOnClickListener(view -> {
            household.setRanking(UiUtils.getInteger(binding.newRank, household.getRanking()));
            household.setRankChangeReason(UiUtils.getNonEmptyText(binding.rankChangeReason));
            if (household.getRanking() == null || household.getRankChangeReason() == null){
                return;
            }
            householdViewModel.update(household);
            UiUtils.toast(HouseholdReviewActivity.this, R.string.updates_saved);
            setResult(RESULT_OK);
            finish();
        });
        binding.btnSaveStatus.setOnClickListener(view -> {
            household.setStatus(spinnerAdapter.getItem(binding.selection.getSelectedItemPosition()));
            household.setStatusChangeReason(UiUtils.getNonEmptyText(binding.statusChangeReason));
            if (household.getStatus() == null || household.getStatusChangeReason() == null){
                return;
            }
            householdViewModel.update(household);
            UiUtils.toast(HouseholdReviewActivity.this, R.string.updates_saved);
            setResult(RESULT_OK);
            finish();
        });
    }

    public static void reviewHousehold(Activity activity, TargetedHousehold household, int requestCode) {
        activity.startActivityForResult(new Intent(activity, HouseholdReviewActivity.class)
                .putExtra(KEY_HOUSEHOLD, household), requestCode);
    }
}
