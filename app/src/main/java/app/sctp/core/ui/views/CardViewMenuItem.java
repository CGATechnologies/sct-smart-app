package app.sctp.core.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.navigation.NavAction;

import com.google.android.material.card.MaterialCardView;

import app.sctp.R;
import app.sctp.databinding.LayoutCardViewMenuItemBinding;
import app.sctp.utils.UiUtils;

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

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CardViewMenuItem
            );

            String label = array.getString(R.styleable.CardViewMenuItem_labelText);
            int resourceId = array.getResourceId(R.styleable.CardViewMenuItem_menuIcon, -1);
            this.actionResId = array.getResourceId(R.styleable.CardViewMenuItem_targetAction, -1);

            array.recycle();

            binding.label.setText(label);
            binding.image.setImageResource(resourceId);
        }
    }

    public int getActionResId() {
        return actionResId;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        binding.ll.setOnClickListener(l);
    }
}
