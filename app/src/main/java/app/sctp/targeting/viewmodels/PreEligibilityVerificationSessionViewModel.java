package app.sctp.targeting.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.persistence.BaseViewModel;
import app.sctp.persistence.ObservableDatabaseCall;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.LocationType;
import app.sctp.targeting.models.PreEligibilityVerificationSession;
import app.sctp.targeting.repositories.LocationRepository;
import app.sctp.targeting.repositories.PreEligibilityVerificationSessionRepository;
import io.reactivex.Flowable;

public class PreEligibilityVerificationSessionViewModel extends BaseViewModel {
    private final PreEligibilityVerificationSessionRepository repository;

    public PreEligibilityVerificationSessionViewModel(@NonNull Application application) {
        super(application);
        repository = getRepository(PreEligibilityVerificationSessionRepository.class);
    }

    public Flowable<List<PreEligibilityVerificationSession>> getAll(LocationSelection locationSelection) {
        return repository.getAll(locationSelection);
    }

    public void save(List<PreEligibilityVerificationSession> locations) {
        repository.save(locations);
    }
}
