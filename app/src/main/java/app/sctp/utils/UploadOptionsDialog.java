package app.sctp.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import app.sctp.R;
import app.sctp.databinding.UploadOptionsPromptDialogBinding;

public class UploadOptionsDialog extends BottomSheetDialog {
    private UploadOption uploadOption;
    private UploadOptionSelectionListener listener;
    private UploadOptionsPromptDialogBinding binding;

    public UploadOptionsDialog(@NonNull Context context, @NonNull UploadOptionSelectionListener listener) {
        super(context);
        this.listener = listener;
        uploadOption = UploadOption.Replace;
        initComponents();
    }

    private void initComponents() {
        binding = UploadOptionsPromptDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.cancelButton.setOnClickListener(v -> {
            cancel();
            listener.onCancel(UploadOptionsDialog.this);
        });
        /*binding.uploadOptions.setOnClickListener(v -> {
            final int id = binding.uploadOptions.getCheckedRadioButtonId();
            if (id == R.id.upload_option_ignore) {
                uploadOption = UploadOption.Ignore;
            } else if (id == R.id.upload_option_replace) {
                uploadOption = UploadOption.Replace;
            }
            dismiss();
            listener.onOptionSelected(UploadOptionsDialog.this, uploadOption);
        });*/
    }

    @Override
    public void show() {
        show(R.string.download_option_message);
    }

    public void show(@StringRes int resId) {
        binding.prompt.setText(resId);
        super.show();
    }

    public UploadOption getDownloadOption() {
        return uploadOption;
    }

    public interface UploadOptionSelectionListener {
        default void onCancel(UploadOptionsDialog dlg) {
        }

        void onOptionSelected(UploadOptionsDialog dlg, UploadOption uploadOption);
    }

    public enum UploadOption {
        Ignore,
        Replace
    }
}
