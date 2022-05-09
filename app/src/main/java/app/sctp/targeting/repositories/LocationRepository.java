package app.sctp.targeting.repositories;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.ObservableDatabaseCall;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.LocationDao;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationType;
import app.sctp.targeting.services.TargetingService;
import io.reactivex.Flowable;

public class LocationRepository extends BaseRepository {
    private LocationDao locationDao;
    private TargetingService targetingService;
    private MutableLiveData<PagedList<GeoLocation>> locationLiveData;


    public LocationRepository(SctpAppDatabase appDatabase) {
        super(appDatabase);
        locationDao = appDatabase.locationDao();
        locationLiveData = new MediatorLiveData<>();
    }

    public Flowable<List<GeoLocation>> getLocationsByParentCode(Long parentCode) {
        return locationDao.getLocationsByParentCode(parentCode);
    }

    public void save(List<GeoLocation> locationList) {
        post(() -> locationDao.saveLocations(locationList));
    }

    public ObservableDatabaseCall<List<GeoLocation>> getLocationsByType(LocationType locationType) {
        final AtomicReference<ObservableDatabaseCall<List<GeoLocation>>> observable
                = new AtomicReference<>();
        observable.set(newObservableDatabaseCall((Runnable) () -> {
            try {
                onBefore(observable.get());
                onSuccess(observable.get(), locationDao.getByType(locationType));
            } catch (Exception exception) {
                onError(observable.get(), exception);
            } finally {
                onAfter(observable.get());
            }
        }));
        return observable.get();
    }

}
