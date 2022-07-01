package app.sctp.core.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.Locale;

import app.sctp.R;
import app.sctp.databinding.LocationSelectorBinding;
import app.sctp.targeting.models.GeoLocation;
import app.sctp.targeting.models.LocationType;
import app.sctp.utils.PlatformUtils;

public class LocationSelector extends LinearLayout {
    private String selectionPrompt;
    private LocationType locationType;
    private GeoLocation selectedLocation;
    private SelectionDialog selectionDialog;
    private LocationSelectorBinding binding;
    private OnLocationSelectedListener locationSelectedListener;

    private long parentCodeHint;

    public LocationSelector(Context context) {
        super(context);
        init(null);
    }

    public LocationSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LocationSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public LocationSelector(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        binding = LocationSelectorBinding.inflate(layoutInflater, this, true);
        setOrientation(VERTICAL);

        CharSequence title = null;

        if (attributeSet != null) {
            TypedArray array = getContext().obtainStyledAttributes(
                    attributeSet,
                    R.styleable.LocationSelector
            );

            title = array.getString(R.styleable.LocationSelector_title);
            selectionPrompt = array.getString(R.styleable.LocationSelector_prompt);
            locationType = LocationType.valueOf(array.getInt(R.styleable.LocationSelector_locationType, LocationType.SUBNATIONAL1.level));

            array.recycle();
        }

        if (selectionPrompt == null) {
            selectionPrompt = getContext().getString(R.string.location_selection_prompt);
        }
        if (title == null) {
            setTitle(String.format(Locale.US, "Select %s", locationType.description));
        }
        setTitle(title);

        selectionDialog = new SelectionDialog(getContext());
        setOnClickListener(v -> selectionDialog.show(geoLocation -> {
            LocationSelector.this.selectedLocation = geoLocation;
            updateUi();
            if (locationSelectedListener != null) {
                locationSelectedListener.onLocationSelected(geoLocation);
            }
        }, selectionPrompt, locationType, parentCodeHint));
        updateUi();
    }

    private void updateUi() {
        String locationName;
        if (selectedLocation == null) {
            locationName = "[No Location Selected]";
        } else {
            locationName = selectedLocation.getName();
        }
        binding.selectedLocationText.setText(locationName);
    }

    public GeoLocation getSelectedLocation() {
        return selectedLocation;
    }

    public void setTitle(CharSequence title) {
        binding.title.setText(title);
    }

    public void setLocationSelectedListener(OnLocationSelectedListener locationSelectedListener) {
        this.locationSelectedListener = locationSelectedListener;
    }

    public void setParentCodeHint(long parentCode) {
        this.parentCodeHint = parentCode;
        PlatformUtils.debugLog("set parent code %d", parentCode);
    }

    public void invalidateSelection() {
        selectedLocation = null;
        updateUi();
        selectionDialog.invalidateSelection();
    }

    public interface OnLocationSelectedListener {
        void onLocationSelected(GeoLocation geoLocation);
    }

}
