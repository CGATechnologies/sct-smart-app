package app.sctp.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import app.sctp.LoginActivity;
import app.sctp.R;
import app.sctp.targeting.models.SelectionStatus;

public final class UiUtils {
    public static void setViewRippleEffect(View view) {
        TypedValue outValue = new TypedValue();
        view.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        view.setBackgroundResource(outValue.resourceId);
    }

    public static void snackbar(View view, String message, boolean dismissible) {
        Snackbar snackbar = Snackbar.make(view, message, dismissible ? Snackbar.LENGTH_INDEFINITE : Snackbar.LENGTH_LONG);
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
        Snackbar snackbar = Snackbar.make(view, message, (int) TimeUnit.SECONDS.toMillis(15));
        if (dismissible) {
            snackbar.setAction(android.R.string.ok, v -> snackbar.dismiss());
        }
        snackbar.show();
    }

    public static String getNonEmptyText(EditText editText) {
        String text = editText.getText().toString().trim();
        if (!LocaleUtils.hasText(text)) {
            editText.setError("Field is required.");
            return null;
        }
        return text;
    }

    public static Integer getInteger(EditText editText, boolean optional) {
        String text = editText.getText().toString();
        if (!LocaleUtils.hasText(text)) {
            if (!optional) {
                editText.setError("Field is required.");
            }
            return null;
        }
        return Integer.valueOf(text);
    }

    public static Integer getInteger(EditText editText, Integer defaultValue) {
        String text = editText.getText().toString();
        if (!LocaleUtils.hasText(text)) {
            return defaultValue;
        }
        return Integer.valueOf(text);
    }

    public static ProgressDialog progressDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(context.getText(R.string.processing_wait));
        return dialog;
    }

    public static ProgressDialog progressDialogWithProgress(Context context) {
        ProgressDialog dialog = progressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        return dialog;
    }

    public static void toast(Context context, @StringRes int resId, Object... args) {
        Toast.makeText(context, context.getString(resId, args), Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, @StringRes int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static DialogCall dialogPrompt(Context context, @StringRes int message) {
        return new DialogCallImpl(new AlertDialog.Builder(context).setMessage(message).create());
    }

    public static DialogCall dialogPrompt(Context context, String message) {
        return new DialogCallImpl(new AlertDialog.Builder(context).setMessage(message).create());
    }

    public static AlertDialog messageDialog(Context context, @StringRes int resId) {
        return new AlertDialog.Builder(context)
                .setMessage(resId)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .create();
    }

    /**
     * @param adapter   Adapter containing items.
     * @param selection Item whose index to look for
     * @return Returns the index of the item in the given adapter
     */
    public static int itemAdapterPosition(Adapter adapter, Object selection) {
        for (int position = 0; position < adapter.getCount(); position++) {
            final Object item = adapter.getItem(position);
            if (Objects.equals(item, selection)) {
                return position;
            }
        }
        return -1;
    }

    public interface DialogCall {
        void show();

        DialogCall cancelable(boolean cancelable);

        DialogCall onOk(DialogInterface.OnClickListener onClickListener);

        DialogCall onOk(@StringRes int text, DialogInterface.OnClickListener onClickListener);

        DialogCall onOk(CharSequence text, DialogInterface.OnClickListener onClickListener);

        DialogCall onCancel(@StringRes int text, DialogInterface.OnClickListener onClickListener);

        DialogCall onCancel(CharSequence text, DialogInterface.OnClickListener onClickListener);

        DialogCall onCancel(DialogInterface.OnClickListener onClickListener);
    }

    private static class DialogCallImpl implements DialogCall {
        private final AlertDialog dialog;

        DialogCallImpl(AlertDialog alertDialog) {
            this.dialog = alertDialog;
        }

        private CharSequence getText(@StringRes int id) {
            return dialog.getContext().getText(id);
        }

        @Override
        public void show() {
            dialog.show();
        }

        @Override
        public DialogCall cancelable(boolean cancellable) {
            dialog.setCancelable(cancellable);
            dialog.setCanceledOnTouchOutside(cancellable);
            return this;
        }

        @Override
        public DialogCall onOk(DialogInterface.OnClickListener onClickListener) {
            return onOk(android.R.string.ok, onClickListener);
        }

        @Override
        public DialogCall onOk(@StringRes int text, DialogInterface.OnClickListener onClickListener) {
            return onOk(getText(text), onClickListener);
        }

        @Override
        public DialogCall onOk(CharSequence text, DialogInterface.OnClickListener onClickListener) {
            this.dialog.setButton(DialogInterface.BUTTON_POSITIVE, text, onClickListener);
            return this;
        }

        @Override
        public DialogCall onCancel(@StringRes int text, DialogInterface.OnClickListener onClickListener) {
            return onCancel(getText(text), onClickListener);
        }

        @Override
        public DialogCall onCancel(CharSequence text, DialogInterface.OnClickListener onClickListener) {
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, text, onClickListener);
            return this;
        }

        @Override
        public DialogCall onCancel(DialogInterface.OnClickListener onClickListener) {
            return onCancel(android.R.string.cancel, onClickListener);
        }
    }
}
