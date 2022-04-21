package app.sctp.targeting.models;

import androidx.annotation.NonNull;

public enum EducationLevel {
    Nursery(1, null),
    Primary(2, null),
    Secondary(3, null),
    TrainingCollege(4, "Training College"),
    University(5, null),
    Other(6, null),
    None(7, null);

    public final int code;
    public final String otherName;

    EducationLevel(int code, String otherName) {
        this.code = code;
        this.otherName = otherName;
    }


    @NonNull
    @Override
    public String toString() {
        return otherName != null ? otherName : name();
    }

    public static final EducationLevel[] VALUES = values();
}
