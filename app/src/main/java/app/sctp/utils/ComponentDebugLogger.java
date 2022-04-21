package app.sctp.utils;

public interface ComponentDebugLogger {
    void error(String message, Throwable throwable);
    void error(String message, Object...args);
    void verbose(String message, Object...args);
}
