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

    public static void debugLog(String format, Object... parameters) {
        if (BuildConfig.DEBUG) {
            Log.d(getMethodName(), String.format(format, parameters));
        }
    }

    public static void errorLog(String format, Object... parameters) {
        Log.e(getMethodName(), String.format(format, parameters));
    }

    private static String getMethodName() {
        try {
            throw new RuntimeException();
        } catch (Exception e) {
            StackTraceElement element = e.getStackTrace()[2];
            if (element != null) {
                String[] parts = element.getClassName().split("\\.");
                return parts[parts.length - 1] + "." + element.getMethodName();
            }
            return "??.??";
        }
    }
}
