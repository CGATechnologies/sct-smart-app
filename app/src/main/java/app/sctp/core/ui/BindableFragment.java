package app.sctp.core.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.util.Locale;

import app.sctp.app.SctApplication;
import app.sctp.core.ApplicationConfiguration;

public abstract class BindableFragment extends Fragment {

    private ViewBinding viewBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = bindViews(inflater, container, savedInstanceState);
        initializeComponents(savedInstanceState);
        return viewBinding.getRoot();
    }

    @SuppressWarnings("unchecked")
    protected final <T extends ViewBinding> T getViewBinding() {
        return (T) viewBinding;
    }

    protected void initializeComponents(Bundle savedInstance) {
    }

    protected abstract ViewBinding bindViews(LayoutInflater inflater, ViewGroup container, Bundle bundle);

    protected final String format(String text, Object... args) {
        return String.format(Locale.US, text, args);
    }

    private ApplicationConfiguration applicationConfiguration;

    protected final ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    public BindableFragment() {
        applicationConfiguration = SctApplication.getInstance().getConfiguration();
    }
}
