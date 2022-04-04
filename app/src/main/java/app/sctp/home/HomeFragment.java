package app.sctp.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import app.sctp.BuildConfig;
import app.sctp.LoginActivity;
import app.sctp.MainActivity;
import app.sctp.R;
import app.sctp.StartupActivity;
import app.sctp.core.ui.BindableFragment;
import app.sctp.databinding.FragmentHomeBinding;
import app.sctp.targeting.ui.activities.LocationSelectionActivity;
import app.sctp.user.UserDetails;
import app.sctp.utils.UiUtils;

public class HomeFragment extends BindableFragment {
    private static final int REQUEST_ID_LOCATION_SELECTION = 1002;

    private FragmentHomeBinding binding;
    private NavController navController;

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return binding = FragmentHomeBinding.inflate(inflater);
    }

    @Override
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
    }

    private NavController getNavController() {
        if (navController != null) {
            return navController;
        }
        return navController = Navigation.findNavController(binding.getRoot());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_ID_LOCATION_SELECTION) {
            if (resultCode == Activity.RESULT_OK) {
                // TODO launch submodule selection screen
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void navigateToTargeting() {
        UserDetails userDetails = getApplicationConfiguration().getUserDetails();
        boolean locationSet = getApplicationConfiguration()
                .sharedPreferenceAccessor()
                .isLocationSelected();
        if (!locationSet) {
            LocationSelectionActivity.selectLocation(this, REQUEST_ID_LOCATION_SELECTION);
        }else{
            // target submodules selection
        }
    }

    private void navigateToAdministration() {

    }
}
