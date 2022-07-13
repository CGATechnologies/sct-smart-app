package app.sctp.persistence;

import androidx.room.migration.Migration;

import app.sctp.persistence.migrations.V5Migration;
import app.sctp.persistence.migrations.V6Migration;
import app.sctp.persistence.migrations.V7Migration;
import app.sctp.persistence.migrations.V8Migration;

public final class Migrations {
    public static final Migration[] MIGRATIONS = {
            new V5Migration(),
            new V6Migration(),
            new V7Migration(),
            new V8Migration(),
    };
}
