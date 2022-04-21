package app.sctp.utils;

import android.util.Log;

import java.util.Locale;

import app.sctp.BuildConfig;

public class ComponentDebugLoggerImpl implements ComponentDebugLogger {
    private final String tag;

    public ComponentDebugLoggerImpl(String tag) {
        this.tag = tag;
    }

    @Override
    public void error(String message, Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, throwable);
        }
    }

    @Override
    public void error(String message, Object... args) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, String.format(Locale.US, message, args));
        }
    }

    @Override
    public void verbose(String message, Object... args) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, String.format(Locale.US, message, args));
        }
    }
}
