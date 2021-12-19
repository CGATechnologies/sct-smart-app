package app.sctp;

import androidx.viewbinding.ViewBinding;

import android.os.Bundle;
import android.view.LayoutInflater;

import app.sctp.core.ui.BindableActivity;
import app.sctp.databinding.AcivityLoginBinding;

public class LoginActivity extends BindableActivity {

    private AcivityLoginBinding binding;

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater) {
        return AcivityLoginBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getViewBinding();
        binding.version.setText(format("v%s", BuildConfig.VERSION_NAME));
    }


}