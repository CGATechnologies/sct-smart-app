package app.sctp.core.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import app.sctp.R;
import app.sctp.databinding.DualLineTextviewBinding;

public class DualLineTextView extends LinearLayout {
    private DualLineTextviewBinding binding;

    public DualLineTextView(Context context) {
        super(context);
        init(context, null);
    }

    public DualLineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DualLineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public DualLineTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        binding = DualLineTextviewBinding.inflate(
                LayoutInflater.from(context),
                this,
                true
        );

        if (attributeSet != null) {
            TypedArray typedArray = context.obtainStyledAttributes(
                    attributeSet,
                    R.styleable.DualLineTextView
            );

            CharSequence title = typedArray.getText(R.styleable.DualLineTextView_line1);
            CharSequence summary = typedArray.getText(R.styleable.DualLineTextView_line2);

            setTitle(title);
            setSummary(summary);

            typedArray.recycle();
        }
    }

    public void setTitle(CharSequence title) {
        binding.title.setText(title);
    }

    public void setSummary(CharSequence summary) {
        binding.summary.setText(summary);
    }
}
