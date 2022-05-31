package app.sctp.targeting.models;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class LocationSelection implements Serializable {
    private Long districtCode;
    private GeoLocation traditionalAuthority;
    private GeoLocation zone;
    private GeoLocation cluster;
    private GeoLocation village;

    public LocationSelection() {
    }

    public LocationSelection(Long districtCode, GeoLocation traditionalAuthority, GeoLocation zone, GeoLocation cluster, GeoLocation village) {
        this.districtCode = districtCode;
        this.traditionalAuthority = traditionalAuthority;
        this.zone = zone;
        this.cluster = cluster;
        this.village = village;
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
    }

    public GeoLocation getTraditionalAuthority() {
        return traditionalAuthority;
    }

    public void setTraditionalAuthority(GeoLocation traditionalAuthority) {
        this.traditionalAuthority = traditionalAuthority;
    }

    public GeoLocation getZone() {
        return zone;
    }

    public void setZone(GeoLocation zone) {
        this.zone = zone;
    }

    public GeoLocation getCluster() {
        return cluster;
    }

    public void setCluster(GeoLocation cluster) {
        this.cluster = cluster;
    }

    public GeoLocation getVillage() {
        return village;
    }

    public void setVillage(GeoLocation village) {
        this.village = village;
    }

    private String locationName(GeoLocation geoLocation) {
        return geoLocation != null ? geoLocation.getName() : "N/A";
    }

    public String getTraditionalAuthorityName() {
        return locationName(traditionalAuthority);
    }

    public String getClusterName() {
        return locationName(cluster);
    }

    public String getZoneName() {
        return locationName(zone);
    }

    public String getVillageName() {
        return locationName(village);
    }

    public boolean allLocationsSet() {
        return getDistrictCode() != null
                && getTraditionalAuthority() != null
                && getZone() != null
                && getVillage() != null
                && getCluster() != null;
    }

    @Nullable
    public static Long codeOrNull(GeoLocation geoLocation) {
        return geoLocation != null ? geoLocation.getCode() : null;
    }

    public static Long codeOrZero(GeoLocation geoLocation) {
        return geoLocation != null ? geoLocation.getCode() : 0L;
    }

    public static String nameOrAsterisks(GeoLocation geoLocation) {
        return geoLocation != null ? geoLocation.getName() : "*";
    }
}
