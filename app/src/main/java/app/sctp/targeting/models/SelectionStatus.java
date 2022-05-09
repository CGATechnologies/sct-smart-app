package app.sctp.targeting.models;

public enum SelectionStatus {
    NonRecertified(4),
    PreEligible(6),
    Ineligible(2),
    Eligible(1),
    Selected(3),
    Enrolled(5);

    public final int code;

    SelectionStatus(int code) {
        this.code = code;
    }
}
