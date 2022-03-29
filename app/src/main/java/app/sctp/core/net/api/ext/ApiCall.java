package app.sctp.core.net.api.ext;

import app.sctp.core.net.api.ApiResponseCallback;

public interface ApiCall<T> {
    ApiCall<T> beforeCall(NotificationCallback callback);

    /**
     * Overrides {@link #onStatus(int, ApiStatusCallback)} with status 200
     *
     * @param callback .
     * @return .
     */
    ApiCall<T> onSuccess(ApiStatusCallback<T> callback);

    ApiCall<T> onCompleted(NotificationCallback callback);

    /**
     * Same as calling <code>onStatus(404, callback)</code>
     *
     * @param callback .
     * @return .
     */
    ApiCall<T> onNotFound(NotificationCallback callback);

    /**
     * @param callback
     * @return
     */
    ApiCall<T> onError(ApiErrorCallback callback);

    ApiCall<T> onStatus(int httpStatus, ApiStatusCallback<T> callback);

    ApiCall<T> afterCall(NotificationCallback callback);

    void execute();
}
