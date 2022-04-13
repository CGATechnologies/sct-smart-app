package app.sctp.targeting.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Transaction;

import java.util.List;

import app.sctp.targeting.models.Household;

@Dao
public abstract class HouseholdDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<Household> households);

}
