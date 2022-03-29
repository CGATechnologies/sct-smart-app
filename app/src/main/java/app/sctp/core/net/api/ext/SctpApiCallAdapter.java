package app.sctp.core.net.api.ext;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import app.sctp.core.net.api.ErrorResponse;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;

public class SctpApiCallAdapter<R> implements CallAdapter<R, ApiCall<R>> {
    private final Gson gson;
    private final Handler handler;
    private final Type responseType;
    private final ExecutorService executorService;


    public SctpApiCallAdapter(Type responseType, Gson gson) {
        this(responseType, gson, null);
    }

    public SctpApiCallAdapter(Type responseType, Gson gson, ExecutorService executorService) {
        this.gson = gson;
        this.responseType = responseType;
        this.executorService = executorService == null ? Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()) : executorService;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @NonNull
    @Override
    public Type responseType() {
        return responseType;
    }

    private ErrorResponse getErrorResponse(Response<R> response) {
        try {
            return gson.fromJson(response.errorBody().string(), ErrorResponse.class);
        } catch (Exception e) {
            return null;
        }
    }

    @NonNull
    @Override
    public ApiCall<R> adapt(@NonNull Call<R> call) {
        final ApiCallImpl<R> apiCall = new ApiCallImpl<>(this.executorService);
        final AtomicReference<Response<R>> responseReference = new AtomicReference<>();
        Runnable runnable = () -> {
            handler.post(apiCall::notifyBeforeCall);
            try {
                responseReference.set(call.execute());
                if (responseReference.get().isSuccessful()) {
                    final ApiStatusCallback<R> callback = apiCall.getStatusCallback(responseReference.get().code());
                    handler.post(() -> {
                        apiCall.notifyAfterCall();
                        if (callback != null) {
                            callback.call(responseReference.get().body());
                        } else {
                            // fallback to success
                            apiCall.notifyOnSuccess(responseReference.get().body());
                        }
                        apiCall.notifyOnCompleted();
                    });
                } else {
                    final ApiStatusCallback<R> callback = apiCall.getStatusCallback(responseReference.get().code());
                    if (callback != null) {
                        handler.post(() -> {
                            apiCall.notifyAfterCall();
                            callback.call(null);
                            apiCall.notifyOnCompleted();
                        });
                    } else {
                        final AtomicReference<ErrorResponse> errorResponseReference
                                = new AtomicReference<>(getErrorResponse(responseReference.get()));
                        if (errorResponseReference.get() == null) {
                            errorResponseReference.set(
                                    new ErrorResponse(responseReference.get().code()
                                            , responseReference.get().message())
                            );
                        }
                        handler.post(() -> {
                            apiCall.notifyAfterCall();
                            apiCall.notifyOnError(
                                    errorResponseReference.get(),
                                    new HttpException(responseReference.get())
                            );
                            apiCall.notifyOnCompleted();
                        });
                    }
                }
            } catch (final Exception e) {
                final String message;
                if (e instanceof RuntimeException) {
                    // bad response
                    message = "Bad response";
                } else {
                    // some other response
                    message = "Error negotiating with the server.";
                }
                handler.post(() -> {
                    apiCall.notifyAfterCall();
                    apiCall.notifyOnError(new ErrorResponse(-1, message), e);
                    apiCall.notifyOnCompleted();
                });
            }
        };
        return apiCall.setRunnable(runnable);
    }
}
