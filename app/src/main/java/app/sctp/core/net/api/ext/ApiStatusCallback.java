package app.sctp.core.net.api.ext;

public interface ApiStatusCallback<T> {
    void call(T data);
}
