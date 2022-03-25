package app.sctp.app;

import android.app.Application;

import app.sctp.core.ApplicationConfiguration;


public class SctApplication extends Application {

    private static SctApplication instance;
    private ApplicationConfiguration configuration;

    public static SctApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        configuration = new ApplicationConfiguration(this);
        configuration.refreshUserDetailsIfAvailable();
    }

    public ApplicationConfiguration getConfiguration() {
        return configuration;
    }
}
