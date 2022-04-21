package app.sctp.targeting.models;

import androidx.annotation.NonNull;

public enum ChronicIllness {
    ChronicMalaria(1, "Chronic Malaria"),
    TB(2, null),
    HivAids(3, "HIV/AIDS"),
    Asthma(4, null),
    Arthritis(5, "Arthritis"),
    Epilepsy(6, null),
    Cancer(7, null),
    Other(8, null),
    None(9, null);

    ChronicIllness(int code, String text) {
        this.code = code;
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return text != null ? text : name();
    }

    public final int code;
    public final String text;
    public static final ChronicIllness[] VALUES = values();
}
