package app.sctp.targeting.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import app.sctp.core.model.Diffable;

@Entity(tableName = "locations")
public class GeoLocation implements Diffable {

    @PrimaryKey
    private Long id;

    private String name;

    @ColumnInfo(index = true)
    private Long code;

    @ColumnInfo(name = "parent_code")
    private Long parentCode;

    @ColumnInfo(name = "location_type")
    private LocationType locationType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public Long getParentCode() {
        return parentCode;
    }

    public void setParentCode(Long parentCode) {
        this.parentCode = parentCode;
    }

    @Override
    public Object getDiffValue() {
        return getCode();
    }

    @Override
    public String toString() {
        return "GeoLocation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code=" + code +
                ", parentCode=" + parentCode +
                ", locationType=" + locationType +
                '}';
    }
}
