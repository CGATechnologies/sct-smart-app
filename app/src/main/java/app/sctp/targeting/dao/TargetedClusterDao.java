package app.sctp.targeting.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import app.sctp.targeting.models.Individual;
import app.sctp.targeting.models.TargetedCluster;
import io.reactivex.Flowable;

@Dao
public abstract class TargetedClusterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void saveAll(List<TargetedCluster> targetedClusters);
}
