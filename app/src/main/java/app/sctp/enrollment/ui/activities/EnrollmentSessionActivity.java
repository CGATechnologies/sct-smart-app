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
import app.sctp.core.ui.adapter.GenericPagedAdapter;
import app.sctp.databinding.ActivityEnrollmentLayoutBinding;
import app.sctp.databinding.ActivityHouseholdEligibilitySelectionBinding;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.TargetedHousehold;
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
    private GenericPagedAdapter<TargetedHousehold> householdAdapter;

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
                .when(R.id.enrollment_download_sessions, () -> downloadOptionsDialog.show(R.string.enrollment_sessions_download_prompt))
                .eval();
        return super.onOptionsItemSelected(item);
    }

    private void downloadSessions(DownloadOptionsDialog.DownloadOption downloadOption) {

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
