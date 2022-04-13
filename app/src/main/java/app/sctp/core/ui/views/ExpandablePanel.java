package app.sctp.core.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;

import java.util.Objects;
import java.util.function.Function;

import app.sctp.R;
import app.sctp.databinding.ExpandablePanelBinding;
import app.sctp.databinding.LocationInfoBinding;

public class ExpandablePanel extends LinearLayout {
    private View contentView;
    private ViewMode viewMode;
    private ExpandablePanelBinding binding;

    public ExpandablePanel(Context context) {
        super(context);
        init(context, null);
    }

    public ExpandablePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandablePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ExpandablePanel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void toggle() {
        if (viewMode == ViewMode.collapse) {
            setViewMode(ViewMode.expand);
        } else {
            setViewMode(ViewMode.collapse);
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        viewMode = ViewMode.collapse;
        setOrientation(VERTICAL);

        binding = ExpandablePanelBinding.inflate(
                LayoutInflater.from(context), this, true
        );

        binding.headerPanel.setOnClickListener(v -> toggle());

        if (attributeSet != null) {
            TypedArray array = context.obtainStyledAttributes(
                    attributeSet,
                    R.styleable.ExpandablePanel
            );
            setTitle(array.getText(R.styleable.ExpandablePanel_panel_title));
            setSummary(array.getText(R.styleable.ExpandablePanel_panel_summary));
            viewMode = ViewMode.VIEW_MODES[array.getInt(R.styleable.ExpandablePanel_view_mode,
                    ViewMode.collapse.ordinal())];
            int layout = array.getResourceId(R.styleable.ExpandablePanel_layout, -1);

            contentView = LayoutInflater.from(context)
                    .inflate(layout, binding.contentPanel, false);

            setColor(array.getColor(R.styleable.ExpandablePanel_content_background_color, 0));

            array.recycle();
        }

        binding.contentPanel.addView(contentView);

        updateView();
    }

    private void updateView() {
        binding.dividerLine.setVisibility(viewMode.visibility);
        binding.contentPanel.setVisibility(viewMode.visibility);
        binding.statusIcon.setImageResource(viewMode.statusIcon);
    }

    public void setColorResource(@ColorRes int colorResource) {
        setColor(ResourcesCompat.getColor(getResources(), colorResource, null));
    }

    public void setColor(int color) {
        binding.contentPanel.setBackgroundColor(color);
    }

    public void setTitle(CharSequence title) {
        binding.title.setText(title);
    }

    public void setSummary(CharSequence summary) {
        binding.summary.setText(summary);
    }

    public void setViewMode(ViewMode viewMode) {
        Objects.requireNonNull(viewMode, "View mode cannot be null");
        if (viewMode != this.viewMode) {
            this.viewMode = viewMode;
            updateView();
        }
    }

    public ViewMode getViewMode() {
        return viewMode;
    }

    public <T extends ViewBinding> T getContentViewBinding(ViewBindingReceiver<T> receiver) {
        return receiver.apply(contentView);
    }

    public interface ViewBindingReceiver<T> {
        T apply(View contentView);
    }

    public enum ViewMode {
        expand(R.drawable.ic_baseline_arrow_drop_down_24, View.VISIBLE),
        collapse(R.drawable.ic_baseline_arrow_right_24, View.GONE);

        final int statusIcon;
        final int visibility;

        ViewMode(int statusIcon, int visibility) {
            this.statusIcon = statusIcon;
            this.visibility = visibility;
        }

        static final ViewMode[] VIEW_MODES = values();
    }
}
