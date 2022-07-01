package app.sctp.targeting.repositories;

import androidx.paging.DataSource;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.ObservableDatabaseCall;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.LocationDao;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationType;
import io.reactivex.Flowable;

public class LocationRepository extends BaseRepository {
    private final LocationDao locationDao;

    public LocationRepository(SctpAppDatabase appDatabase) {
        super(appDatabase);
        locationDao = appDatabase.locationDao();
    }

    public Flowable<List<GeoLocation>> getLocationsByParentCode(Long parentCode) {
        return locationDao.getLocationsByParentCode(parentCode);
    }

    public void save(List<GeoLocation> locationList) {
        post(() -> locationDao.saveLocations(locationList));
    }

    public DataSource.Factory<Integer, GeoLocation> getByLocation(long parentCode, String searchValue) {
        return locationDao.getByParentCode(parentCode, searchValue);
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
