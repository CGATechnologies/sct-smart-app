package app.sctp.targeting.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import app.sctp.persistence.BaseViewModel;
import app.sctp.persistence.ObservableDatabaseCall;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationType;
import app.sctp.targeting.repositories.LocationRepository;
import io.reactivex.Flowable;

public class LocationViewModel extends BaseViewModel {

    private final LocationRepository locationRepository;
    private final MutableLiveData<SearchKey> searchLiveData;
    private final LiveData<PagedList<GeoLocation>> searchResults;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        locationRepository = getRepository(LocationRepository.class);
        searchLiveData = new MutableLiveData<>();
        searchResults = Transformations.switchMap(
                searchLiveData,
                key -> new LivePagedListBuilder<>(
                        locationRepository.getByLocation(key.parentCode, key.searchValue),
                        PAGE_SIZE
                ).build()
        );
    }

    public Flowable<List<GeoLocation>> getSubLocationsByParentCode(Long parentCode) {
        return locationRepository.getLocationsByParentCode(parentCode);
    }

    public void save(List<GeoLocation> locations) {
        locationRepository.save(locations);
    }

    public ObservableDatabaseCall<List<GeoLocation>> getLocationsByType(LocationType locationType) {
        return locationRepository.getLocationsByType(locationType);
    }

    public LiveData<PagedList<GeoLocation>> getByParentCode(){
        return searchResults;
    }

    public void updateParentCodeAndSearchValue(long parentCode, String searchValue) {
        searchLiveData.postValue(new SearchKey(parentCode, searchValue));
    }

    private static class SearchKey {
        private final long parentCode;
        private final String searchValue;

        public SearchKey(long parentCode, String searchValue) {
            this.parentCode = parentCode;
            this.searchValue = searchValue;
        }
    }
}
