package app.sctp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;

import app.sctp.LoginActivity;
import app.sctp.R;

public final class UiUtils {
    public static void setViewRippleEffect(View view) {
        TypedValue outValue = new TypedValue();
        view.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        view.setBackgroundResource(outValue.resourceId);
    }

    public static void snackbar(View view, String message, boolean dismissible) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        if (dismissible) {
            snackbar.setAction(android.R.string.ok, v -> snackbar.dismiss());
        }
        snackbar.show();
    }

    public static void snackbar(View view, String message) {
        snackbar(view, message, true);
    }

    public static void snackbar(View view, @StringRes int message) {
        snackbar(view, message, true);
    }

    public static void snackbar(View view, @StringRes int message, boolean dismissible) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        if (dismissible) {
            snackbar.setAction(android.R.string.ok, v -> snackbar.dismiss());
        }
        snackbar.show();
    }

    public static String getNonEmptyText(EditText editText) {
        String text = editText.getText().toString();
        if (!LocaleUtils.hasText(text)) {
            editText.setError("Field is required.");
            return null;
        }
        return text;
    }

    public static ProgressDialog progressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(context.getText(R.string.processing_wait));
        return dialog;
    }

    public static void toast(Context context, @StringRes int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }
}
