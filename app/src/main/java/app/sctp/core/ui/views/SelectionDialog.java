package app.sctp.core.ui.views;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import app.sctp.R;
import app.sctp.app.SctApplication;
import app.sctp.core.ui.adapter.GenericPagedAdapter;
import app.sctp.core.ui.adapter.ItemSelectionListener;
import app.sctp.databinding.DialogLocationSelectionBinding;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationType;
import app.sctp.targeting.ui.viewholders.LocationItemViewHolderCreator;
import app.sctp.targeting.viewmodels.LocationViewModel;
import app.sctp.utils.PlatformUtils;

class SelectionDialog extends Dialog {
    private LocationViewModel locationViewModel;
    private DialogLocationSelectionBinding binding;
    private GenericPagedAdapter<GeoLocation> geoLocationAdapter;
    private LocationSelector.OnLocationSelectedListener listener;

    private long parentCode;
    private Observer<PagedList<GeoLocation>> listObserver;
    private LiveData<PagedList<GeoLocation>> listLiveData;

    public SelectionDialog(@NonNull Context context) {
        super(context, R.style.ThemeOverlay_AppCompat);
        initComponents();
    }

    private void removeObserver() {
        if (listObserver != null && listLiveData != null) {
            listLiveData.removeObserver(listObserver);
            PlatformUtils.debugLog("remove observer");
        }
    }

    protected void invalidateSelection() {
        this.parentCode = 0;
        locationViewModel.updateParentCodeAndSearchValue(parentCode, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeObserver();
    }

    public void initComponents() {
        binding = DialogLocationSelectionBinding.inflate(getLayoutInflater(), null, false);
        setContentView(binding.getRoot());
        binding.backButton.setOnClickListener(v -> dismiss());
        binding.recyclerView.setSearchFilterListener(text -> locationViewModel.updateParentCodeAndSearchValue(parentCode, text.toString()));

        locationViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(SctApplication.getInstance())
                .create(LocationViewModel.class);

        listObserver = geoLocations -> geoLocationAdapter.submitList(geoLocations);

        (this.listLiveData = this.locationViewModel.getByParentCode()).observeForever(listObserver);

        geoLocationAdapter = new GenericPagedAdapter<>(new LocationItemViewHolderCreator(
                new ItemSelectionListener<GeoLocation>() {
                    @Override
                    public void onItemSelected(GeoLocation item) {
                        if (listener != null) {
                            listener.onLocationSelected(item);
                            dismiss();
                        }
                    }

                    @Override
                    public void onItemLongSelected(GeoLocation item) {

                    }
                }));

        binding.recyclerView.setAdapter(geoLocationAdapter);
    }

    @Override
    public void show() {
        throw new UnsupportedOperationException();
    }

    public void show(LocationSelector.OnLocationSelectedListener listener, CharSequence prompt, LocationType locationType, long parentCode) {
        this.listener = listener;
        this.parentCode = parentCode;
        this.binding.prompt.setText(prompt);
        this.locationViewModel.updateParentCodeAndSearchValue(parentCode, null);
        super.show();
    }
}
