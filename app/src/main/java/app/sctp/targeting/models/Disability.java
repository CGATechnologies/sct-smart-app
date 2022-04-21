package app.sctp.targeting.models;

import androidx.annotation.NonNull;

public enum Disability {
    Blind(1, null),
    Deaf(2, null),
    SpeechImpairment(3, "Speech Impairment"), // This is how it was spelled in the forms
    DeformedLimbs(4, "Deformed limbs"),
    MentallyDisabled(5, "Mentally disabled"),
    Albinism(6, null),
    Other(7, null),
    NotDisabled(8, "Not disabled");

    public final int code;
    public final String text;

    Disability(int code, String text) {
        this.code = code;
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return text != null ? text : name();
    }

    public static final Disability[] VALUES = values();
}
