package app.sctp.targeting.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import app.sctp.core.ui.BindableFragment;
import app.sctp.databinding.FragmentTargetingCommunityMeetingBinding;

public class CommunityMeetingFragment extends BindableFragment {

    private FragmentTargetingCommunityMeetingBinding binding;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //noinspection ConstantConditions
    }

    @Override
    protected ViewBinding bindViews(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return binding = FragmentTargetingCommunityMeetingBinding.inflate(inflater, container, false);
    }
}
