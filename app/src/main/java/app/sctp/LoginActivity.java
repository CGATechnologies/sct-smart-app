package app.sctp;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.net.ConnectException;
import java.net.HttpURLConnection;

import app.sctp.auth.AuthenticationRequest;
import app.sctp.auth.AuthenticationResponse;
import app.sctp.auth.AuthenticationService;
import app.sctp.core.net.api.ApiResponseCallback;
import app.sctp.core.net.api.ErrorResponse;
import app.sctp.core.net.api.ext.ApiErrorCallback;
import app.sctp.core.net.api.ext.ApiStatusCallback;
import app.sctp.core.net.api.ext.NotificationCallback;
import app.sctp.core.ui.BindableActivity;
import app.sctp.databinding.AcivityLoginBinding;
import app.sctp.utils.UiUtils;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class LoginActivity extends BindableActivity {

    private AcivityLoginBinding binding;
    private AuthenticationRequest authenticationRequest;

    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater) {
        return AcivityLoginBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();
        binding.version.setText(format("v%s", BuildConfig.VERSION_NAME));

        binding.btnLogin.setOnClickListener(v -> doAuthenticate());
        authenticationRequest = new AuthenticationRequest();
    }

    private void doAuthenticate() {
        authenticationRequest.setUserName(UiUtils.getNonEmptyText(binding.username));
        authenticationRequest.setPassword(UiUtils.getNonEmptyText(binding.password));

        if (authenticationRequest.getUserName() == null
                || authenticationRequest.getPassword() == null) {
            return;
        }

        ProgressDialog progressDialog = UiUtils.progressDialog(this);

        getApplicationConfiguration().getService(AuthenticationService.class)
                .authenticate(authenticationRequest)
                .beforeCall(progressDialog::show)
                .afterCall(progressDialog::dismiss)
                .onSuccess(data -> {
                    UiUtils.toast(LoginActivity.this, R.string.auth_success);
                    getApplicationConfiguration().sharedPreferenceAccessor()
                            .setAccessToken(data.getAccessToken());
                    getApplicationConfiguration().refreshUserDetailsIfAvailable();
                    navigateToDashboardActivity();
                })
                .onStatus(HttpURLConnection.HTTP_UNAUTHORIZED, data -> UiUtils.snackbar(getRootView(), R.string.auth_invalid_credentials))
                .onStatus(HttpURLConnection.HTTP_FORBIDDEN, data -> UiUtils.snackbar(getRootView(), R.string.auth_account_locked))
                .onStatus(HttpURLConnection.HTTP_PRECON_FAILED, data -> UiUtils.snackbar(getRootView(), R.string.app_outdated_version))
                .onError(data -> UiUtils.snackbar(getRootView(), data.getMessage()))
                .execute();
    }

    private void navigateToDashboardActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }
}