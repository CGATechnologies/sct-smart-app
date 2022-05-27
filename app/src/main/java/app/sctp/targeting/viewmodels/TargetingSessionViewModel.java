package app.sctp.targeting.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.List;

import app.sctp.persistence.BaseViewModel;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.TargetingSession;
import app.sctp.targeting.repositories.TargetingSessionRepository;
import app.sctp.targeting.services.TargetingService;

public class TargetingSessionViewModel extends BaseViewModel {

    static class SessionListingKey {
        private final String search;
        private final LocationSelection location;
        private final TargetingSession.MeetingPhase meetingPhase;

        public SessionListingKey(LocationSelection location, TargetingSession.MeetingPhase meetingPhase, String search) {
            this.search = search;
            this.location = location;
            this.meetingPhase = meetingPhase;
        }
    }

    private final TargetingSessionRepository repository;
    private MutableLiveData<SessionListingKey> searchLiveData;
    private LiveData<PagedList<TargetingSession>> sessionLiveData;

    public TargetingSessionViewModel(@NonNull Application application) {
        super(application);
        repository = getRepository(TargetingSessionRepository.class);
        searchLiveData = new MutableLiveData<>();

        sessionLiveData = Transformations.switchMap(
                searchLiveData,
                input -> buildList(input).build()
        );
    }

    private LivePagedListBuilder<Integer, TargetingSession> buildList(SessionListingKey key) {
        DataSource.Factory<Integer, TargetingSession> factory;
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
        searchLiveData.postValue(new SessionListingKey(location, meetingPhase, search));
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
                                          TargetingSessionRepository.SessionDownloadListener listener) {
        repository.downloadTargetingSessions(location, phase, service, listener);
    }
}
