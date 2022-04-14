package app.sctp.targeting.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;
import androidx.room.Transaction;

import java.util.List;

import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.HouseholdDao;
import app.sctp.targeting.dao.IndividualDao;
import app.sctp.targeting.models.Household;
import app.sctp.targeting.models.Individual;

public class HouseholdRepository extends BaseRepository {
    private final HouseholdDao householdDao;
    private final IndividualDao individualDao;

    public HouseholdRepository(@NonNull SctpAppDatabase database) {
        super(database);
        this.householdDao = database.householdDao();
        this.individualDao = database.individualDao();
    }

    @Transaction
    public void save(List<Household> households) {
        super.post(() -> householdDao.insert(households));
    }

    @Transaction
    public void save(Household household) {
        super.post(() -> householdDao.insert(household));
    }

    public DataSource.Factory<Integer, Household> search(Long sessionId, String search) {
        return householdDao.search(sessionId, search);
    }

    public DataSource.Factory<Integer, Household> getBySessionId(Long sessionId) {
        return householdDao.getBySessionId(sessionId);
    }
}
