package app.sctp.targeting.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import app.sctp.R;
import app.sctp.core.ui.BaseActivity;
import app.sctp.core.ui.adapter.GenericPagedAdapter;
import app.sctp.core.ui.adapter.ItemSelectionListener;
import app.sctp.databinding.ActivityHouseholdEligibilitySelectionBinding;
import app.sctp.targeting.models.HouseholdSelectionResults;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.targeting.models.TargetingSession;
import app.sctp.targeting.models.UpdateHouseholdRankRequest;
import app.sctp.targeting.services.TargetingService;
import app.sctp.targeting.ui.viewholders.HouseholdViewHolderCreator;
import app.sctp.targeting.viewmodels.HouseholdViewModel;
import app.sctp.utils.PlatformUtils;
import app.sctp.utils.UiUtils;
import retrofit2.HttpException;
import retrofit2.Response;

public class TargetingSessionActivity extends BaseActivity {
    private static final String KEY_SESSION = "sctp.targeting.pev.session";
    private static final int REVIEW_HOUSEHOLD_REQUEST_ID = 1001;

    private ProgressDialog progressDialog;
    private HouseholdViewModel householdViewModel;
    private TargetingSession session;
    private GenericPagedAdapter<TargetedHousehold> householdAdapter;
    private ActivityHouseholdEligibilitySelectionBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHouseholdEligibilitySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolBar();
        setSubTitle(getText(R.string.community_meeting_pev_results));

        session = (TargetingSession) getIntent().getSerializableExtra(KEY_SESSION);

        householdAdapter = new GenericPagedAdapter<>(new HouseholdViewHolderCreator());
        householdAdapter.setItemSelectionListener(new ItemSelectionListener<TargetedHousehold>() {
            @Override
            public void onItemSelected(TargetedHousehold item) {
                HouseholdReviewActivity.reviewHousehold(
                        TargetingSessionActivity.this,
                        item,
                        REVIEW_HOUSEHOLD_REQUEST_ID
                );
            }

            @Override
            public void onItemLongSelected(TargetedHousehold item) {

            }
        });
        binding.list.setAdapter(householdAdapter);

        householdViewModel = getViewModel(HouseholdViewModel.class);
/*
        householdViewModel.getHouseholdsLiveData().observe(this, householdAdapter::submitList);
        householdViewModel.updateFilterParameters(session.getId(), null);*/
        /*householdViewModel.getSessionHouseholds().observe(this, householdAdapter::submitList);
        householdViewModel.setSessionId(session.getId());*/
        //refreshHouseholds();

        progressDialog = UiUtils.progressDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshHouseholds();
    }

    private void refreshHouseholds() {
        // TODO Fix LiveDate update
        // noinspection NotifyDatasetChanged
        householdViewModel.getSessionHouseholds(session.getId()).observe(this, householdAdapter::submitList);
    }

    public static void selectEligibleHouseholds(Activity activity, TargetingSession session) {
        activity.startActivity(new Intent(activity, TargetingSessionActivity.class)
                .putExtra(KEY_SESSION, session));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REVIEW_HOUSEHOLD_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                refreshHouseholds();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.household_selection_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cmo_send_households) {
            synchronizeHouseholds();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void synchronizeHouseholds() {
        progressDialog.setMessage("Processing...");
        progressDialog.show();
        getApplicationConfiguration().postBackgroundWork(() -> {
            progressDialog.setMessage("Uploading selection results...");

            // TODO Make the requests to the server are batched
            // https://github.com/CGATechnologies/sct-smart-app/issues/1
            List<HouseholdSelectionResults> resultsList = householdViewModel
                    .getHouseholdSelectionResultsForSession(session.getId());

            UpdateHouseholdRankRequest request = new UpdateHouseholdRankRequest(resultsList);

            Response<Void> response;
            AtomicBoolean success = new AtomicBoolean(false);

            try {
                response = getApplicationConfiguration()
                        .getService(TargetingService.class)
                        .uploadHouseholdSelectionResults(session.getId(), request).execute();
                if (response.isSuccessful()) {
                    success.set(true);
                    // TODO Set session as Closed ?
                } else {
                    throw new HttpException(response);
                }
            } catch (Exception e) {
                PlatformUtils.printStackTrace(e);
            } finally {
                runOnUiThread(() -> {
                    progressDialog.dismiss();
                    if (success.get()) {
                        UiUtils.snackbar(binding.getRoot(), R.string.household_updates_sent);
                    } else {
                        UiUtils.snackbar(binding.getRoot(), R.string.household_updates_failed, true);
                    }
                });
            }
        });
    }
}
