package app.sctp.persistence;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import app.sctp.app.SctApplication;

public abstract class BaseViewModel extends AndroidViewModel {
    public static final int PAGE_SIZE = 20;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected SctpAppDatabase getDatabase() {
        return ((SctApplication) getApplication()).getAppDatabase();
    }

    protected final <T extends BaseRepository> T getRepository(Class<T> repositoryClass){
        return Repositories.getRepository(repositoryClass);
    }
}
