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
import app.sctp.targeting.models.Household;
import app.sctp.targeting.models.Individual;
import app.sctp.targeting.repositories.HouseholdRepository;
import app.sctp.targeting.repositories.IndividualRepository;
import app.sctp.utils.LocaleUtils;
import io.reactivex.Flowable;

public class IndividualViewModel extends BaseViewModel {

    private IndividualRepository individualRepository;

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
