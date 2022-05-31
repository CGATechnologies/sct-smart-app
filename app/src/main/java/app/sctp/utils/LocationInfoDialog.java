package app.sctp.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import app.sctp.databinding.DialogLocationInfoBinding;
import app.sctp.targeting.models.LocationSelection;

public class LocationInfoDialog extends Dialog {
    private final DialogLocationInfoBinding binding;

    public LocationInfoDialog(@NonNull Context context) {
        super(context);
        binding = DialogLocationInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void show() {
        throw new UnsupportedOperationException();
    }

    public void show(String districtName, LocationSelection selection) {
        binding.setLocation(selection);
        binding.setDistrictName(districtName);
        super.show();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
