package app.sctp.persistence.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import app.sctp.persistence.DbUtils;

public final class V7Migration extends Migration {
    public static final int CURRENT_VERSION = 7;
    public static final int PREVIOUS_VERSION = 6;

    public V7Migration() {
        super(PREVIOUS_VERSION, CURRENT_VERSION);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        // ALTER TABLE targeted_household ADD COLUMN reason TEXT
        DbUtils.addColumnIfNotExists(database, "targeted_households", "change_reason", "TEXT");
    }
}
