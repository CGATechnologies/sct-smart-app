package app.sctp.targeting.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import app.sctp.R;
import app.sctp.core.ui.BaseActivity;
import app.sctp.databinding.ActivityTargetingHouseholdSelectionBinding;
import app.sctp.targeting.models.Household;
import app.sctp.targeting.models.SelectionStatus;
import app.sctp.targeting.viewmodels.HouseholdViewModel;
import app.sctp.utils.UiUtils;

public class HouseholdReviewActivity extends BaseActivity {
    private static final String KEY_HOUSEHOLD = "sctp.targeting.pev.session.household";

    private Household household;
    private ArrayAdapter<SelectionStatus> spinnerAdapter;
    private HouseholdViewModel householdViewModel;
    private ActivityTargetingHouseholdSelectionBinding binding;
    private static final SelectionStatus[] SELECTION_STATUSES = {
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

        household = (Household) getIntent().getSerializableExtra(KEY_HOUSEHOLD);
        binding.getRoot().post(() -> binding.setHousehold(household));
        householdViewModel = getViewModel(HouseholdViewModel.class);

        spinnerAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                SELECTION_STATUSES
        );
        binding.selection.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.selection.setSelection(UiUtils.itemAdapterPosition(spinnerAdapter, household.getSelection()));
        binding.selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                household.setSelection(spinnerAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnComposition.setOnClickListener(v -> HouseholdMemberListActivity.viewHouseholdMembers(this, household));
        binding.btnSave.setOnClickListener(v -> {
            household.setRanking(UiUtils.getInteger(binding.newRank, household.getRanking()));
            householdViewModel.save(household);
            UiUtils.toast(HouseholdReviewActivity.this, R.string.updates_saved);
            setResult(RESULT_OK);
            finish();
        });
    }

    public static void reviewHousehold(Activity activity, Household household, int requestCode) {
        activity.startActivityForResult(new Intent(activity, HouseholdReviewActivity.class)
                .putExtra(KEY_HOUSEHOLD, household), requestCode);
    }
}
