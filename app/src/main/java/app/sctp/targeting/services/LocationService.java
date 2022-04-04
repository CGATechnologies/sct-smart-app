package app.sctp.targeting.services;


import java.util.List;

import app.sctp.core.net.api.ext.ApiCall;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationDownloadResponse;
import app.sctp.targeting.models.LocationType;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationService {

    @GET("/locations/get-by-type")
    ApiCall<LocationDownloadResponse> getLocationsByType(@Query("type") LocationType locationType);
}
