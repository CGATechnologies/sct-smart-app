package app.sctp.persistence;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.dao.LocationDao;

@Database(entities = {GeoLocation.class}, version = 1, exportSchema = true)
public abstract class SctpAppDatabase extends RoomDatabase {
    public abstract LocationDao locationDao();
}
