package app.sctp.targeting.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.persistence.BaseViewModel;
import app.sctp.persistence.ObservableDatabaseCall;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationType;
import app.sctp.targeting.repositories.LocationRepository;
import io.reactivex.Flowable;

public class LocationViewModel extends BaseViewModel {
    private final LocationRepository locationRepository;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        locationRepository = getRepository(LocationRepository.class);
    }

    public Flowable<List<GeoLocation>> getSubLocationsByParentCode(Long parentCode){
        return locationRepository.getLocationsByParentCode(parentCode);
    }

    public void save(List<GeoLocation> locations){
        locationRepository.save(locations);
    }

    public ObservableDatabaseCall<List<GeoLocation>> getLocationsByType(LocationType locationType) {
        return locationRepository.getLocationsByType(locationType);
    }
}
