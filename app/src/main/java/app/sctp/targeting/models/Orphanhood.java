package app.sctp.targeting.models;

import androidx.annotation.NonNull;

public enum Orphanhood {
    SingleOrphan(1, "Single orphan"),
    DoubleOrphan(2, "Double orphan"),
    NotOrphaned(3, "Not orphan");

    public final int code;
    public final String text;

    Orphanhood(int code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    @NonNull
    public String toString() {
        return text != null ? text : name();
    }

    public static final Orphanhood[] VALUES = values();
}
