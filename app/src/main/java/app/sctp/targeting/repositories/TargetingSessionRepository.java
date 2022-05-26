package app.sctp.targeting.repositories;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import java.util.List;

import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.TargetingSessionDao;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.TargetingSession;

public class TargetingSessionRepository extends BaseRepository {
    private final TargetingSessionDao dao;

    public TargetingSessionRepository(@NonNull SctpAppDatabase database) {
        super(database);
        dao = database.targetingSessionDao();
    }

    public void saveAll(List<TargetingSession> sessions) {
        post(() -> dao.insert(sessions));
    }

    public DataSource.Factory<Integer, TargetingSession> getSecondCommunityMeetingSessions(LocationSelection location) {
        return dao.getSecondCommunityMeetingSessions(location);
    }

    public DataSource.Factory<Integer, TargetingSession> getDistrictMeetingSessions(LocationSelection location) {
        return dao.getDistrictMeetingSessions(location);
    }
}
