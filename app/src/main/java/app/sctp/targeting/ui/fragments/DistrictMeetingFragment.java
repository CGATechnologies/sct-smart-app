package app.sctp.targeting.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import app.sctp.core.ui.BindableFragment;
import app.sctp.databinding.FragmentTargetingDistrictMeetingBinding;

public class DistrictMeetingFragment extends BindableFragment {

    private FragmentTargetingDistrictMeetingBinding binding;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return binding = FragmentTargetingDistrictMeetingBinding.inflate(inflater, container, false);
    }
}
