package app.sctp.enrollment.repositories;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import androidx.room.Transaction;

import java.util.List;

import app.sctp.enrollment.dao.EnrollmentHouseholdDao;
import app.sctp.enrollment.models.EnrollmentHousehold;
import app.sctp.enrollment.models.EnrollmentSession;
import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.IndividualDao;
import app.sctp.targeting.models.HouseholdSelectionResults;
import app.sctp.targeting.models.SelectionCount;
import app.sctp.targeting.models.SelectionStatus;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.utils.DownloadOptionsDialog;

public class HouseholdRepository extends BaseRepository {
    private final Handler handler;
    private final IndividualDao individualDao;
    private final EnrollmentHouseholdDao householdDao;

    public HouseholdRepository(@NonNull SctpAppDatabase database) {
        super(database);
        this.handler = new Handler(Looper.getMainLooper());
        this.householdDao = database.enrollmentHouseholdDao();
        this.individualDao = database.individualDao();
    }

    @Transaction
    public void save(List<EnrollmentHousehold> households, DownloadOptionsDialog.DownloadOption dopt) {
        super.post(() -> householdDao.insert(households, dopt));
    }

    @Transaction
    public void save(EnrollmentHousehold household, DownloadOptionsDialog.DownloadOption dlopt) {
        super.post(() -> householdDao.insert(household, dlopt));
    }

    public DataSource.Factory<Integer, EnrollmentHousehold> getSessionHouseholds(Long sessionId) {
        return householdDao.getBySessionId(sessionId);
    }

    public int getHouseholdCount(Long sessionId) {
        return householdDao.countSessionHouseholds(sessionId);
    }

    public List<HouseholdSelectionResults> getHouseholdSelectionResultsForSession(Long sessionId, int offset, int count) {
        return householdDao.getHouseholdSelectionResultsForSession(sessionId, offset, count);
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
    public void update(EnrollmentSession household) {
        post(() -> householdDao.update(household));
    }

}
