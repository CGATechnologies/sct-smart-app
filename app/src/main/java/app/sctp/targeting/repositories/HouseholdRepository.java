package app.sctp.targeting.repositories;

import static java.util.Collections.singletonList;

import androidx.annotation.NonNull;
import androidx.room.Transaction;

import java.util.List;

import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.HouseholdDao;
import app.sctp.targeting.dao.IndividualDao;
import app.sctp.targeting.models.HouseholdDetailResponse;

public class HouseholdRepository extends BaseRepository {
    private final HouseholdDao householdDao;

    private final IndividualDao individualDao;

    public HouseholdRepository(@NonNull SctpAppDatabase database) {
        super(database);
        this.householdDao = database.householdDao();
        this.individualDao = database.individualDao();
    }

    public void saveAll(List<HouseholdDetailResponse> householdDetailResponses) {
        for (HouseholdDetailResponse household: householdDetailResponses) {
            save(household);
        }
    }

    @Transaction
    public void save(HouseholdDetailResponse householdDetailResponse) {
        this.householdDao.insert(singletonList(householdDetailResponse.getHousehold()));
        this.individualDao.saveAll(householdDetailResponse.getMembers());
    }
}
