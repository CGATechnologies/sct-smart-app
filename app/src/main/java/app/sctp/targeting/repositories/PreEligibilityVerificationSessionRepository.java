package app.sctp.targeting.repositories;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagingData;

import java.util.List;

import app.sctp.persistence.BaseRepository;
import app.sctp.persistence.SctpAppDatabase;
import app.sctp.targeting.dao.PreEligibilityVerificationSessionDao;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationSelection;
import app.sctp.targeting.models.PreEligibilityVerificationSession;
import io.reactivex.Flowable;

public class PreEligibilityVerificationSessionRepository extends BaseRepository {
    private PreEligibilityVerificationSessionDao pevSessionDao;
    private MutableLiveData<PagingData<GeoLocation>> locationLiveData;

    public PreEligibilityVerificationSessionRepository(SctpAppDatabase appDatabase) {
        super(appDatabase);
        pevSessionDao = appDatabase.pevSessionDao();
    }

    public Flowable<List<PreEligibilityVerificationSession>> getAll(LocationSelection locationSelection) {
        if(locationSelection.getCluster() != null){
            return pevSessionDao.getAll(
                    locationSelection.getDistrictCode(),
                    locationSelection.getTraditionalAuthority().getCode(),
                    locationSelection.getCluster().getCode()
            );
        }
        if (locationSelection.getTraditionalAuthority() != null){
            return pevSessionDao.getAll(
                    locationSelection.getDistrictCode(),
                    locationSelection.getTraditionalAuthority().getCode()
            );
        }
        return pevSessionDao.getAll(locationSelection.getDistrictCode());
    }

    public void save(List<PreEligibilityVerificationSession> sessionList) {
        post(() -> pevSessionDao.save(sessionList));
    }
}
