package app.sctp.persistence;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import app.sctp.core.Converters;
import app.sctp.targeting.dao.HouseholdDao;
import app.sctp.targeting.dao.IndividualDao;
import app.sctp.targeting.dao.LocationDao;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.Household;
import app.sctp.targeting.models.Individual;

@Database(entities = {
        GeoLocation.class,
        Household.class,
        Individual.class,
    },
    version = 2,
    exportSchema = true,
    autoMigrations = {
        @AutoMigration(from = 1, to = 2)
    }
)
@TypeConverters({Converters.class})
public abstract class SctpAppDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();

    public abstract HouseholdDao householdDao();

    public abstract IndividualDao individualDao();
}
