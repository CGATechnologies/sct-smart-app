package app.sctp.targeting.repositories;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import androidx.room.Transaction;

import java.util.List;

import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.IndividualDao;
import app.sctp.targeting.dao.TargetedHouseholdDao;
import app.sctp.targeting.models.HouseholdSelectionResults;
import app.sctp.targeting.models.SelectionCount;
import app.sctp.targeting.models.SelectionStatus;
import app.sctp.targeting.models.TargetedHousehold;

public class HouseholdRepository extends BaseRepository {
    private final TargetedHouseholdDao householdDao;
    private final IndividualDao individualDao;

    public HouseholdRepository(@NonNull SctpAppDatabase database) {
        super(database);
        this.householdDao = database.targetedHouseholdDao();
        this.individualDao = database.individualDao();
    }

    @Transaction
    public void save(List<TargetedHousehold> households) {
        super.post(() -> householdDao.insert(households));
    }

    @Transaction
    public void save(TargetedHousehold household) {
        super.post(() -> householdDao.insert(household));
    }

    public DataSource.Factory<Integer, TargetedHousehold> getSessionHouseholds(Long sessionId) {
        return householdDao.getBySessionId(sessionId);
    }

    public List<HouseholdSelectionResults> getHouseholdSelectionResultsForSession(Long sessionId) {
        return householdDao.getHouseholdSelectionResultsForSession(sessionId);
    }

    public SelectionCount getHouseholdSelectionCount(Long sessionId) {
        return householdDao.sessionHouseholdsSelected(sessionId);
    }

    public void updateHouseholdStatus(Long sessionId, SelectionStatus selectionStatus) {
        post(() -> householdDao.updateHouseholdStatus(sessionId, selectionStatus));
    }

    public boolean sessionHouseholdsSelected(Long sessionId) {
        return getHouseholdSelectionCount(sessionId).getUnselected() == 0;
    }

}
