package app.sctp.targeting.repositories;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.TargetedClusterDao;
import app.sctp.targeting.models.TargetedCluster;

public class TargetedClusterRepository extends BaseRepository {
    private final TargetedClusterDao dao;

    public TargetedClusterRepository(@NonNull SctpAppDatabase database) {
        super(database);
        dao = database.targetedClusterDao();
    }

    public void saveAll(List<TargetedCluster> targetedClusters) {
        post(() -> dao.saveAll(targetedClusters));
    }
}
