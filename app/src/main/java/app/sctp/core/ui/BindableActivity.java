package app.sctp.core.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.viewbinding.ViewBinding;

public abstract class BindableActivity extends BaseActivity {

    protected ViewBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewBinding = bindViews(getLayoutInflater());
        setContentView(viewBinding.getRoot());
    }

    @SuppressWarnings("unchecked")
    protected <T extends ViewBinding> T getViewBinding() {
        return (T) viewBinding;
    }

    protected <T extends View> T getRootView() {
        return (T) getViewBinding().getRoot();
    }

    /**
     * Override to bind views
     *
     * @param inflater .
     * @return
     */
    protected abstract ViewBinding bindViews(LayoutInflater inflater);
}
