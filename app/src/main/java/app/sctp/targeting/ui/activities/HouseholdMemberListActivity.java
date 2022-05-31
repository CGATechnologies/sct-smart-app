package app.sctp.targeting.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.List;

import app.sctp.core.ui.BaseActivity;
import app.sctp.core.ui.adapter.GenericAdapter;
import app.sctp.databinding.ActivityHouseholdMembersListBinding;
import app.sctp.targeting.models.Individual;
import app.sctp.targeting.models.TargetedHousehold;
import app.sctp.targeting.ui.viewholders.IndividualViewHolderCreator;
import app.sctp.targeting.viewmodels.IndividualViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class HouseholdMemberListActivity extends BaseActivity {
    private static final String KEY_HOUSEHOLD = "sctp.targeting.pev.session.household";

    private TargetedHousehold household;
    private IndividualViewModel individualViewModel;
    private ActivityHouseholdMembersListBinding binding;
    private GenericAdapter<Individual> individualAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHouseholdMembersListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupToolBar();

        household = (TargetedHousehold) getIntent().getSerializableExtra(KEY_HOUSEHOLD);

        setSubTitle(household.getMlCodeForPrint());

        individualAdapter = new GenericAdapter<>(new IndividualViewHolderCreator());
        binding.memberList.setAdapter(individualAdapter);

        individualViewModel = getViewModel(IndividualViewModel.class);
        individualViewModel.getAll(household.getHouseholdId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new DisposableSubscriber<List<Individual>>() {
                    @Override
                    public void onNext(List<Individual> individuals) {
                        individualAdapter.submitList(individuals);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        dispose();
                    }
                });
    }

    public static void viewHouseholdMembers(Activity activity, TargetedHousehold household) {
        activity.startActivity(new Intent(activity, HouseholdMemberListActivity.class)
                .putExtra(KEY_HOUSEHOLD, household));
    }
}
