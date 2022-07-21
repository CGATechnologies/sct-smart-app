package app.sctp.enrollment.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import app.sctp.enrollment.models.EnrollmentHousehold;
import app.sctp.targeting.models.HouseholdSelectionResults;
import app.sctp.targeting.models.SelectionCount;
import app.sctp.targeting.models.SelectionStatus;
import app.sctp.utils.DownloadOptionsDialog;

@Dao
public abstract class EnrollmentHouseholdDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertReplace(List<EnrollmentHousehold> households);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertIgnore(List<EnrollmentHousehold> households);

    public void insert(List<EnrollmentHousehold> households, DownloadOptionsDialog.DownloadOption saveOption) {
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
    protected abstract void insertReplace(EnrollmentHousehold household);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insertIgnore(EnrollmentHousehold household);


    public void insert(EnrollmentHousehold household, DownloadOptionsDialog.DownloadOption saveOption) {
        switch (saveOption) {
            case Ignore:
                insertIgnore(household);
                break;
            case Replace:
                insertReplace(household);
                break;
        }
    }

    @Query("select * from enrollment_households where enrollment_session = :sessionId ORDER BY ranking ASC")
    public abstract DataSource.Factory<Integer, EnrollmentHousehold> getBySessionId(Long sessionId);

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
    public abstract void update(EnrollmentHousehold household);
}

}
