package app.sctp.targeting.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.List;

import app.sctp.persistence.BaseViewModel;
import app.sctp.targeting.models.Individual;
import app.sctp.targeting.repositories.IndividualRepository;
import io.reactivex.Flowable;

public class IndividualViewModel extends BaseViewModel {

    private final IndividualRepository individualRepository;

    public IndividualViewModel(@NonNull Application application) {
        super(application);
        individualRepository = getRepository(IndividualRepository.class);
    }

    public void save(List<Individual> households) {
        individualRepository.save(households);
    }

    public Flowable<List<Individual>> getAll(Long householdId) {
        return individualRepository.getByHouseHoldId(householdId);
    }
}
