package app.sctp.targeting.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import app.sctp.R;
import app.sctp.core.ui.BindableFragment;
import app.sctp.core.ui.adapter.GenericAdapter;
import app.sctp.core.ui.adapter.ItemSelectionListener;
import app.sctp.databinding.FragmentTargetingCommunityMeetingBinding;
import app.sctp.databinding.LocationInfoBinding;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.HouseholdDetailResponse;
import app.sctp.targeting.models.HouseholdDetails;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.PreEligibilityVerificationSession;
import app.sctp.targeting.models.PreEligibilityVerificationSessionResponse;
import app.sctp.targeting.services.TargetingService;
import app.sctp.targeting.ui.activities.PreEligibilityVerificationSessionActivity;
import app.sctp.targeting.ui.viewholders.PreEligibilityVerificationSessionViewHolderCreator;
import app.sctp.targeting.viewmodels.HouseholdViewModel;
import app.sctp.targeting.viewmodels.IndividualViewModel;
import app.sctp.targeting.viewmodels.PreEligibilityVerificationSessionViewModel;
import app.sctp.utils.PlatformUtils;
import app.sctp.utils.UiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class CommunityMeetingFragment extends BindableFragment {

    private ProgressDialog progressDialog;
    private LocationSelection locationSelection;
    private HouseholdViewModel householdViewModel;
    private LocationInfoBinding locationInfoBinding;
    private IndividualViewModel individualViewModel;
    private FragmentTargetingCommunityMeetingBinding binding;
    private final Handler handler = new Handler(Looper.getMainLooper());
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
        sessionAdapter.setItemSelectionListener(new ItemSelectionListener<PreEligibilityVerificationSession>() {
            @Override
            public void onItemSelected(PreEligibilityVerificationSession item) {
                PreEligibilityVerificationSessionActivity.selectEligibleHouseholds(
                        requireActivity(),
                        item
                );
            }

            @Override
            public void onItemLongSelected(PreEligibilityVerificationSession item) {

            }
        });

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
                        PlatformUtils.printStackTrace(t);
                        UiUtils.toast(requireContext(), R.string.error_loading_data);
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
        progressDialog.show();
        progressDialog.setMessage("Downloading sessions...");
        getApplicationConfiguration().postBackgroundWork(() -> {
            try {
                int page = 0;
                AtomicInteger pageCount = new AtomicInteger(0);
                do {
                    handler.post(() -> progressDialog.setMessage("Downloading sessions..."));

                    Response<PreEligibilityVerificationSessionResponse> sessionResponse =
                            getService(TargetingService.class)
                                    .getPreEligibilitySessions(
                                            locationCode(locationSelection.getTraditionalAuthority())
                                            , locationCode(locationSelection.getCluster())
                                            , locationCode(locationSelection.getZone())
                                            , locationCode(locationSelection.getVillage())
                                            , page
                                    ).execute();
                    if (sessionResponse.isSuccessful()) {
                        PreEligibilityVerificationSessionResponse response = sessionResponse.body();

                        pageCount.compareAndSet(0, response.getTotalPages());

                        if (!response.getItems().isEmpty()) {
                            sessionViewModel.save(response.getItems());
                        }
                        // download households under this session
                        downloadSessionHouseholds(response.getItems());

                        progressDialog.setProgress(page);
                    } else {
                        throw new HttpException(sessionResponse);
                    }
                } while (++page < pageCount.get());
                handler.post(() -> {
                    progressDialog.dismiss();
                    UiUtils.snackbar(binding.getRoot(), R.string.download_successful);
                });
            } catch (Exception exception) {
                PlatformUtils.printStackTrace(exception);
                handler.post(() -> {
                    progressDialog.dismiss();
                    UiUtils.snackbar(binding.getRoot(), R.string.error_downloading_data);
                });
            }
        });
    }

    private void downloadSessionHouseholds(List<PreEligibilityVerificationSession> sessions) throws IOException {
        handler.post(() -> progressDialog.setMessage("Downloading household data..."));
        for (PreEligibilityVerificationSession session : sessions) {
            int page = 0;
            AtomicInteger pageCount = new AtomicInteger(0);
            do {
                Response<HouseholdDetailResponse> response =
                        getService(TargetingService.class)
                                .getHouseholdsFromPreEligibilitySession(session.getId(), page)
                                .execute();
                if (response.isSuccessful()) {
                    HouseholdDetailResponse detailResponse = response.body();
                    pageCount.compareAndSet(0, detailResponse.getTotalPages());

                    if (!detailResponse.getItems().isEmpty()) {
                        for (HouseholdDetails householdDetails : detailResponse.getItems()) {
                            householdViewModel.save(householdDetails.getHousehold());
                            if (!householdDetails.getMemberDetails().isEmpty()) {
                                individualViewModel.save(householdDetails.getMemberDetails());
                            }
                        }
                    }
                } else {
                    throw new HttpException(response);
                }
            } while (++page < pageCount.get());
        }
    }

    private Long locationCode(GeoLocation geoLocation) {
        return geoLocation != null ? geoLocation.getCode() : 0L;
    }
}
