package app.sctp.enrollment.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import app.sctp.enrollment.models.EnrollmentSession;
import app.sctp.enrollment.repositories.EnrollmentSessionRepository;
import app.sctp.enrollment.services.EnrollmentService;
import app.sctp.persistence.BaseViewModel;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.utils.DownloadOptionsDialog;

public class EnrollmentSessionViewModel extends BaseViewModel {

    private final EnrollmentSessionRepository repository;
    private MutableLiveData<EnrollmentSessionViewModel.SessionListingKey> searchLiveData;
    private LiveData<PagedList<EnrollmentSession>> sessionLiveData;
    public EnrollmentSessionViewModel(@NonNull Application application) {
        super(application);
        repository = getRepository(EnrollmentSessionRepository.class);
        searchLiveData = new MutableLiveData<>();

        sessionLiveData = Transformations.switchMap(
                searchLiveData,
                input -> buildList(input).build()
        );
    }

    private LivePagedListBuilder<Integer, EnrollmentSession> buildList(SessionListingKey key) {
        DataSource.Factory<Integer, EnrollmentSession> factory
                = repository.getEnrollmentSessions(key.location);

        return new LivePagedListBuilder<>(factory, PAGE_SIZE);
    }

    public void updateFilterParameters(LocationSelection location, String search) {
        searchLiveData.postValue(new SessionListingKey(location, search));
    }

    public LiveData<PagedList<EnrollmentSession>> getEnrollmentSessions(LocationSelection location) {
        updateFilterParameters(location, null);
        return sessionLiveData;
    }

    public LiveData<PagedList<EnrollmentSession>> getEnrollmentSessions() {
        return sessionLiveData;
    }

    public void save(List<EnrollmentSession> sessions) {
        repository.saveAll(sessions);
    }

    public void update(EnrollmentSession session) {
        repository.update(session);
    }

    public void downloadEnrollmentSessions(LocationSelection location,
                                           EnrollmentService service,
                                           EnrollmentSessionRepository.SessionDownloadListener listener,
                                           DownloadOptionsDialog.DownloadOption downloadOption) {
        repository.downloadEnrollmentSessions(location, service, listener, downloadOption);
    }

    static class SessionListingKey {
        private final String search;
        private final LocationSelection location;

        public SessionListingKey(LocationSelection location, String search) {
            this.search = search;
            this.location = location;
        }
    }
}