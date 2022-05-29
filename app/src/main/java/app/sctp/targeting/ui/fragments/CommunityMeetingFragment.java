package app.sctp.targeting.ui.fragments;

import android.app.ProgressDialog;
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
import app.sctp.core.ui.adapter.GenericAdapter;
import app.sctp.core.ui.adapter.ItemSelectionListener;
import app.sctp.databinding.FragmentTargetingCommunityMeetingBinding;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.TargetingSession;
import app.sctp.targeting.repositories.TargetingSessionRepository;
import app.sctp.targeting.services.TargetingService;
import app.sctp.targeting.ui.activities.TargetingSessionActivity;
import app.sctp.targeting.ui.viewholders.TargetingSessionViewHolderCreator;
import app.sctp.targeting.viewmodels.HouseholdViewModel;
import app.sctp.targeting.viewmodels.IndividualViewModel;
import app.sctp.targeting.viewmodels.TargetingSessionViewModel;
import app.sctp.utils.DownloadOptionsDialog;
import app.sctp.utils.UiUtils;

public class CommunityMeetingFragment extends BindableFragment {

    private ProgressDialog progressDialog;
    private LocationSelection locationSelection;
    private HouseholdViewModel householdViewModel;
    private IndividualViewModel individualViewModel;
    private TargetingSessionViewModel sessionViewModel;
    private DownloadOptionsDialog downloadOptionsDialog;
    private GenericAdapter<TargetingSession> sessionAdapter;
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

        progressDialog = UiUtils.progressDialogWithProgress(requireContext());

        sessionAdapter = new GenericAdapter<>(new TargetingSessionViewHolderCreator());
        sessionAdapter.setItemSelectionListener(new ItemSelectionListener<TargetingSession>() {
            @Override
            public void onItemSelected(TargetingSession session) {
                TargetingSessionActivity.selectEligibleHouseholds(
                        requireActivity(),
                        session
                );
            }

            @Override
            public void onItemLongSelected(TargetingSession item) {

            }
        });

        downloadOptionsDialog = new DownloadOptionsDialog(
                requireContext(),
                (dlg, downloadOption) -> downloadTargetingSessions(downloadOption)
        );

        householdViewModel = getViewModel(HouseholdViewModel.class);
        individualViewModel = getViewModel(IndividualViewModel.class);
        sessionViewModel = getViewModel(TargetingSessionViewModel.class);

        binding.list.setAdapter(sessionAdapter);

        loadSessions();
    }

    private void loadSessions() {
        sessionViewModel.get2ndCommunityMeetingTargetingSessions(locationSelection)
                .observe(requireActivity(), sessionAdapter::submitList);
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
            downloadOptionsDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void downloadTargetingSessions(DownloadOptionsDialog.DownloadOption downloadOption) {
        sessionViewModel.downloadTargetingSessions(
                locationSelection,
                TargetingSession.MeetingPhase.second_community_meeting,
                getService(TargetingService.class),
                new TargetingSessionRepository.SessionDownloadListener() {
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
}
