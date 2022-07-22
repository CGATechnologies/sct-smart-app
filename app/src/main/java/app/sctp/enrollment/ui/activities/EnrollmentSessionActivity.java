package app.sctp.enrollment.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import app.sctp.R;
import app.sctp.core.ui.BaseActivity;
import app.sctp.core.ui.adapter.GenericAdapter;
import app.sctp.core.ui.adapter.GenericPagedAdapter;
import app.sctp.databinding.ActivityEnrollmentLayoutBinding;
import app.sctp.enrollment.models.EnrollmentHousehold;
import app.sctp.enrollment.models.EnrollmentSession;
import app.sctp.enrollment.repositories.EnrollmentSessionRepository;
import app.sctp.enrollment.services.EnrollmentService;
import app.sctp.enrollment.ui.viewholders.EnrollmentSessionViewHolderCreator;
import app.sctp.enrollment.viewmodels.EnrollmentSessionViewModel;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.utils.DownloadOptionsDialog;
import app.sctp.utils.LocationInfoDialog;
import app.sctp.utils.PlatformUtils;
import app.sctp.utils.UiUtils;

public class EnrollmentSessionActivity extends BaseActivity {

    private String districtName;
    private ProgressDialog progressDialog;
    private LocationSelection locationSelection;
    private LocationInfoDialog locationInfoDialog;
    private ActivityEnrollmentLayoutBinding binding;
    private DownloadOptionsDialog downloadOptionsDialog;
    private GenericPagedAdapter<EnrollmentHousehold> householdAdapter;

    private GenericAdapter<EnrollmentSession> sessionAdapter;
    private EnrollmentSessionViewModel sessionViewModel;  //  TODO remove
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnrollmentLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolBar();
        setSubTitle(getText(R.string.label_targeting_and_enrollment));

        locationSelection = (LocationSelection) getIntent().getSerializableExtra("location");

        locationInfoDialog = new LocationInfoDialog(this);
        progressDialog = UiUtils.progressDialogWithProgress(this);
        districtName = getApplicationConfiguration().getUserDetails().getDistrictName();
        downloadOptionsDialog = new DownloadOptionsDialog(this, (dlg, downloadOption) -> downloadSessions(downloadOption));

        sessionViewModel = getViewModel(EnrollmentSessionViewModel.class);  // TODO remove

        sessionAdapter = new GenericAdapter<EnrollmentSession>(new EnrollmentSessionViewHolderCreator());
        sessionViewModel.getEnrollmentSessions(locationSelection)
                .observe(this, sessionAdapter::submitList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.enrollment_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        PlatformUtils.dynamicSwitchOn(item.getItemId())
                .when(R.id.enrollment_location_info, this::showLocationInfo)
                .when(R.id.enrollment_download_sessions, () -> downloadOptionsDialog.show())
                .eval();
        return super.onOptionsItemSelected(item);
    }

    private void downloadSessions(DownloadOptionsDialog.DownloadOption downloadOption) {
        sessionViewModel.downloadEnrollmentSessions(
                locationSelection,
                getApplicationConfiguration().getService(EnrollmentService.class),
                new EnrollmentSessionRepository.SessionDownloadListener() {
                    @Override
                    public void onStart() {
                        progressDialog.setIndeterminate(false);
                        progressDialog.setMessage("Preparing...");
                        progressDialog.show();
                    }

                    @Override
                    public void onProgressTotalAvailable(int total) {
                        progressDialog.setIndeterminate(false);
                        progressDialog.setMax(total);
                        progressDialog.setProgress(0);
                    }

                    @Override
                    public void onCompleted() {
                        progressDialog.dismiss();
                        UiUtils.snackbar(binding.getRoot(), R.string.download_successful);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressDialog.dismiss();
                        UiUtils.snackbar(binding.getRoot(), R.string.error_downloading_data);
                    }

                    @Override
                    public void onMessage(String message) {
                        progressDialog.setMessage(message);
                    }

                    @Override
                    public void onProgress(int progress, int total) {
                        progressDialog.setProgress(progress);
                    }
                },
                downloadOption
        );
    }

    private void showLocationInfo() {
        locationInfoDialog.show(districtName, locationSelection);
    }

    public static void manageSessions(Context context, LocationSelection location) {
        context.startActivity(
                new Intent(context, EnrollmentSessionActivity.class)
                        .putExtra("location", location)
        );
    }
}
