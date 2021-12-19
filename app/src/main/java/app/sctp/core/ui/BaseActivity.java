package app.sctp.core.ui;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {

    protected String format(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }
}
