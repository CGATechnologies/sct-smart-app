package app.sctp.targeting.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * <p>This table will be used for joins.</p>
 * <p>Here we use a composite key yo avoid duplication when doing a refresh. This will be combined with {@link androidx.room.OnConflictStrategy#IGNORE}</p>
 */
@Entity(tableName = "targeted_clusters", primaryKeys = {"targeting_session_id", "cluster_code"})
public class TargetedCluster {
    @ColumnInfo(name = "targeting_session_id", index = true)
    @NonNull
    private Long sessionId;

    @ColumnInfo(name = "cluster_code", index = true)
    @NonNull
    private Long clusterCode;

    @NonNull
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(@NonNull Long sessionId) {
        this.sessionId = sessionId;
    }

    @NonNull
    public Long getClusterCode() {
        return clusterCode;
    }

    public void setClusterCode(@NonNull Long clusterCode) {
        this.clusterCode = clusterCode;
    }
}
