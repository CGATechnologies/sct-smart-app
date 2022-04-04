package app.sctp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import app.sctp.core.ui.BindableActivity;
import app.sctp.databinding.ActivityMainBinding;
import app.sctp.targeting.ui.activities.LocationSelectionActivity;
import app.sctp.utils.UiUtils;

public class MainActivity extends BindableActivity {
    private static final int LOCATION_SELECTION_REQUEST_ID = 1001;

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater) {
        return binding = ActivityMainBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.host_fragment);

        navController = navHostFragment.getNavController();

        checkForLocationSelection();
    }

    private void showLocationSelectionDialogPrompt() {
        UiUtils.dialogPrompt(this, R.string.location_selection_prompt2)
                .cancelable(false)
                .onOk(R.string.yes, (dialog, which) -> navigateToLocationSelection())
                .onCancel(R.string.later, (dialog, which) -> {
                    getApplicationConfiguration().sharedPreferenceAccessor()
                            .setSuppressInitialLocationPrompt(true);
                    UiUtils.messageDialog(MainActivity.this, R.string.location_selection_canceled)
                            .show();
                })
                .show();
    }

    private void navigateToLocationSelection() {
        LocationSelectionActivity.selectLocation(this, LOCATION_SELECTION_REQUEST_ID);
    }

    private void checkForLocationSelection() {
        if (!getApplicationConfiguration().sharedPreferenceAccessor().isLocationSelected()) {
            if (!getApplicationConfiguration().sharedPreferenceAccessor().getSuppressInitialLocationPrompt()) {
                navigateToLocationSelection();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == LOCATION_SELECTION_REQUEST_ID) {
            if (resultCode != RESULT_OK) {
                showLocationSelectionDialogPrompt();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
