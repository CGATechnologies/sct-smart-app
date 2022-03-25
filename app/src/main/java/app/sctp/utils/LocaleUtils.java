package app.sctp.utils;

public final class LocaleUtils {
    public static boolean hasText(String text) {
        return text != null && text.trim().length() >= 1;
    }
}
