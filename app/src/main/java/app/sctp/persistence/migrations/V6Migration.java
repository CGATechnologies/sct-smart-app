package app.sctp.persistence.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import app.sctp.persistence.DbUtils;

public final class V6Migration extends Migration {
    public static final int CURRENT_VERSION = 6;
    public static final int PREVIOUS_VERSION = 5;

    public V6Migration() {
        super(PREVIOUS_VERSION, CURRENT_VERSION);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        // empty migration
    }
}
