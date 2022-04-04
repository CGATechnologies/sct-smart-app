package app.sctp.targeting.services;

import java.util.List;

import app.sctp.core.net.api.ext.ApiCall;
import app.sctp.targeting.models.GeoLocation;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TargetingService {
    @GET("/district-locations/{district}")
    ApiCall<List<GeoLocation>> getLocationsUnderDistrict(@Path("district") Long districtId);
}
