package app.sctp.utils;

import android.util.Log;

import java.util.Locale;

import app.sctp.BuildConfig;

public class PlatformUtils {
    public static void printStackTrace(Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace();
        }
    }

    public static void error(String tag, String format, Object... args) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, String.format(Locale.US, format, args));
        }
    }
}
