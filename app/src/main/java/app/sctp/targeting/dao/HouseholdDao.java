package app.sctp.targeting.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import app.sctp.targeting.models.Household;
import app.sctp.targeting.models.HouseholdSelectionResults;
import app.sctp.targeting.models.SelectionCount;
import app.sctp.targeting.models.SelectionStatus;

@Dao
public abstract class HouseholdDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<Household> households);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Household household);

    // TODO Revise query. Use concantenation
    @Query("select * from households where sessionId = :sessionId AND (mlCode = :search or formNumber LIKE :search)")
    protected abstract DataSource.Factory<Integer, Household> search0(Long sessionId, String search);

    @Query("select * from households where sessionId = :sessionId")
    public abstract DataSource.Factory<Integer, Household> getBySessionId(Long sessionId);

    public DataSource.Factory<Integer, Household> search(Long sessionId, String search) {
        search = search.replace("%", "\\%");
        return search0(sessionId, "%" + search + "%");
    }

    @Query("select * from households where sessionId = :sessionId")
    public abstract DataSource.Factory<Integer, Household> getSessionHouseholds(Long sessionId);

    @Query("select householdId, selection, ranking from households where sessionId = :sessionId")
    public abstract List<HouseholdSelectionResults> getHouseholdSelectionResultsForSession(Long sessionId);

    @Query("select selected.c as selected, unselected.c as unselected from (select count(householdId) c from households where sessionId = :sessionId and selection = 'Eligible') selected, (select count(householdId) c from households where sessionId = :sessionId and selection != 'Eligible') as unselected")
    public abstract SelectionCount sessionHouseholdsSelected(Long sessionId);

    @Query("update households set selection = :status WHERE sessionId = :sessionId")
    public abstract void updateHouseholdStatus(Long sessionId, SelectionStatus status);
}
