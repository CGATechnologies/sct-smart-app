package app.sctp.persistence;

public interface ObservableDatabaseCall<T> {
    ObservableDatabaseCall<T> before(DatabaseCallStatusObserver observer);
    ObservableDatabaseCall<T> after(DatabaseCallStatusObserver observer);
    ObservableDatabaseCall<T> success(DatabaseCallDataObserver<T> observer);

    ObservableDatabaseCall<T> error(DatabaseCallDataObserver<Throwable> observer);

    void execute();
}
