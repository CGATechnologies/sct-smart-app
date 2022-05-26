package app.sctp.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import app.sctp.targeting.dao.IndividualDao;
import app.sctp.targeting.dao.LocationDao;
import app.sctp.targeting.dao.TargetedClusterDao;
import app.sctp.targeting.dao.TargetedHouseholdDao;
import app.sctp.targeting.dao.TargetingSessionDao;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.Individual;
import app.sctp.targeting.models.TargetedCluster;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.targeting.models.TargetedVillage;
import app.sctp.targeting.models.TargetedZone;
import app.sctp.targeting.models.TargetingSession;

@Database(
        entities = {
                GeoLocation.class,
                Individual.class,
                TargetingSession.class,
                TargetedCluster.class,
                TargetedHousehold.class
        },
        views = {
                TargetedZone.class,
                TargetedVillage.class
        },
        version = SctpAppDatabase.VERSION/*,
        autoMigrations = {
                @AutoMigration(from = 1, to = 2),
                @AutoMigration(from = 2, to = 3),
                @AutoMigration(from = 3, to = 4*//*, spec = SctpAppDatabase.V4AutoMigrationSpec.class*//*)
        }*/
)
@TypeConverters({Converters.class})
public abstract class SctpAppDatabase extends RoomDatabase {
    public static final int VERSION = 6;

    public abstract LocationDao locationDao();

    public abstract IndividualDao individualDao();

    public abstract TargetedClusterDao targetedClusterDao();

    public abstract TargetingSessionDao targetingSessionDao();

    public abstract TargetedHouseholdDao targetedHouseholdDao();
}
