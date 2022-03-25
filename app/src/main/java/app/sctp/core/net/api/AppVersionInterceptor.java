package app.sctp.core.net.api;

import android.os.Build;

import androidx.annotation.NonNull;

import java.io.IOException;

import app.sctp.BuildConfig;
import app.sctp.core.SharedPreferenceAccessor;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor that automatically injects version code into all requests
 */
public class AppVersionInterceptor implements Interceptor {
    private static final String APP_VERSION_CODE = "X-App-Version-Code";
    private static final String APP_VERSION_UPDATED_AT = "X-App-Version-Updated-At";
    private static final String APP_VERSION_UPDATE_MANDATORY = "X-App-Version-Update-Mandatory";
    private static final String APP_VERSION_UPDATE_AVAILABLE = "X-App-Version-Update-Available";

    private final SharedPreferenceAccessor accessor;

    public AppVersionInterceptor(SharedPreferenceAccessor accessor) {
        this.accessor = accessor;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader(APP_VERSION_CODE, Integer.toString(BuildConfig.VERSION_CODE))
                .build();
        Response response = chain.proceed(request);

        boolean updateAvailable = false;
        boolean mandatoryUpdate = false;
        try {
            updateAvailable = Boolean.parseBoolean(response.header(APP_VERSION_UPDATE_AVAILABLE));
            mandatoryUpdate = Boolean.parseBoolean(response.header(APP_VERSION_UPDATE_MANDATORY));
        } catch (Exception e) {

        }
        accessor.setUpdateAvailable(updateAvailable, mandatoryUpdate);
        // TODO send global event
        return response;
    }
}
