package app.sctp.targeting.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.util.List;

import app.sctp.R;
import app.sctp.core.net.api.ext.ApiCall;
import app.sctp.core.net.api.ext.ApiErrorCallback;
import app.sctp.core.ui.WindowedActivity;
import app.sctp.core.ui.adapter.GenericAdapter;
import app.sctp.core.ui.adapter.ItemSelectionListener;
import app.sctp.databinding.ActivityLocationSelectionBinding;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationDownloadResponse;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.LocationType;
import app.sctp.targeting.services.LocationService;
import app.sctp.targeting.ui.viewholders.LocationItemViewHolderCreator;
import app.sctp.targeting.viewmodels.LocationViewModel;
import app.sctp.utils.UiUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class LocationSelectionActivity extends WindowedActivity {
    public static final String PARAM_LOCATION = "selection.result.location";
    private GenericAdapter<GeoLocation> adapter;
    private LocationViewModel locationViewModel;
    private ActivityLocationSelectionBinding binding;

    public static void selectLocation(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(new Intent(fragment.getContext(),
                LocationSelectionActivity.class), requestCode);
    }

    public static void selectLocation(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity,
                LocationSelectionActivity.class), requestCode);
    }

    public static LocationSelection getSelectedLocation(@NonNull Intent data) {
        return (LocationSelection) data.getSerializableExtra(PARAM_LOCATION);
    }


    public static LocationSelection getSelectedLocation(@NonNull Bundle data) {
        return (LocationSelection) data.getSerializable(PARAM_LOCATION);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationViewModel = getViewModel(LocationViewModel.class);

        adapter = new GenericAdapter<>(new LocationItemViewHolderCreator(
                new ItemSelectionListener<GeoLocation>() {
                    @Override
                    public void onItemSelected(GeoLocation item) {
                        // TODO(zikani03): Handler here?
                    }

                    @Override
                    public void onItemLongSelected(GeoLocation item) {

                    }
                }));

        binding.selectButton.setOnClickListener(v -> {
            GeoLocation zone = binding.zone.getSelectedLocation();
            GeoLocation ta = binding.taSelector.getSelectedLocation();
            GeoLocation village = binding.village.getSelectedLocation();
            GeoLocation villageCluster = binding.villageCluster.getSelectedLocation();

            if (ta == null && villageCluster == null && zone == null && village == null) {
                UiUtils.snackbar(getRootView(), R.string.error_no_location_selected);
                return;
            }

            if (isInvalidSelection(zone, village)) {
                UiUtils.snackbar(getRootView(), R.string.error_zone_must_be_selected);
                return;
            }

            if (isInvalidSelection(villageCluster, zone)) {
                UiUtils.snackbar(getRootView(), R.string.error_cluster_must_be_selected);
                return;
            }

            if (isInvalidSelection(ta, villageCluster)) {
                UiUtils.snackbar(getRootView(), R.string.error_ta_must_be_selected);
                return;
            }

            LocationSelection locationSelection = new LocationSelection(
                    getApplicationConfiguration().getUserDetails().getDistrictCode(),
                    ta, villageCluster, zone, village
            );
            setResult(RESULT_OK, new Intent().putExtra(PARAM_LOCATION, locationSelection));
            finish();
        });

        listLocations();
    }

    private boolean isInvalidSelection(GeoLocation parent, GeoLocation child) {
        return child != null && parent == null;
    }

    private void listLocations() {
        locationViewModel.getSubLocationsByParentCode(getApplicationConfiguration().getUserDetails().getDistrictCode())
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<List<GeoLocation>>() {
                    @Override
                    public void onNext(List<GeoLocation> locations) {
                        adapter.submitList(locations);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_update_locations) {
            downloadLocations();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_selection_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Download locations from
     */
    private void downloadLocations() {
        ProgressDialog progressDialog = UiUtils.progressDialog(this);
        final ApiErrorCallback errorCallback = data -> {
            progressDialog.cancel();
            UiUtils.snackbar(getRootView(), data.getMessage());
        };
        downloadLocations(LocationType.SUBNATIONAL2)
                .beforeCall(progressDialog::show)
                .onError(errorCallback)
                .onSuccess(tas -> {
                    locationViewModel.save(tas.getList());
                    downloadLocations(LocationType.SUBNATIONAL3)
                            .onSuccess(clusters -> {
                                locationViewModel.save(clusters.getList());
                                downloadLocations(LocationType.SUBNATIONAL4)
                                        .onError(errorCallback)
                                        .onSuccess(zones -> {
                                            locationViewModel.save(zones.getList());
                                            downloadLocations(LocationType.SUBNATIONAL5)
                                                    .onError(errorCallback)
                                                    .onSuccess(villages -> {
                                                        locationViewModel.save(villages.getList());
                                                        UiUtils.snackbar(
                                                                getRootView(),
                                                                R.string.location_download_complete,
                                                                true);
                                                        // TODO notify the selectors to refresh
                                                    })
                                                    .onCompleted(progressDialog::cancel)
                                                    .execute();
                                        })
                                        .execute();
                            })
                            .execute();
                }).execute();
    }

    private ApiCall<LocationDownloadResponse> downloadLocations(LocationType locationType) {
        return getApplicationConfiguration()
                .getService(LocationService.class)
                .getLocationsByType(locationType);
    }

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater) {
        return binding = ActivityLocationSelectionBinding.inflate(inflater);
    }

}
