package app.sctp.targeting.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import app.sctp.persistence.BaseViewModel;
import app.sctp.targeting.models.Household;
import app.sctp.targeting.repositories.HouseholdRepository;
import app.sctp.utils.LocaleUtils;

public class HouseholdViewModel extends BaseViewModel {
    static class HouseholdListingKey {
        private String search;
        private Long sessionId;

        public HouseholdListingKey() {
        }

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

    private MutableLiveData<HouseholdListingKey> searchLiveData;
    private final HouseholdRepository householdRepository;
    private LiveData<PagedList<Household>> householdsLiveData;

    public HouseholdViewModel(@NonNull Application application) {
        super(application);
        householdRepository = getRepository(HouseholdRepository.class);
        searchLiveData = new MutableLiveData<>();

        householdsLiveData = Transformations.switchMap(
                searchLiveData,
                input -> {
                    if (!LocaleUtils.hasText(input.search)) {
                        return new LivePagedListBuilder<>(
                                householdRepository.getBySessionId(input.sessionId),
                                PAGE_SIZE
                        ).build();
                    } else {
                        return new LivePagedListBuilder<>(
                                householdRepository.search(input.sessionId, input.search),
                                PAGE_SIZE
                        ).build();
                    }
                }
        );
    }

    public void updateFilterParameters(Long sessionId, String search) {
        searchLiveData.postValue(new HouseholdListingKey(sessionId, search));
    }

    public void save(List<Household> households) {
        householdRepository.save(households);
    }

    public LiveData<PagedList<Household>> getHouseholdsLiveData() {
        return householdsLiveData;
    }
}
