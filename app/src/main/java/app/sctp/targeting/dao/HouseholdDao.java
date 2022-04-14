package app.sctp.targeting.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import app.sctp.targeting.models.Household;

@Dao
public abstract class HouseholdDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<Household> households);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Household household);

    @Query("select * from households where cbtSessionId = :sessionId AND (mlCode = :search or ubrCode LIKE :search)")
    protected abstract DataSource.Factory<Integer, Household> search0(Long sessionId, String search);

    @Query("select * from households where cbtSessionId = :sessionId")
    public abstract DataSource.Factory<Integer, Household> getBySessionId(Long sessionId);

    public DataSource.Factory<Integer, Household> search(Long sessionId, String search) {
        search = search.replace("%", "\\%");
        return search0(sessionId, "%" + search + "%");
    }
}
