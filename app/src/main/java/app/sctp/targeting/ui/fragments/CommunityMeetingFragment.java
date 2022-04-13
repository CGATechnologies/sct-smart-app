package app.sctp.targeting.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import java.util.Objects;

import app.sctp.core.ui.BindableFragment;
import app.sctp.core.ui.views.ExpandablePanel;
import app.sctp.databinding.FragmentTargetingCommunityMeetingBinding;
import app.sctp.databinding.LocationInfoBinding;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.ui.activities.LocationSelectionActivity;

public class CommunityMeetingFragment extends BindableFragment {

    private LocationSelection locationSelection;
    private LocationInfoBinding locationInfoBinding;
    private FragmentTargetingCommunityMeetingBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationSelection = CommunityMeetingFragmentArgs.fromBundle(getArguments())
                .getSelectedLocation();
        locationInfoBinding = binding.locationInformation
                .getContentViewBinding(LocationInfoBinding::bind);
        locationInfoBinding.setLocation(locationSelection);
    }

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return binding = FragmentTargetingCommunityMeetingBinding.inflate(inflater, container, false);
    }
}
