package app.sctp.persistence;

import android.os.Handler;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;

class ObservableDatabaseCallImpl<T> implements ObservableDatabaseCall<T> {
    private final Runnable task;
    private final Handler handler;
    private final ExecutorService executorService;
    private DatabaseCallStatusObserver afterObserver;
    private DatabaseCallStatusObserver beforeObserver;
    private DatabaseCallDataObserver<T> successObserver;
    private DatabaseCallDataObserver<Throwable> errorObserver;

    ObservableDatabaseCallImpl(@NonNull Runnable task, ExecutorService executorService, Handler handler) {
        this.task = task;
        this.handler = handler;
        this.executorService = executorService;
    }

    @Override
    public ObservableDatabaseCall<T> before(DatabaseCallStatusObserver observer) {
        this.beforeObserver = observer;
        return this;
    }

    @Override
    public ObservableDatabaseCall<T> after(DatabaseCallStatusObserver observer) {
        this.afterObserver = observer;
        return this;
    }

    @Override
    public ObservableDatabaseCall<T> success(DatabaseCallDataObserver<T> observer) {
        this.successObserver = observer;
        return this;
    }

    @Override
    public ObservableDatabaseCall<T> error(DatabaseCallDataObserver<Throwable> observer) {
        this.errorObserver = observer;
        return this;
    }

    @Override
    public void execute() {
        this.executorService.submit(task);
    }

    void onError(Throwable throwable) {
        if (errorObserver != null) {
            handler.post(() -> errorObserver.update(throwable));
        }
    }

    void onSuccess(T data) {
        if (successObserver != null) {
            handler.post(() -> successObserver.update(data));
        }
    }

    void onBefore() {
        if (beforeObserver != null) {
            handler.post(() -> beforeObserver.update());
        }
    }

    void onAfter() {
        if (afterObserver != null) {
            handler.post(() -> afterObserver.update());
        }
    }
}
