package app.sctp.persistence;

public interface DatabaseCallDataObserver<T> {
    void update(T data);
}
