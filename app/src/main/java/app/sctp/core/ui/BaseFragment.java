package app.sctp.core.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import app.sctp.app.SctApplication;
import app.sctp.core.ApplicationConfiguration;
import app.sctp.persistence.BaseViewModel;

public abstract class BaseFragment extends Fragment {
    private final ApplicationConfiguration configuration;

    public BaseFragment(){
        configuration = SctApplication.getInstance().getConfiguration();;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected final <T extends BaseViewModel> T getViewModel(Class<T> viewModelClass) {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(SctApplication.getInstance())
                .create(viewModelClass);
    }

    protected final <T> T getService(Class<T> serviceClass) {
        return getApplicationConfiguration().getService(serviceClass);
    }

    protected final ApplicationConfiguration getApplicationConfiguration() {
        return configuration;
    }

    protected final String format(String text, Object... args) {
        return String.format(Locale.US, text, args);
    }
}
