package app.sctp.utils;

import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.Locale;

import app.sctp.BuildConfig;
import io.reactivex.Scheduler;

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

    public static DynamicSwitch dynamicSwitchOn(int value) {
        return new DynamicSwitch(value);
    }

    public static final class DynamicSwitch {
        private final int value;
        private final SparseArray<Runnable> callbacks;

        private DynamicSwitch(int value) {
            this.value = value;
            this.callbacks = new SparseArray<>();
        }

        public DynamicSwitch when(int match, Runnable callback) {
            callbacks.put(match, callback);
            return this;
        }

        public void eval() {
            for (int i = 0; i < callbacks.size(); i++) {
                final int key = callbacks.keyAt(i);
                if (key == value) {
                    callbacks.get(key).run();
                }
            }
        }
    }
}
