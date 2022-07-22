package app.sctp.enrollment.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import app.sctp.enrollment.models.EnrollmentHousehold;
import app.sctp.enrollment.models.HouseholdSelectionResults;
import app.sctp.enrollment.repositories.HouseholdRepository;
import app.sctp.persistence.BaseViewModel;
import app.sctp.utils.DownloadOptionsDialog;

public class HouseholdViewModel  extends BaseViewModel {

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
    private final MutableLiveData<app.sctp.enrollment.viewmodels.HouseholdViewModel.HouseholdListingKey> searchLiveData;
    private final LiveData<PagedList<EnrollmentHousehold>> householdsLiveData;

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

    public void save(List<EnrollmentHousehold> households, DownloadOptionsDialog.DownloadOption dopt) {
        householdRepository.save(households, dopt);
    }

    public void save(EnrollmentHousehold household, DownloadOptionsDialog.DownloadOption dltop) {
        householdRepository.save(household, dltop);
    }

    private void setSessionId(Long sessionId) {
        searchLiveData.postValue(new HouseholdViewModel.HouseholdListingKey(sessionId, null));
    }

    public LiveData<PagedList<EnrollmentHousehold>> getSessionHouseholds(Long sessionId) {
        setSessionId(sessionId);
        return householdsLiveData;
    }

    public int getHouseholdCount(long sessionId) {
        return householdRepository.getHouseholdCount(sessionId);
    }

    public List<HouseholdSelectionResults> getHouseholdSelectionResultsForSession(Long sessionId, int offset, int count) {
        return householdRepository.getHouseholdSelectionResultsForSession(sessionId, offset, count);
    }

    /*public void synchronizeHouseholds(
            Long sessionId,
            EnrollmentService service,
            EnrollmentSession.MeetingPhase phase,
            HouseholdRepository.SyncListener listener) {
        householdRepository.synchronizeHouseholds(sessionId, service, phase, listener);
    }*/

    public void update(EnrollmentHousehold household) {
        householdRepository.update(household);
    }
}