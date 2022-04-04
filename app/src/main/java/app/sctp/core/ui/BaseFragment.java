package app.sctp.core.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import app.sctp.app.SctApplication;
import app.sctp.core.ApplicationConfiguration;
import app.sctp.persistence.BaseViewModel;

public abstract class BaseFragment extends Fragment {
    private final ApplicationConfiguration configuration;

    public BaseFragment(){
        configuration = SctApplication.getInstance().getConfiguration();;
    }

    protected final ApplicationConfiguration getConfiguration(){
        return configuration;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected final <T extends BaseViewModel> T getViewModel(Class<T> viewModelClass) {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(SctApplication.getInstance())
                .create(viewModelClass);
    }
}
