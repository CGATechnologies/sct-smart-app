package app.sctp.enrollment.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.sctp.enrollment.models.EnrollmentIndividual;
import app.sctp.utils.DownloadOptionsDialog;
import io.reactivex.Flowable;
@Dao
public abstract class EnrollmentIndividualDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void saveAllIgnore(List<EnrollmentIndividual> individuals);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void saveAllReplace(List<EnrollmentIndividual> individuals);

    public void saveAll(List<EnrollmentIndividual> individuals, DownloadOptionsDialog.DownloadOption saveOption) {
        switch (saveOption) {
            case Replace:
                saveAllReplace(individuals);
                break;
            case Ignore:
                saveAllIgnore(individuals);
                break;
        }
    }

    @Query(value = "select * from individual where householdId = :householdId")
    public abstract Flowable<List<EnrollmentIndividual>> getByHouseholdId(Long householdId);
}
