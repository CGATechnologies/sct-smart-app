package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

/**
 * Because {@link TargetingSession} only stop at cluster level, this view will list zones
 * under that targeting session for better filtering
 */
@DatabaseView(
        viewName = "targeted_zones",
        value = "select zone.code, tc.targeting_session_id " +
                "from locations zone " +
                "join locations cluster on cluster.code = zone.parent_code " +
                "join targeted_clusters tc on tc.cluster_code = cluster.code "
)
public class TargetedZone {
    @ColumnInfo(name = "targeting_session_id")
    private Long sessionId;

    @ColumnInfo(name = "code")
    private Long zoneCode;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(Long zoneCode) {
        this.zoneCode = zoneCode;
    }
}
