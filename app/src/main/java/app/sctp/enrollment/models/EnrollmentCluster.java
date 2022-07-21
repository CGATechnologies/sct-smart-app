package app.sctp.enrollment.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity(tableName = "enrollment_clusters", primaryKeys = {"enrollment_session_id", "cluster_code"})
public class EnrollmentCluster {
    @ColumnInfo(name = "enrollment_session_id", index = true)
    @NonNull
    private Long sessionId;

    @ColumnInfo(name = "cluster_code", index = true)
    @NonNull
    private Long clusterCode;

    public EnrollmentCluster(long clusterCode, long sessionId) {
        this.sessionId = sessionId;
        this.clusterCode = clusterCode;
    }

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

    public static List<EnrollmentCluster> of(Set<Long> clusterCodes, Long sessionId) {
        final LinkedList<EnrollmentCluster> clusters = new LinkedList<>();
        for (Long code : clusterCodes) {
            clusters.add(new EnrollmentCluster(code, sessionId));
        }
        return clusters;
    }
}
