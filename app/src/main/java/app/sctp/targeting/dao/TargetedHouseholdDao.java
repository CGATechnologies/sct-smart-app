package app.sctp.targeting.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import app.sctp.targeting.models.HouseholdSelectionResults;
import app.sctp.targeting.models.SelectionCount;
import app.sctp.targeting.models.SelectionStatus;
import app.sctp.targeting.models.TargetedHousehold;

@Dao
public abstract class TargetedHouseholdDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<TargetedHousehold> households);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(TargetedHousehold household);

    @Query("select * from targeted_households where targeting_session = :sessionId")
    public abstract DataSource.Factory<Integer, TargetedHousehold> getBySessionId(Long sessionId);

    @Query("select count(household_id) from targeted_households where targeting_session = :sessionId")
    public abstract int countSessionHouseholds(long sessionId);

    @Query("select household_id householdId, status, ranking rank from targeted_households where targeting_session = :sessionId LIMIT :offset, :count")
    public abstract List<HouseholdSelectionResults> getHouseholdSelectionResultsForSession(Long sessionId, int offset, int count);

    @Deprecated
    @Query("select selected.c as selected, unselected.c as unselected from (select count(household_id) c from targeted_households where targeting_session = :sessionId and status = 'Eligible') selected, (select count(household_id) c from targeted_households where targeting_session = :sessionId and status != 'Eligible') as unselected")
    public abstract SelectionCount sessionHouseholdsSelected(Long sessionId);

    @Query("update targeted_households set status = :status WHERE targeting_session = :sessionId")
    public abstract void updateHouseholdStatus(Long sessionId, SelectionStatus status);
}
