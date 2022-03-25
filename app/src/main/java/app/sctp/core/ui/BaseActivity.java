package app.sctp.core.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import app.sctp.app.SctApplication;
import app.sctp.core.ApplicationConfiguration;
import io.reactivex.Observable;

public abstract class BaseActivity extends AppCompatActivity {

    protected String format(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }

    private ApplicationConfiguration applicationConfiguration;

    protected final ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationConfiguration = SctApplication.getInstance().getConfiguration();
    }
}
