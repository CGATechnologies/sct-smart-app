package app.sctp.targeting.repositories;

import androidx.annotation.NonNull;
import androidx.room.Transaction;

import java.util.List;

import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.IndividualDao;
import app.sctp.targeting.models.Individual;
import io.reactivex.Flowable;

public class IndividualRepository extends BaseRepository {
    private final IndividualDao individualDao;

    public IndividualRepository(@NonNull SctpAppDatabase database) {
        super(database);
        this.individualDao = database.individualDao();
    }

    @Transaction
    public void save(List<Individual> individuals) {
        super.post(() -> individualDao.saveAll(individuals));
    }

   public Flowable<List<Individual>> getByHouseHoldId(Long householdId) {
        return individualDao.getByHouseholdId(householdId);
    }
}
