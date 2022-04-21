package app.sctp.targeting.models;

public enum MaritalStatus {
    Married(1),
    Separated(2),
    Divorced(3),
    Widowed(4),
    NeverMarried(5);

    public final int code;

    MaritalStatus(int code) {
        this.code = code;
    }

    public static final MaritalStatus[] VALUES = values();
}
