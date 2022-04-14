package app.sctp.persistence;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseRepository {
    public static final int DEFAULT_PAGE_SIZE = 20;

    private static final Handler handler;
    private static final ExecutorService executorService;

    private final SctpAppDatabase database;

    static {
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    public BaseRepository(@NonNull SctpAppDatabase database) {
        this.database = null;
    }

    public SctpAppDatabase getDatabase() {
        return database;
    }

    /**
     * Post task to background
     *
     * @param task .
     */
    protected void post(Runnable task) {
        executorService.submit(task);
    }

    protected final <T> ObservableDatabaseCallImpl<T> newObservableDatabaseCall(Runnable task) {
        return new ObservableDatabaseCallImpl<>(task, executorService, handler);
    }

    protected final <T> void onBefore(ObservableDatabaseCall<T> observable) {
        if (observable instanceof ObservableDatabaseCallImpl) {
            ((ObservableDatabaseCallImpl<T>) observable).onBefore();
        }
    }

    protected final <T> void onAfter(ObservableDatabaseCall<T> observable) {
        if (observable instanceof ObservableDatabaseCallImpl) {
            ((ObservableDatabaseCallImpl<T>) observable).onAfter();
        }
    }

    protected final <T> void onError(ObservableDatabaseCall<T> observable, Throwable throwable) {
        if (observable instanceof ObservableDatabaseCallImpl) {
            ((ObservableDatabaseCallImpl<T>) observable).onError(throwable);
        }
    }

    protected final <T> void onSuccess(ObservableDatabaseCall<T> observable, T data) {
        if (observable instanceof ObservableDatabaseCallImpl) {
            ((ObservableDatabaseCallImpl<T>) observable).onSuccess(data);
        }
    }
}
