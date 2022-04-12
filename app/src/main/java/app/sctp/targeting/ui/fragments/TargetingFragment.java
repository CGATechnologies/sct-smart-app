package app.sctp.targeting.ui.fragments;

import android.app.ProgressDialog;

import java.net.HttpURLConnection;

import app.sctp.core.ui.BaseFragment;
import app.sctp.targeting.services.TargetingService;
import app.sctp.utils.UiUtils;

public class TargetingFragment extends BaseFragment {


    private void downloadDistrictLocations() {
        /*ProgressDialog progressDialog = UiUtils.progressDialog(requireContext());
        getConfiguration().getService(TargetingService.class)
                .getLocationsUnderDistrict(10L)
                .beforeCall(progressDialog::show)
                .afterCall(progressDialog::hide)
                .onStatus(HttpURLConnection.HTTP_UNAUTHORIZED, data -> {
                    // TODO show login message
                })
                .onSuccess(data -> {
                    // TODO save data to some
                }).onError(data -> {
            // TODO show error
        }).execute();*/
    }
}
