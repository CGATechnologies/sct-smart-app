package app.sctp.core.model;

import android.content.Intent;

import java.io.Serializable;

public class NotificationEvent implements Serializable {
    public enum Severity {
        Normal,
        Warning,
        Critical
    }

    private Intent data;
    private Severity severity;

    public NotificationEvent() {
    }

    public NotificationEvent(Intent data, Severity severity) {
        this.data = data;
        this.severity = severity;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }
}
