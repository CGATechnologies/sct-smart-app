package app.sctp.targeting.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavOptionsBuilder;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import app.sctp.R;
import app.sctp.core.ui.BindableFragment;
import app.sctp.databinding.FragmentTargetingBinding;

public class TargetingModuleFragment extends BindableFragment {

    private NavController navController;
    private FragmentTargetingBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.targetingDistrictMeetingCard.setOnClickListener(v -> navigateToDistrictMeeting());
        binding.targetingCommunityMeetingCard.setOnClickListener(v -> navigateToCommunityMeeting());

        navController = Navigation.findNavController(binding.getRoot());
    }

    private void navigateToDistrictMeeting() {
        navController.navigate(R.id.action_home_to_district_meeting);
    }

    private void navigateToCommunityMeeting() {
        navController.navigate(R.id.action_home_to_community_meeting);
    }

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return binding = FragmentTargetingBinding.inflate(inflater, container, false);
    }
}
