package app.sctp.persistence.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import app.sctp.persistence.DbUtils;

public class V8Migration extends Migration {
    public static final int CURRENT_VERSION = 8;
    public static final int PREVIOUS_VERSION = 7;

    public V8Migration() {
        super(PREVIOUS_VERSION, CURRENT_VERSION);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        // ALTER TABLE targeted_household ADD COLUMN change_status_reason TEXT
        DbUtils.addColumnIfNotExists(database, "targeted_households", "status_change_reason", "TEXT");
        // ALTER TABLE targeted_household ADD COLUMN change_rank_reason TEXT
        DbUtils.addColumnIfNotExists(database, "targeted_households", "rank_change_reason", "TEXT");
    }
}
