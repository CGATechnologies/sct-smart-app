package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;

/**
 * Because {@link TargetingSession} only stop at cluster level, this view will list
 * villages under each targeting session for better filtering
 */
@DatabaseView(
        viewName = "targeted_villages",
        value = "select village.code, tz.targeting_session_id " +
                "from locations village " +
                "join targeted_zones tz on tz.code = village.parent_code "
)
public class TargetedVillage {
    @ColumnInfo(name = "targeting_session_id")
    private Long sessionId;

    @ColumnInfo(name = "code")
    private Long villageCode;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getVillageCode() {
        return villageCode;
    }

    public void setVillageCode(Long villageCode) {
        this.villageCode = villageCode;
    }
}
