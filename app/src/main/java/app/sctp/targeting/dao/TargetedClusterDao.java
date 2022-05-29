package app.sctp.targeting.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

import app.sctp.targeting.models.TargetedCluster;
import app.sctp.utils.DownloadOptionsDialog;

@Dao
public abstract class TargetedClusterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void saveAllIgnore(List<TargetedCluster> targetedClusters);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveAllReplace(List<TargetedCluster> targetedClusters);

    public void saveAll(List<TargetedCluster> targetedClusters, DownloadOptionsDialog.DownloadOption saveOption) {
        switch (saveOption) {
            case Replace:
                saveAllReplace(targetedClusters);
                break;
            case Ignore:
                saveAllIgnore(targetedClusters);
                break;
        }
    }
}
