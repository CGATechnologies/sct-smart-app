package app.sctp.utils;

import androidx.databinding.BindingAdapter;

import app.sctp.core.ui.views.DualLineTextView;

public class BindingUtils {

    @BindingAdapter(value = {"line1"})
    public static void setAppLine1Text(DualLineTextView textView, String text) {
        textView.setTitle(text);
    }

    @BindingAdapter(value = {"line2"})
    public static void setAppLine2Text(DualLineTextView textView, String text) {
        textView.setSummary(text);
    }
}
