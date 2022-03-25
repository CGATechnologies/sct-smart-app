package app.sctp.core.net.api;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Locale;

import app.sctp.core.SharedPreferenceAccessor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor to automatically inject access token into requests
 */
public class AuthorizationTokenInterceptor implements Interceptor {
    private final SharedPreferenceAccessor sharedPreferenceAccessor;

    public AuthorizationTokenInterceptor(SharedPreferenceAccessor sharedPreferenceAccessor) {
        this.sharedPreferenceAccessor = sharedPreferenceAccessor;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request;
        String accessToken = sharedPreferenceAccessor.getAccessToken();
        if (accessToken != null) {
            request = chain.request().newBuilder()
                    .addHeader("Authorization", String.format(Locale.US, "Bearer %s", accessToken))
                    .build();
        } else {
            request = chain.request();
        }
        return chain.proceed(request);
    }
}
