package app.sctp.targeting.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationType;
import app.sctp.utils.LocaleUtils;
import io.reactivex.Flowable;

@Dao
public abstract class LocationDao {

    @Query(value = "select * from locations where parent_code = :parentCode")
    public abstract Flowable<List<GeoLocation>> getLocationsByParentCode(Long parentCode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveLocations(List<GeoLocation> locations);

    @Query(value = "select * from locations where location_type = :locationType ORDER BY name ASC")
    public abstract List<GeoLocation> getByType(LocationType locationType);

    @Query(value = "select * from locations where parent_code = :parentCode ORDER BY name ASC")
    protected abstract DataSource.Factory<Integer, GeoLocation> getByParentCode0(long parentCode);

    @Query(value = "select * from locations where parent_code = :parentCode AND ((name LIKE '%' || :search || '%') OR (code LIKE '%' || :search || '%')) ORDER BY name ASC")
    protected abstract DataSource.Factory<Integer, GeoLocation> getByParentCode0(long parentCode, String search);

    public DataSource.Factory<Integer, GeoLocation> getByParentCode(long parentCode, String search) {
        if (LocaleUtils.hasText(search)) {
            return getByParentCode0(parentCode, search.replaceAll("%", ""));
        }
        return getByParentCode0(parentCode);
    }
}
