package app.sctp.enrollment.dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.sctp.enrollment.models.Individual;
import app.sctp.utils.DownloadOptionsDialog;
import io.reactivex.Flowable;

public abstract class IndividualDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void saveAllIgnore(List<Individual> individuals);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void saveAllReplace(List<Individual> individuals);

    public void saveAll(List<Individual> individuals, DownloadOptionsDialog.DownloadOption saveOption) {
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
    public abstract Flowable<List<Individual>> getByHouseholdId(Long householdId);
}
