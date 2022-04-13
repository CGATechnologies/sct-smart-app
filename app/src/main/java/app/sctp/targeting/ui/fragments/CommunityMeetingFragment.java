package app.sctp.targeting.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import app.sctp.R;
import app.sctp.core.ui.BindableFragment;
import app.sctp.databinding.FragmentTargetingCommunityMeetingBinding;
import app.sctp.databinding.LocationInfoBinding;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.services.TargetingService;

public class CommunityMeetingFragment extends BindableFragment {

    private LocationSelection locationSelection;
    private LocationInfoBinding locationInfoBinding;
    private FragmentTargetingCommunityMeetingBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.community_meeting_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cmo_download_new_data) {
            downloadEligibilityVerificationSessions();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void downloadEligibilityVerificationSessions(){

    }
}
