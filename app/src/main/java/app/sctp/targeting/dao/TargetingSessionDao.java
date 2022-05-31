package app.sctp.targeting.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.TargetingSession;
import app.sctp.utils.DownloadOptionsDialog;

@Dao
public abstract class TargetingSessionDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<TargetingSession> sessions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(TargetingSession session);

    @Query("select * from targeting_sessions ts " +
            "where ts.meetingPhase = :meetingPhase " +
            "and ts.districtCode = :districtCode " +
            "and (:taCode IS NULL OR ts.taCode = :taCode) " +
            "and (:clusterCode IS NULL or (select exists(select 1 from targeted_clusters tc where tc.targeting_session_id = ts.id and tc.cluster_code = :clusterCode))) " +
            "and (:zoneCode is null or (select exists(select 1 from targeted_zones tz where tz.targeting_session_id = ts.id and tz.code = :zoneCode))) " +
            "and (:villageCode is null or (select exists(select 1 from targeted_villages tv where tv.targeting_session_id = ts.id and tv.code = :villageCode))) " +
            "and meetingPhase != 'completed'")
    protected abstract DataSource.Factory<Integer, TargetingSession> getByLocation(
            Long districtCode,
            Long taCode,
            Long clusterCode,
            Long zoneCode,
            Long villageCode,
            TargetingSession.MeetingPhase meetingPhase
    );

    public DataSource.Factory<Integer, TargetingSession> getSecondCommunityMeetingSessions(LocationSelection selection) {
        return getByLocation(
                selection.getDistrictCode(),
                LocationSelection.codeOrNull(selection.getTraditionalAuthority()),
                LocationSelection.codeOrNull(selection.getCluster()),
                LocationSelection.codeOrNull(selection.getZone()),
                LocationSelection.codeOrNull(selection.getVillage()),
                TargetingSession.MeetingPhase.second_community_meeting
        );
    }

    public DataSource.Factory<Integer, TargetingSession> getDistrictMeetingSessions(LocationSelection selection) {
        return getByLocation(
                selection.getDistrictCode(),
                LocationSelection.codeOrNull(selection.getTraditionalAuthority()),
                LocationSelection.codeOrNull(selection.getCluster()),
                LocationSelection.codeOrNull(selection.getZone()),
                LocationSelection.codeOrNull(selection.getVillage()),
                TargetingSession.MeetingPhase.district_meeting
        );
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    protected abstract void saveAllReplace(List<TargetingSession> sessions);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Transaction
    protected abstract void saveAllIgnore(List<TargetingSession> sessions);


    public void saveAll(List<TargetingSession> sessions, DownloadOptionsDialog.DownloadOption saveOption) {
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
    public abstract void update(TargetingSession session);
}
