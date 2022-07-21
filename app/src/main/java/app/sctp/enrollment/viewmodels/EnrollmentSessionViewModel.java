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
import app.sctp.persistence.BaseViewModel;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.utils.DownloadOptionsDialog;

public class EnrollmentSessionViewModel  extends BaseViewModel {

    static class SessionListingKey {
        private final String search;
        private final LocationSelection location;;

        public SessionListingKey(LocationSelection location, String search) {
            this.search = search;
            this.location = location;
        }
    }

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

    private LivePagedListBuilder<Integer, EnrollmentSession> buildList(EnrollmentSessionRepository.LIkey) {
        DataSource.Factory<Integer, EnrollmentSession> factory;
        switch (key.meetingPhase) {
            case district_meeting:
                factory = repository.getDistrictMeetingSessions(key.location);
                break;
            case second_community_meeting:
                factory = repository.getSecondCommunityMeetingSessions(key.location);
                break;
            default:
                throw new IllegalArgumentException("unsupported meeting phase value " + key.meetingPhase);
        }
        return new LivePagedListBuilder<>(factory, PAGE_SIZE);
    }

    public void updateFilterParameters(LocationSelection location, TargetingSession.MeetingPhase meetingPhase, String search) {
        searchLiveData.postValue(new TargetingSessionViewModel.SessionListingKey(location, meetingPhase, search));
    }

    public LiveData<PagedList<TargetingSession>> getTargetingSessions(LocationSelection location, TargetingSession.MeetingPhase meetingPhase) {
        updateFilterParameters(location, meetingPhase, null);
        return sessionLiveData;
    }

    public LiveData<PagedList<TargetingSession>> get2ndCommunityMeetingTargetingSessions(LocationSelection location) {
        updateFilterParameters(location, TargetingSession.MeetingPhase.second_community_meeting, null);
        return sessionLiveData;
    }

    public LiveData<PagedList<TargetingSession>> getDistrictMeetingTargetingSessions(LocationSelection location) {
        updateFilterParameters(location, TargetingSession.MeetingPhase.district_meeting, null);
        return sessionLiveData;
    }

    public LiveData<PagedList<TargetingSession>> getTargetingSessions() {
        return sessionLiveData;
    }

    public void save(List<TargetingSession> sessions) {
        repository.saveAll(sessions);
    }

    public void update(TargetingSession session) {
        repository.update(session);
    }

    public void downloadTargetingSessions(LocationSelection location,
                                          TargetingSession.MeetingPhase phase,
                                          TargetingService service,
                                          TargetingSessionRepository.SessionDownloadListener listener,
                                          DownloadOptionsDialog.DownloadOption downloadOption) {
        repository.downloadTargetingSessions(location, phase, service, listener, downloadOption);
    }
}