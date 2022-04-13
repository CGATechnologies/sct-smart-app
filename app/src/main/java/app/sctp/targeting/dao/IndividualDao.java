package app.sctp.targeting.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

import app.sctp.targeting.models.Individual;

@Dao
public abstract class IndividualDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveAll(List<Individual> individuals);
}
