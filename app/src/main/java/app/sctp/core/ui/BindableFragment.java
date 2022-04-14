package app.sctp.core.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

public abstract class BindableFragment extends BaseFragment {

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

    /**
     * <p>Initialize components within {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}</p>
     * <p>Heavy initialization should be done in {@link #onViewCreated(View, Bundle)}</p>
     *
     * @param savedInstance .
     */
    protected void initializeComponents(Bundle savedInstance) {
    }

    protected abstract ViewBinding bindViews(LayoutInflater inflater, ViewGroup container, Bundle bundle);
}
