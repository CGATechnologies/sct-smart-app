package app.sctp.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    public static void toast(Context context, @StringRes int resId, Object... args) {
        Toast.makeText(context, context.getString(resId, args), Toast.LENGTH_SHORT).show();
    }

    public static void toast(Context context, @StringRes int resId) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static DialogCall dialogPrompt(Context context, @StringRes int message) {
        return new DialogCallImpl(new AlertDialog.Builder(context).setMessage(message).create());
    }

    public static AlertDialog messageDialog(Context context, @StringRes int resId) {
        return new AlertDialog.Builder(context)
                .setMessage(resId)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .create();
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
