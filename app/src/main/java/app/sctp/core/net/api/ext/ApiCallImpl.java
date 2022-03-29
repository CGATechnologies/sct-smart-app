package app.sctp.core.net.api.ext;

import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;

import java.net.HttpURLConnection;
import java.util.concurrent.ExecutorService;

import app.sctp.core.net.api.ErrorResponse;
import retrofit2.Call;
import retrofit2.HttpException;

class ApiCallImpl<T> implements ApiCall<T> {
    private Runnable runnable;
    private final Handler mainThreadHandler;
    private final ExecutorService executorService;
    private NotificationCallback afterCallCallback;
    private NotificationCallback completedCallback;
    private NotificationCallback beforeCallCallback;
    private ApiStatusCallback<ErrorResponse> errorCallback;
    private SparseArray<ApiStatusCallback<T>> statusCallbackTable;

    ApiCallImpl(ExecutorService executorService) {
        this.executorService = executorService;
        this.statusCallbackTable = new SparseArray<>();
        this.mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public ApiCall<T> beforeCall(NotificationCallback callback) {
        this.beforeCallCallback = callback;
        return this;
    }

    @Override
    public ApiCall<T> onSuccess(ApiStatusCallback<T> callback) {
        this.statusCallbackTable.put(HttpURLConnection.HTTP_OK, callback);
        return this;
    }

    @Override
    public ApiCall<T> onNotFound(NotificationCallback callback) {
        this.statusCallbackTable.put(HttpURLConnection.HTTP_NOT_FOUND, wrapNonParameterizedCallback(callback));
        return this;
    }

    private ApiStatusCallback<T> wrapNonParameterizedCallback(NotificationCallback delegate) {
        return data -> delegate.call();
    }

    @Override
    public ApiCall<T> onCompleted(NotificationCallback callback) {
        this.completedCallback = callback;
        return this;
    }

    @Override
    public ApiCall<T> onError(ApiErrorCallback callback) {
        this.errorCallback = callback;
        return this;
    }

    @Override
    public ApiCall<T> onStatus(int httpStatus, ApiStatusCallback<T> callback) {
        this.statusCallbackTable.put(httpStatus, callback);
        return this;
    }

    @Override
    public ApiCall<T> afterCall(NotificationCallback callback) {
        this.afterCallCallback = callback;
        return this;
    }

    private boolean isMainThread() {
        return Thread.currentThread() == mainThreadHandler.getLooper().getThread();
    }

    private void postOnBeforeCall() {
        if (beforeCallCallback != null) {
            if (isMainThread()) {
                beforeCallCallback.call();
            } else {
                mainThreadHandler.post(() -> beforeCallCallback.call());
            }
        }
    }

    private void postAfterCall() {
        if (afterCallCallback != null) {
            if (isMainThread()) {
                afterCallCallback.call();
            } else {
                mainThreadHandler.post(() -> afterCallCallback.call());
            }
        }
    }

    @Override
    public void execute() {
        this.executorService.submit(runnable);
    }

    void notifyBeforeCall() {
        if (beforeCallCallback != null) {
            beforeCallCallback.call();
        }
    }

    void notifyAfterCall() {
        if (afterCallCallback != null) {
            afterCallCallback.call();
        }
    }

    void notifyOnSuccess(T data) {
        for (int i = 0; i < statusCallbackTable.size(); i++) {
            int code = statusCallbackTable.keyAt(i);
            if (code >= 200 && code <= 300) {
                ApiStatusCallback<T> successCallback = statusCallbackTable.get(code);
                successCallback.call(data);
                break;
            }
        }
    }

    void notifyOnCompleted() {
        if (completedCallback != null) {
            completedCallback.call();
        }
    }

    ApiStatusCallback<T> getStatusCallback(int code) {
        return statusCallbackTable.get(code);
    }

    ApiCallImpl<T> setRunnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    void notifyOnError(ErrorResponse errorResponse, Exception httpException) {
        if(errorCallback != null){
            errorCallback.call(errorResponse);
        }
    }
}
