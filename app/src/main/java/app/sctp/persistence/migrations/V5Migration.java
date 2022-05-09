package app.sctp.persistence.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import app.sctp.persistence.DbUtils;

public final class V5Migration extends Migration {
    public static final int CURRENT_VERSION = 5;
    public static final int PREVIOUS_VERSION = 4;

    public V5Migration() {
        super(PREVIOUS_VERSION, CURRENT_VERSION);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        DbUtils.addColumnIfNotExists(database, "households", "ranking", "INTEGER");
        DbUtils.addColumnIfNotExists(database, "households", "selection", "TEXT");
        DbUtils.addColumnIfNotExists(database, "households", "lastRankingDate", "TEXT");
    }
}
