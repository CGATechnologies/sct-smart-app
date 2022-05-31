package app.sctp.targeting.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import app.sctp.R;
import app.sctp.core.ui.BindableFragment;
import app.sctp.databinding.FragmentTargetingBinding;
import app.sctp.enrollment.ui.activities.EnrollmentSessionActivity;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.ui.activities.LocationSelectionActivity;

public class TargetingModuleFragment extends BindableFragment {
    private static final int ENROLLMENT_LOCATION_SELECTION_REQUEST_CODE = 1003;
    private static final int DISTRICT_MEETING_LOCATION_SELECTION_REQUEST_CODE = 1002;
    private static final int COMMUNITY_MEETING_LOCATION_SELECTION_REQUEST_CODE = 1001;

    private NavController navController;
    private FragmentTargetingBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.targetingDistrictMeetingCard.setOnClickListener(
                v -> selectLocation(DISTRICT_MEETING_LOCATION_SELECTION_REQUEST_CODE)
        );
        binding.targetingCommunityMeetingCard.setOnClickListener(
                v -> selectLocation(COMMUNITY_MEETING_LOCATION_SELECTION_REQUEST_CODE)
        );
        binding.targetingEnrollmentCard.setOnClickListener(
                v -> selectLocation(ENROLLMENT_LOCATION_SELECTION_REQUEST_CODE)
        );

        navController = Navigation.findNavController(binding.getRoot());
    }

    private void navigateToDistrictMeeting(LocationSelection location) {
        DistrictMeetingFragmentArgs args = new DistrictMeetingFragmentArgs.Builder(location)
                .build();
        navController.navigate(R.id.action_home_to_district_meeting, args.toBundle());
    }

    private void navigateToCommunityMeeting(LocationSelection location) {
        CommunityMeetingFragmentArgs args = new CommunityMeetingFragmentArgs.Builder(location)
                .build();
        navController.navigate(R.id.action_home_to_community_meeting, args.toBundle());
    }

    private void navigateToEnrollment(LocationSelection location) {
        EnrollmentSessionActivity.manageSessions(requireActivity(), location);
    }

    private void selectLocation(int requestCode) {
        LocationSelectionActivity.selectLocation(this, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == COMMUNITY_MEETING_LOCATION_SELECTION_REQUEST_CODE) {
                navigateToCommunityMeeting(LocationSelectionActivity.getSelectedLocation(data));
            } else if (requestCode == DISTRICT_MEETING_LOCATION_SELECTION_REQUEST_CODE) {
                navigateToDistrictMeeting(LocationSelectionActivity.getSelectedLocation(data));
            } else if (requestCode == ENROLLMENT_LOCATION_SELECTION_REQUEST_CODE) {
                navigateToEnrollment(LocationSelectionActivity.getSelectedLocation(data));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return binding = FragmentTargetingBinding.inflate(inflater, container, false);
    }
}
