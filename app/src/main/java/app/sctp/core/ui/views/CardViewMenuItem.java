package app.sctp.core.ui.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import app.sctp.R;
import app.sctp.databinding.LayoutCardViewMenuItemBinding;

public class CardViewMenuItem extends LinearLayout {
    @IdRes
    private int actionResId = -1;
    private LayoutCardViewMenuItemBinding binding;

    public CardViewMenuItem(Context context) {
        super(context);
        initComponents(context, null);
    }

    public CardViewMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponents(context, attrs);
    }

    public CardViewMenuItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponents(context, attrs);
    }

    private void initComponents(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = LayoutCardViewMenuItemBinding.inflate(inflater, this, true);
        int step = 0;
        boolean showStep = false;

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CardViewMenuItem
            );

            step = array.getInt(R.styleable.CardViewMenuItem_stepValue, 0);
            String label = array.getString(R.styleable.CardViewMenuItem_labelText);
            int resourceId = array.getResourceId(R.styleable.CardViewMenuItem_menuIcon, -1);
            showStep = array.getBoolean(R.styleable.CardViewMenuItem_showStep, false);
            this.actionResId = array.getResourceId(R.styleable.CardViewMenuItem_targetAction, -1);

            array.recycle();

            binding.label.setText(label);
            binding.image.setImageResource(resourceId);
        }

        setStep(step);

        if (!showStep) {
            binding.stepText.setVisibility(GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    public void setStep(int step) {
        binding.stepText.setText(Integer.toString(step));
    }

    public int getActionResId() {
        return actionResId;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        binding.ll.setOnClickListener(l);
    }
}
