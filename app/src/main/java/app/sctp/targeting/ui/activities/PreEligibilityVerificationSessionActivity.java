package app.sctp.targeting.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import app.sctp.R;
import app.sctp.core.ui.BaseActivity;
import app.sctp.core.ui.adapter.GenericPagedAdapter;
import app.sctp.databinding.ActivityHouseholdEligibilitySelectionBinding;
import app.sctp.targeting.models.Household;
import app.sctp.targeting.models.PreEligibilityVerificationSession;
import app.sctp.targeting.ui.viewholders.HouseholdViewHolderCreator;
import app.sctp.targeting.viewmodels.HouseholdViewModel;

public class PreEligibilityVerificationSessionActivity extends BaseActivity {
    private static final String KEY_SESSION = "sctp.targeting.pev.session";

    private HouseholdViewModel householdViewModel;
    private PreEligibilityVerificationSession session;
    private GenericPagedAdapter<Household> householdAdapter;
    private ActivityHouseholdEligibilitySelectionBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHouseholdEligibilitySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolBar();
        setSubTitle(getText(R.string.community_meeting_pev_results));

        session = (PreEligibilityVerificationSession) getIntent().getSerializableExtra(KEY_SESSION);

        householdAdapter = new GenericPagedAdapter<>(new HouseholdViewHolderCreator());
        binding.list.setAdapter(householdAdapter);

        householdViewModel = getViewModel(HouseholdViewModel.class);

        householdViewModel.getHouseholdsLiveData().observe(this, householdAdapter::submitList);
        //householdViewModel.updateFilterParameters(session.getId(), null);
    }

    public static void selectEligibleHouseholds(Activity activity, PreEligibilityVerificationSession session) {
        activity.startActivity(new Intent(activity, PreEligibilityVerificationSessionActivity.class)
                .putExtra(KEY_SESSION, session));
    }
}
