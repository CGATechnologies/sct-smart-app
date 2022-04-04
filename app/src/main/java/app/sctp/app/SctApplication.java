package app.sctp.app;

import android.app.Application;

import androidx.room.Room;
import androidx.room.migration.AutoMigrationSpec;

import app.sctp.core.ApplicationConfiguration;
import app.sctp.persistence.Migrations;
import app.sctp.persistence.SctpAppDatabase;


public class SctApplication extends Application {

    private SctpAppDatabase appDatabase;
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
        appDatabase = Room.databaseBuilder(this, SctpAppDatabase.class, getPackageName())
                .addMigrations(Migrations.MIGRATIONS)
                .build();
    }

    public SctpAppDatabase getAppDatabase() {
        return appDatabase;
    }

    public ApplicationConfiguration getConfiguration() {
        return configuration;
    }
}
