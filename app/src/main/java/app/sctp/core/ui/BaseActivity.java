package app.sctp.core.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import app.sctp.app.SctApplication;
import app.sctp.core.ApplicationConfiguration;
import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.BaseViewModel;
import io.reactivex.Observable;

public abstract class BaseActivity extends AppCompatActivity {

    protected String format(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }

    private ActionBar actionBar;
    private boolean toolbarInitialized;
    private ApplicationConfiguration applicationConfiguration;

    protected final ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    protected final <T extends BaseViewModel> T getViewModel(Class<T> viewModelClass) {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(SctApplication.getInstance())
                .create(viewModelClass);
    }

    protected final void setupToolBar() {
        if (!toolbarInitialized) {
            actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            toolbarInitialized = true;
        }
    }

    protected final void setSubTitle(CharSequence subTitle) {
        if (actionBar != null) {
            actionBar.setSubtitle(subTitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationConfiguration = SctApplication.getInstance().getConfiguration();
    }
}
