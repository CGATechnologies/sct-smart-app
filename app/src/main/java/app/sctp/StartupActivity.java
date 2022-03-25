package app.sctp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.viewbinding.ViewBinding;

import app.sctp.core.ui.BindableActivity;
import app.sctp.databinding.AcivityStartupBinding;

public class StartupActivity extends BindableActivity {
    private AcivityStartupBinding binding;

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater) {
        return AcivityStartupBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(viewBinding.getRoot());


        if (getApplicationConfiguration().sharedPreferenceAccessor().hasAccessToken()) {
           startActivity(new Intent(this, MainActivity.class));
        } else {
           startActivity(new Intent(this, LoginActivity.class));
        }
        finishAffinity();
    }
}