package app.sctp.targeting.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import app.sctp.R;
import app.sctp.core.ui.BaseActivity;
import app.sctp.core.ui.adapter.GenericPagedAdapter;
import app.sctp.core.ui.adapter.ItemSelectionListener;
import app.sctp.databinding.ActivityHouseholdEligibilitySelectionBinding;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.targeting.models.TargetingSession;
import app.sctp.targeting.repositories.HouseholdRepository;
import app.sctp.targeting.services.TargetingService;
import app.sctp.targeting.ui.viewholders.HouseholdViewHolderCreator;
import app.sctp.targeting.viewmodels.HouseholdViewModel;
import app.sctp.targeting.viewmodels.TargetingSessionViewModel;
import app.sctp.utils.PlatformUtils;
import app.sctp.utils.UiUtils;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class TargetingSessionActivity extends BaseActivity {
    private static final String KEY_SESSION = "sctp.targeting.pev.session";
    private static final int REVIEW_HOUSEHOLD_REQUEST_ID = 1001;

    private TargetingSession session;
    private ProgressDialog progressDialog;
    private HouseholdViewModel householdViewModel;
    private TargetingSessionViewModel sessionViewModel;
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
        sessionViewModel = getViewModel(TargetingSessionViewModel.class);
/*
        householdViewModel.getHouseholdsLiveData().observe(this, householdAdapter::submitList);
        householdViewModel.updateFilterParameters(session.getId(), null);*/
        /*householdViewModel.getSessionHouseholds().observe(this, householdAdapter::submitList);
        householdViewModel.setSessionId(session.getId());*/
        //refreshHouseholds();
        householdViewModel.getSessionHouseholds(session.getId()).observe(this, householdAdapter::submitList);

        progressDialog = UiUtils.progressDialogWithProgress(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshHouseholds();
    }

    private void refreshHouseholds() {
        // TODO Fix LiveDate update
        // noinspection NotifyDatasetChanged
        //householdViewModel.getSessionHouseholds(session.getId()).observe(this, householdAdapter::submitList);
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
            // TODO Show dialog to ask whether to move to next phase or not
            synchronizeHouseholds(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param moveToNextPhase whether to mark the session as being complete in the current phase.
     */
    private void synchronizeHouseholds(boolean moveToNextPhase) {
        TargetingService service = getApplicationConfiguration().getService(TargetingService.class);
        householdViewModel.synchronizeHouseholds(
                session.getId(),
                service,
                session.getMeetingPhase(),
                new HouseholdRepository.SyncListener() {
                    @Override
                    public void onMessage(String message) {
                        progressDialog.setMessage(message);
                    }

                    @Override
                    public void onComplete() {
                        if (moveToNextPhase) {
                            markSessionMeetingAsDone(service);
                        } else {
                            progressDialog.dismiss();
                            UiUtils.snackbar(binding.getRoot(), R.string.household_updates_sent);
                        }
                    }

                    @Override
                    public void onStart() {
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
                    }

                    @Override
                    public void onError(Exception exception) {
                        progressDialog.dismiss();
                        UiUtils.snackbar(binding.getRoot(), R.string.household_updates_failed, true);
                    }

                    @Override
                    public void onInitializeProgress(int max) {
                        progressDialog.setMax(max);
                    }

                    @Override
                    public void onProgress(int progress) {
                        progressDialog.setProgress(progress);
                    }
                }
        );
    }

    private void markSessionMeetingAsDone(TargetingService service) {
        getApplicationConfiguration().postBackgroundWork(() -> {
            try {
                Call<Void> call;
                Response<Void> response;

                switch (session.getMeetingPhase()) {
                    case second_community_meeting:
                        call = service.markSessionSecondCommunityMeetingAsDone(session.getId());
                        break;
                    case district_meeting:
                        call = service.markSessionDistrictMeetingAsDone(session.getId());
                        break;
                    default:
                        throw new UnsupportedOperationException("operation not unsupported for " + session.getMeetingPhase());
                }

                response = call.execute();
                if (!response.isSuccessful()) {
                    throw new HttpException(response);
                }

                // move session to next phase
                session.setMeetingPhase(session.getMeetingPhase().next());
                sessionViewModel.update(session);

                progressDialog.dismiss();
                UiUtils.snackbar(binding.getRoot(), R.string.household_updates_sent);

            } catch (Exception e) {
                PlatformUtils.printStackTrace(e);
                progressDialog.dismiss();
                UiUtils.snackbar(binding.getRoot(), R.string.session_meeting_close_error, true);
            }
        });
    }
}
