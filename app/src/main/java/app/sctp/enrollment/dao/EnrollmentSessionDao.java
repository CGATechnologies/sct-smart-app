package app.sctp.enrollment.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import app.sctp.enrollment.models.EnrollmentSession;
import app.sctp.targeting.models.TargetingSession;
import app.sctp.utils.DownloadOptionsDialog;

@Dao
public abstract class EnrollmentSessionDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<EnrollmentSession> sessions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(EnrollmentSession session);

    @Query("select * from enrollment_sessions es " +
            "where es.districtCode = :districtCode " +
            "and (:taCode IS NULL OR es.taCode = :taCode) " +
            "and (:clusterCode IS NULL or (select exists(select 1 from targeted_clusters tc where tc.targeting_session_id = es.id and tc.cluster_code = :clusterCode))) " +
            "and (:zoneCode is null or (select exists(select 1 from targeted_zones tz where tz.targeting_session_id = es.id and tz.code = :zoneCode))) " +
            "and (:villageCode is null or (select exists(select 1 from targeted_villages tv where tv.targeting_session_id = es.id and tv.code = :villageCode))) "
    )
    protected abstract DataSource.Factory<Integer, TargetingSession> getByLocation(
            Long districtCode,
            Long taCode,
            Long clusterCode,
            Long zoneCode,
            Long villageCode,
            TargetingSession.MeetingPhase meetingPhase
    );

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    protected abstract void saveAllReplace(List<EnrollmentSession> sessions);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Transaction
    protected abstract void saveAllIgnore(List<EnrollmentSession> sessions);


    public void saveAll(List<EnrollmentSession> sessions, DownloadOptionsDialog.DownloadOption saveOption) {
        switch (saveOption) {
            case Ignore:
                saveAllIgnore(sessions);
                break;
            case Replace:
                saveAllReplace(sessions);
                break;
        }
    }

    @Update
    public abstract void update(EnrollmentSession session);

}
