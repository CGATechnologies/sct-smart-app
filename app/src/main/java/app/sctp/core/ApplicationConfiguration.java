package app.sctp.core;


import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import app.sctp.BuildConfig;
import app.sctp.app.SctApplication;
import app.sctp.core.net.api.AppVersionInterceptor;
import app.sctp.core.net.api.AuthorizationTokenInterceptor;
import app.sctp.core.net.api.ext.SctpApiCallAdapterFactory;
import app.sctp.user.UserDetails;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationConfiguration {
    private static final int TIMEOUT_SECONDS = 30;

    private final Gson gson;
    private Retrofit retrofit;
    private UserDetails userDetails;
    private final String apiBaseUrl;
    private final JwtUtils jwtUtils;
    private final Application application;
    private final OkHttpClient httpClient;
    private final SharedPreferenceAccessor accessor;
    private final ExecutorService networkExecutorService;

    public ApplicationConfiguration(SctApplication application) {
        this.gson = new GsonBuilder().create();
        this.application = application;
        this.apiBaseUrl = BuildConfig.API_URL;
        this.accessor = new SharedPreferenceAccessor(application);
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .callTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(new AppVersionInterceptor(accessor))
                .addInterceptor(new AuthorizationTokenInterceptor(accessor))
                .addInterceptor(loggingInterceptor())
                .build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(this.apiBaseUrl)
                .addCallAdapterFactory(new SctpApiCallAdapterFactory(gson))
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        this.jwtUtils = new JwtUtils(gson);
        this.networkExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    private HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

    public JwtUtils getJwtUtils() {
        return jwtUtils;
    }


    public SharedPreferenceAccessor sharedPreferenceAccessor() {
        return accessor;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void postBackgroundWork(Runnable runnable) {
        networkExecutorService.submit(runnable);
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public <T> T getService(Class<T> service) {
        return retrofit.create(service);
    }

    public void refreshUserDetailsIfAvailable() {
        setUserDetails(jwtUtils.parseJwtToken(sharedPreferenceAccessor().getAccessToken()));
    }
}
