package app.sctp.persistence;

import androidx.room.migration.Migration;

import app.sctp.persistence.migrations.V5Migration;
import app.sctp.persistence.migrations.V6Migration;

public final class Migrations {
    public static final Migration[] MIGRATIONS = {
            new V5Migration(),
            new V6Migration()
    };
}
