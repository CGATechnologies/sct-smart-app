package app.sctp.targeting.models;

import java.util.List;

public class LocationDownloadResponse {
    private List<GeoLocation> list;

    public List<GeoLocation> getList() {
        return list;
    }

    public void setList(List<GeoLocation> list) {
        this.list = list;
    }
}
