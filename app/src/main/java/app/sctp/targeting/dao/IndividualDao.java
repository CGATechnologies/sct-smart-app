package app.sctp.targeting.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.sctp.targeting.models.Individual;
import io.reactivex.Flowable;

@Dao
public abstract class IndividualDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveAll(List<Individual> individuals);

    @Query(value = "select * from individual where householdId = :householdId")
    public abstract Flowable<List<Individual>> getByHouseholdId(Long householdId);
}
