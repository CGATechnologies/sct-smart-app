package app.sctp.targeting.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import app.sctp.R;
import app.sctp.core.ui.BindableActivity;
import app.sctp.databinding.ActivityTargetingModuleBinding;
import app.sctp.user.UserDetails;

public class TargetingModuleActivity extends BindableActivity {
    private static final int REQUEST_ID_LOCATION_SELECTION = 1002;

    private NavController navController;
    private ActivityTargetingModuleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolBar();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.host_fragment);

        //noinspection ConstantConditions
        navController = navHostFragment.getNavController();

        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> setSubTitle(navDestination.getLabel()));
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, TargetingModuleActivity.class));
    }

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater) {
        return binding = ActivityTargetingModuleBinding.inflate(inflater);
    }

    /*@Override
    protected void initializeComponents(Bundle savedInstance) {
        binding.info.setText(format("v%s", BuildConfig.VERSION_NAME));
        binding.targetingCard.setOnClickListener(v -> navigateToTargeting());
        binding.administrationCard.setOnClickListener(v -> navigateToAdministration());
        binding.logoutButton.setOnClickListener(
                v -> UiUtils.dialogPrompt(requireContext(), R.string.logout_prompt)
                        .onOk((dialog, which) -> {
                            getApplicationConfiguration().sharedPreferenceAccessor()
                                    .setAccessToken(null);
                            getActivity().finishAffinity();
                            LoginActivity.launch(requireContext());
                        }).onCancel((dialog, which) -> dialog.cancel()).show()
        );

        UserDetails userDetails = getApplicationConfiguration().getUserDetails();
        binding.name.setText(userDetails.fullName());
        binding.district.setText(format("%s district", userDetails.getDistrictName()));
    }*/

    private void navigateToTargeting() {
        UserDetails userDetails = getApplicationConfiguration().getUserDetails();
        boolean locationSet = getApplicationConfiguration()
                .sharedPreferenceAccessor()
                .isLocationSelected();
        if (!locationSet) {
            LocationSelectionActivity.selectLocation(this, REQUEST_ID_LOCATION_SELECTION);
        } else {
            // target submodules selection
        }
    }

    private void navigateToAdministration() {

    }
}
