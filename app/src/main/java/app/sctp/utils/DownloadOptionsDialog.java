package app.sctp.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import app.sctp.R;
import app.sctp.databinding.DownloadOptionsPromptDialogBinding;

public class DownloadOptionsDialog extends BottomSheetDialog {
    private DownloadOption downloadOption;
    private DownloadOptionSelectionListener listener;
    private DownloadOptionsPromptDialogBinding binding;

    public DownloadOptionsDialog(@NonNull Context context, @NonNull DownloadOptionSelectionListener listener) {
        super(context);
        this.listener = listener;
        downloadOption = DownloadOption.Replace;
        initComponents();
    }

    private void initComponents() {
        binding = DownloadOptionsPromptDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.cancelButton.setOnClickListener(v -> {
            cancel();
            listener.onCancel(DownloadOptionsDialog.this);
        });
        binding.downloadButton.setOnClickListener(v -> {
            final int id = binding.downloadOptions.getCheckedRadioButtonId();
            if (id == R.id.download_option_ignore) {
                downloadOption = DownloadOption.Ignore;
            } else if (id == R.id.download_option_replace) {
                downloadOption = DownloadOption.Replace;
            }
            dismiss();
            listener.onOptionSelected(DownloadOptionsDialog.this, downloadOption);
        });
    }

    @Override
    public void show() {
        show(R.string.download_option_message);
    }

    public void show(@StringRes int resId) {
        binding.prompt.setText(resId);
        super.show();
    }

    public DownloadOption getDownloadOption() {
        return downloadOption;
    }

    public interface DownloadOptionSelectionListener {
        default void onCancel(DownloadOptionsDialog dlg) {
        }

        void onOptionSelected(DownloadOptionsDialog dlg, DownloadOption downloadOption);
    }

    public enum DownloadOption {
        Ignore,
        Replace
    }
}
