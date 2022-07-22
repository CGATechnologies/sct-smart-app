package app.sctp.enrollment.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

import app.sctp.enrollment.models.EnrollmentCluster;
import app.sctp.utils.DownloadOptionsDialog;
@Dao
public abstract class EnrollmentClusterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void saveAllIgnore(List<EnrollmentCluster> enrollmentClusters);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveAllReplace(List<EnrollmentCluster> enrollmentClusters);

    public void saveAll(List<EnrollmentCluster> targetedClusters, DownloadOptionsDialog.DownloadOption saveOption) {
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
