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

import java.util.List;

import app.sctp.R;
import app.sctp.core.net.api.ErrorResponse;
import app.sctp.core.net.api.ext.ApiErrorCallback;
import app.sctp.core.net.api.ext.ApiStatusCallback;
import app.sctp.core.net.api.ext.NotificationCallback;
import app.sctp.core.ui.BindableFragment;
import app.sctp.core.ui.adapter.GenericAdapter;
import app.sctp.databinding.FragmentTargetingCommunityMeetingBinding;
import app.sctp.databinding.LocationInfoBinding;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.HouseholdDetailResponse;
import app.sctp.targeting.models.HouseholdDetails;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.PreEligibilityVerificationSession;
import app.sctp.targeting.models.PreEligibilityVerificationSessionResponse;
import app.sctp.targeting.services.TargetingService;
import app.sctp.targeting.ui.viewholders.PreEligibilityVerificationSessionViewHolderCreator;
import app.sctp.targeting.viewmodels.HouseholdViewModel;
import app.sctp.targeting.viewmodels.IndividualViewModel;
import app.sctp.targeting.viewmodels.PreEligibilityVerificationSessionViewModel;
import app.sctp.utils.UiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class CommunityMeetingFragment extends BindableFragment {

    private ProgressDialog progressDialog;
    private LocationSelection locationSelection;
    private HouseholdViewModel householdViewModel;
    private LocationInfoBinding locationInfoBinding;
    private IndividualViewModel individualViewModel;
    private FragmentTargetingCommunityMeetingBinding binding;
    private PreEligibilityVerificationSessionViewModel sessionViewModel;
    private GenericAdapter<PreEligibilityVerificationSession> sessionAdapter;

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

        progressDialog = UiUtils.progressDialog(requireContext());

        sessionAdapter = new GenericAdapter<>(new PreEligibilityVerificationSessionViewHolderCreator());

        householdViewModel = getViewModel(HouseholdViewModel.class);
        individualViewModel = getViewModel(IndividualViewModel.class);
        sessionViewModel = getViewModel(PreEligibilityVerificationSessionViewModel.class);

        binding.list.setAdapter(sessionAdapter);

        loadSessions();
    }

    private void loadSessions() {
        sessionViewModel.getAll(locationSelection)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<PreEligibilityVerificationSession>>() {
                    @Override
                    public void onNext(List<PreEligibilityVerificationSession> sessions) {
                        sessionAdapter.submitList(sessions);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        dispose();
                    }
                });
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

    private void downloadEligibilityVerificationSessions() {
        int currentPage = 0;
        getService(TargetingService.class)
                .getPreEligibilitySessions(
                        locationCode(locationSelection.getTraditionalAuthority())
                        , locationCode(locationSelection.getCluster())
                        , locationCode(locationSelection.getZone())
                        , locationCode(locationSelection.getVillage())
                        , currentPage
                )
                .beforeCall(() -> progressDialog.show())
                .afterCall(() -> progressDialog.dismiss())
                .onSuccess(data -> {
                    sessionViewModel.save(data.getSessions());
                    UiUtils.toast(requireContext(), R.string.download_successful);
                })
                .onError(data -> UiUtils.snackbar(binding.getRoot(), R.string.error_downloading_pev_sessions))
                .execute();
    }

    private void downloadSessions(int page) {
        progressDialog.setMessage("Downloading sessions...");
        getService(TargetingService.class)
                .getPreEligibilitySessions(
                        locationCode(locationSelection.getTraditionalAuthority())
                        , locationCode(locationSelection.getCluster())
                        , locationCode(locationSelection.getZone())
                        , locationCode(locationSelection.getVillage())
                        , page
                )
                .beforeCall(() -> progressDialog.show())
                .afterCall(() -> progressDialog.dismiss())
                .onSuccess(data -> {
                    if (!data.getSessions().isEmpty()) {
                        sessionViewModel.save(data.getSessions());
                        if (!data.isLastPage()) {
                            downloadSessionHouseholds(data.getSessions());
                        }
                    }
                    UiUtils.toast(requireContext(), R.string.download_successful);
                })
                .onError(data -> UiUtils.snackbar(binding.getRoot(), R.string.error_downloading_pev_sessions))
                .execute();
    }

    private void downloadSessionHouseholds(List<PreEligibilityVerificationSession> sessions) {
        progressDialog.setMessage("Downloading households...");
        getService(TargetingService.class)
                .getHouseholdsFromPreEligibilitySession(sessions.remove(0).getId())
                .beforeCall(() -> progressDialog.show())
                .afterCall(() -> progressDialog.dismiss())
                .onSuccess(new ApiStatusCallback<HouseholdDetailResponse>() {
                    @Override
                    public void call(HouseholdDetailResponse data) {
                        if (!data.getHouseholdDetails().isEmpty()) {
                            for (HouseholdDetails householdDetails : data.getHouseholdDetails()) {
                                householdViewModel.save(householdDetails.getHousehold());
                                individualViewModel.save(householdDetails.getMembers());
                            }
                            if (!data.isLastPage()) {

                            }
                        }
                    }
                })
                .onError(data -> UiUtils.snackbar(binding.getRoot(), R.string.error_downloading_pev_sessions))
                .execute();
    }

    private Long locationCode(GeoLocation geoLocation) {
        return geoLocation != null ? geoLocation.getCode() : 0L;
    }
}
