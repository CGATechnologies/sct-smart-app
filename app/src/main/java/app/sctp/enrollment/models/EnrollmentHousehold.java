package app.sctp.enrollment.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.List;

import app.sctp.core.model.Diffable;
import app.sctp.targeting.models.Individual;
import app.sctp.targeting.models.SelectionStatus;

@Entity(tableName = "enrollment_households", primaryKeys = {"enrollment_session", "household_id"})
public class EnrollmentHousehold implements Diffable {

    @ColumnInfo(name = "enrollment_session", index = true)
    @NonNull
    private Long enrollmentSession;

    @NonNull
    @ColumnInfo(name = "household_id", index = true)
    private Long householdId;

    @ColumnInfo(name = "ml_code", index = true)
    private Long mlCode;

    @ColumnInfo(name = "form_number", index = true)
    private Long formNumber;

    @ColumnInfo(name = "members")
    private Long members;

    @ColumnInfo(name = "district")
    private String district;

    @ColumnInfo(name = "district_code")
    private Long districtCode;

    @ColumnInfo(name = "ta")
    private String ta;

    @ColumnInfo(name = "ta_code")
    private Long taCode;

    @ColumnInfo(name = "cluster")
    private String cluster;

    @ColumnInfo(name = "cluster_code")
    private Long clusterCode;

    @ColumnInfo(name = "zone")
    private String zone;

    @ColumnInfo(name = "zone_code")
    private Long zoneCode;

    @ColumnInfo(name = "village")
    private String village;

    @ColumnInfo(name = "village_code")
    private Long villageCode;

    @ColumnInfo(name = "village_head")
    private String villageHead;

    private SelectionStatus status;

    private Integer ranking;

    @ColumnInfo(name = "status_change_reason")
    @Nullable
    private String statusChangeReason;

    @ColumnInfo(name = "rank_change_reason")
    @Nullable
    private String rankChangeReason;

    @ColumnInfo(name = "created_at")
    private String createdAt;

    @ColumnInfo(name = "household_head")
    private String householdHead;

    @Ignore
    private List<Individual> memberDetails;

    @Override
    public Object getDiffValue() {
        return null;
    }

    @Override
    public boolean contentsSameAs(Diffable diffable) {
        return Diffable.super.contentsSameAs(diffable);
    }
}
