package app.sctp.targeting.dao;

import android.database.DatabaseUtils;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.util.DBUtil;

import java.util.List;

import app.sctp.app.SctApplication;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.LocationType;
import app.sctp.targeting.models.PreEligibilityVerificationSession;
import app.sctp.targeting.models.SessionView;
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

    @Query(value = "select * from SessionView where districtCode = :districtCode and coalesce(:taCode, taCode) = taCode")
    protected abstract Flowable<List<SessionView>> getSessionViews(
            Long districtCode,
            Long taCode
    );

    // The LIKE statements here emulate 'FIND_IN_SET'
    @Query(value = "select * from SessionView where districtCode = :districtCode and coalesce(:taCode, taCode) = taCode and (clusters = :clusterCode OR clusters LIKE '%,' || :clusterCode OR clusters LIKE :clusterCode || ',%' OR clusters LIKE '%,' || :clusterCode || ',%')")
    protected abstract Flowable<List<SessionView>> getSessionViews(
            Long districtCode,
            Long taCode,
            Long clusterCode
    );

    public Flowable<List<SessionView>> getSessionViewsByLocation(LocationSelection selection) {
        if (selection.getCluster() != null) {
            return getSessionViews(selection.getDistrictCode(), selection.getTraditionalAuthority().getCode(), selection.getCluster().getCode());
        }
        return getSessionViews(selection.getDistrictCode(), selection.getTraditionalAuthority().getCode());
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void save(List<PreEligibilityVerificationSession> sessions);
}
