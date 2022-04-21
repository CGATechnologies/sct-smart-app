package app.sctp.targeting.models;

public enum Gender {
    Male(1),
    Female(2);

    public final int code;

    Gender(int code) {
        this.code = code;
    }

    public static final Gender[] VALUES = values();
}
