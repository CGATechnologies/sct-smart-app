package app.sctp.targeting.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.sctp.persistence.DatabaseCallDataObserver;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationType;
import io.reactivex.Flowable;

@Dao
public abstract class LocationDao {

    @Query(value = "select * from locations where parent_code = :parentCode")
    public abstract Flowable<List<GeoLocation>> getLocationsByParentCode(Long parentCode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveLocations(List<GeoLocation> locations);

    @Query(value = "select * from locations where location_type = :locationType")
    public abstract List<GeoLocation> getByType(LocationType locationType);
}
