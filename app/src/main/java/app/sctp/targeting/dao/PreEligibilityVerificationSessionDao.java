package app.sctp.targeting.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationType;
import app.sctp.targeting.models.PreEligibilityVerificationSession;
import io.reactivex.Flowable;

@Dao
public abstract class PreEligibilityVerificationSessionDao {

    @Query(value = "select * from pev_sessions where districtCode = :districtCode")
    public abstract Flowable<List<PreEligibilityVerificationSession>> getAll(Long districtCode);

    @Query(value = "select * from pev_sessions where districtCode = :districtCode and taCode = :taCode")
    public abstract Flowable<List<PreEligibilityVerificationSession>> getAll(
            Long districtCode,
            Long taCode
    );

    // The LIKE statements here emulate 'FIND_IN_SET'
    @Query(value = "select * from pev_sessions where districtCode = :districtCode and taCode = :taCode and (clusters = :clusterCode OR clusters LIKE '%,' || :clusterCode OR clusters LIKE :clusterCode || ',%' OR clusters LIKE '%,' || :clusterCode || ',%')")
    public abstract Flowable<List<PreEligibilityVerificationSession>> getAll(
            Long districtCode,
            Long taCode,
            Long clusterCode
    );

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void save(List<PreEligibilityVerificationSession> sessions);
}
