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
import app.sctp.targeting.models.HouseholdSelectionResults;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.targeting.repositories.HouseholdRepository;

public class HouseholdViewModel extends BaseViewModel {

    static class HouseholdListingKey {
        private String search;
        private Long sessionId;

        public HouseholdListingKey(Long sessionId, String search) {
            this.sessionId = sessionId;
            this.search = search;
        }

        public Long getSessionId() {
            return sessionId;
        }

        public void setSessionId(Long sessionId) {
            this.sessionId = sessionId;
        }

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }
    }

    private final HouseholdRepository householdRepository;
    private final MutableLiveData<HouseholdListingKey> searchLiveData;
    private final LiveData<PagedList<TargetedHousehold>> householdsLiveData;

    public HouseholdViewModel(@NonNull Application application) {
        super(application);
        householdRepository = getRepository(HouseholdRepository.class);
        searchLiveData = new MutableLiveData<>();

        householdsLiveData = Transformations.switchMap(
                searchLiveData,
                input -> new LivePagedListBuilder<>(
                        householdRepository.getSessionHouseholds(input.sessionId),
                        PAGE_SIZE
                ).build()
        );
    }

    public void save(List<TargetedHousehold> households) {
        householdRepository.save(households);
    }

    public void save(TargetedHousehold household) {
        householdRepository.save(household);
    }

    private void setSessionId(Long sessionId) {
        searchLiveData.postValue(new HouseholdListingKey(sessionId, null));
    }

    public LiveData<PagedList<TargetedHousehold>> getSessionHouseholds(Long sessionId) {
        setSessionId(sessionId);
        return householdsLiveData;
    }

    public List<HouseholdSelectionResults> getHouseholdSelectionResultsForSession(Long sessionId) {
        return householdRepository.getHouseholdSelectionResultsForSession(sessionId);
    }

}
