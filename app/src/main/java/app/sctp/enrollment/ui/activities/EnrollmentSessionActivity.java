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
import app.sctp.databinding.ActivityHouseholdEligibilitySelectionBinding;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.utils.LocationInfoDialog;
import app.sctp.utils.PlatformUtils;

public class EnrollmentSessionActivity extends BaseActivity {
    private static final String KEY_SESSION = "sctp.enrollment.session";
    private static final int REVIEW_HOUSEHOLD_REQUEST_ID = 1001;


    private String districtName;
    private ProgressDialog progressDialog;
    private LocationSelection locationSelection;
    private LocationInfoDialog locationInfoDialog;
    private ActivityHouseholdEligibilitySelectionBinding binding;
    private GenericPagedAdapter<TargetedHousehold> householdAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHouseholdEligibilitySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolBar();
        setSubTitle(getText(R.string.enrollment));

        locationSelection = (LocationSelection) getIntent().getSerializableExtra("location");

        districtName = getApplicationConfiguration().getUserDetails().getDistrictName();
        locationInfoDialog = new LocationInfoDialog(this);
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
                .when(R.id.enrollment_download_sessions, this::downloadSessions)
                .eval();
        return super.onOptionsItemSelected(item);
    }

    private void downloadSessions() {

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
