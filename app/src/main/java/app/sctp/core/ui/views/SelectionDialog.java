package app.sctp.core.ui.views;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import app.sctp.R;
import app.sctp.app.SctApplication;
import app.sctp.core.ui.adapter.GenericAdapter;
import app.sctp.core.ui.adapter.ItemSelectionListener;
import app.sctp.databinding.DialogLocationSelectionBinding;
import app.sctp.persistence.DatabaseCallDataObserver;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationType;
import app.sctp.targeting.ui.LocationItemViewHolderCreator;
import app.sctp.targeting.viewmodels.LocationViewModel;
import app.sctp.utils.UiUtils;

class SelectionDialog extends Dialog {
    private LocationViewModel locationViewModel;
    private DialogLocationSelectionBinding binding;
    private GenericAdapter<GeoLocation> geoLocationAdapter;
    private LocationSelector.OnLocationSelectedListener listener;

    public SelectionDialog(@NonNull Context context) {
        super(context, R.style.ThemeOverlay_AppCompat);
        initComponents();
    }

    public void initComponents() {
        binding = DialogLocationSelectionBinding.inflate(getLayoutInflater(), null, false);
        setContentView(binding.getRoot());
        binding.backButton.setOnClickListener(v -> dismiss());
        locationViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(SctApplication.getInstance())
                .create(LocationViewModel.class);
        geoLocationAdapter = new GenericAdapter<>(new LocationItemViewHolderCreator());
        geoLocationAdapter.setItemSelectionListener(new ItemSelectionListener<GeoLocation>() {
            @Override
            public void onItemSelected(GeoLocation item) {
                if (listener != null) {
                    listener.onLocationSelected(item);
                }
            }

            @Override
            public void onItemLongSelected(GeoLocation item) {

            }
        });
        binding.recyclerView.setAdapter(geoLocationAdapter);
    }

    @Override
    public void show() {
        throw new UnsupportedOperationException();
    }

    public void show(LocationSelector.OnLocationSelectedListener listener, CharSequence prompt, LocationType locationType) {
        this.listener = listener;
        this.binding.prompt.setText(prompt);
        this.locationViewModel.getLocationsByType(locationType)
                .error(data -> UiUtils.toast(getContext(), R.string.error_loading_db_locations, locationType.description))
                .success(geoLocationAdapter::submitList)
                .execute();
        super.show();
    }
}
