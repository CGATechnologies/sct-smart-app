package app.sctp.targeting;

import android.app.ProgressDialog;

import java.net.HttpURLConnection;
import java.util.List;

import app.sctp.core.net.api.ErrorResponse;
import app.sctp.core.net.api.ext.ApiErrorCallback;
import app.sctp.core.net.api.ext.ApiStatusCallback;
import app.sctp.core.net.api.ext.NotificationCallback;
import app.sctp.core.ui.BaseFragment;
import app.sctp.utils.UiUtils;

public class TargetingFragment extends BaseFragment {


    private void downloadDistrictLocations() {
        ProgressDialog progressDialog = UiUtils.progressDialog(requireContext());
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
        }).execute();
    }
}
