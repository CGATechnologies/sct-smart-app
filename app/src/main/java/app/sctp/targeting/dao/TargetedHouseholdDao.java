package app.sctp.targeting.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import app.sctp.targeting.models.HouseholdSelectionResults;
import app.sctp.targeting.models.SelectionCount;
import app.sctp.targeting.models.SelectionStatus;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.utils.DownloadOptionsDialog;

@Dao
public abstract class TargetedHouseholdDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertReplace(List<TargetedHousehold> households);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertIgnore(List<TargetedHousehold> households);

    public void insert(List<TargetedHousehold> households, DownloadOptionsDialog.DownloadOption saveOption) {
        switch (saveOption) {
            case Ignore:
                insertIgnore(households);
                break;
            case Replace:
                insertReplace(households);
                break;
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertReplace(TargetedHousehold household);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insertIgnore(TargetedHousehold household);


    public void insert(TargetedHousehold household, DownloadOptionsDialog.DownloadOption saveOption) {
        switch (saveOption) {
            case Ignore:
                insertIgnore(household);
                break;
            case Replace:
                insertReplace(household);
                break;
        }
    }

    @Query("select * from targeted_households where targeting_session = :sessionId ORDER BY ranking ASC")
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

    @Update
    public abstract void update(TargetedHousehold household);
}
