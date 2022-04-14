package app.sctp.core.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedTransferQueue;

import app.sctp.core.model.NotificationEvent;
import app.sctp.databinding.LayoutNotificationBannerBinding;

public class NotificationBanner extends LinearLayout {
    private class NotificationParameters {
        CharSequence message;
        OnClickListener actionHandler;
        NotificationEvent.Severity severity;

        public NotificationParameters(CharSequence message, OnClickListener actionHandler, NotificationEvent.Severity severity) {
            this.message = message;
            this.severity = severity;
            this.actionHandler = actionHandler;
        }
    }

    private TimerTask timerTask;
    private LayoutNotificationBannerBinding binding;
    private LinkedTransferQueue<NotificationParameters> notificationParameters;

    public NotificationBanner(Context context) {
        super(context);
    }

    public NotificationBanner(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NotificationBanner(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NotificationBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attributeSet) {
        notificationParameters = new LinkedTransferQueue<>();
        binding = LayoutNotificationBannerBinding.inflate(LayoutInflater.from(context), this, true);
        if (attributeSet != null) {

        }
    }

    public void setMessage(CharSequence message, NotificationEvent.Severity severity, OnClickListener actionHandler) {
        notificationParameters.add(new NotificationParameters(message, actionHandler, severity));
        updateMessage();
    }

    public void setMessage(CharSequence message, NotificationEvent.Severity severity) {
        setMessage(message, severity, null);
    }

    public void setVisible(boolean visible) {
        this.setVisibility(visible ? VISIBLE : GONE);
    }

    private void updateMessage(){

    }
}
