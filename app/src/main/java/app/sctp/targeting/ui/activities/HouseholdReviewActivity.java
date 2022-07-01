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
import app.sctp.targeting.models.SelectionStatus;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.targeting.models.TargetingSession;
import app.sctp.targeting.viewmodels.HouseholdViewModel;
import app.sctp.utils.UiUtils;

public class HouseholdReviewActivity extends BaseActivity {
    private static final String KEY_SESSION = "session";
    private static final String KEY_HOUSEHOLD = "sctp.targeting.pev.session.household";

    private TargetedHousehold household;
    private HouseholdViewModel householdViewModel;
    private ArrayAdapter<SelectionStatus> spinnerAdapter;
    private ActivityTargetingHouseholdSelectionBinding binding;

    private TargetingSession targetingSession;

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
        targetingSession = (TargetingSession) getIntent().getSerializableExtra(KEY_SESSION);

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
        binding.selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                household.setStatus(spinnerAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnComposition.setOnClickListener(v -> HouseholdMemberListActivity.viewHouseholdMembers(this, household));
        binding.btnSave.setOnClickListener(v -> {
            /*Integer newRank = UiUtils.getInteger(binding.newRank, -1);

            if(newRank > 0){
                household.setRanking(UiUtils.getInteger(binding.newRank, household.getRanking()));

            }*/
            household.setRanking(UiUtils.getInteger(binding.newRank, household.getRanking()));
            household.setChangeReason("Change me!!");
            householdViewModel.update(household);
            UiUtils.toast(HouseholdReviewActivity.this, R.string.updates_saved);
            setResult(RESULT_OK);
            finish();
        });
    }

    public static void reviewHousehold(Activity activity, TargetedHousehold household, TargetingSession session, int requestCode) {
        activity.startActivityForResult(new Intent(activity, HouseholdReviewActivity.class)
                        .putExtra(KEY_HOUSEHOLD, household)
                        .putExtra(KEY_SESSION, session)
                , requestCode);
    }
}
