package app.sctp.targeting.models;

import androidx.annotation.NonNull;

public enum GradeLevel {
    Standard1(1, "Std. 1"),
    Standard2(2, "Std. 2"),
    Standard3(3, "Std. 3"),
    Standard4(4, "Std. 4"),
    Standard5(5, "Std. 5"),
    Standard6(6, "Std. 6"),
    Standard7(7, "Std. 7"),
    Standard8(8, "Std. 8"),
    Form1(9, "Form 1"),
    Form2(10, "Form 2"),
    Form3(11, "Form 3"),
    Form4(12, "Form 4"),
    Other(13, null);

    public final int code;
    public final String text;
    public static final GradeLevel[] VALUES = values();

    GradeLevel(int code, String text) {
        this.code = code;
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return text != null ? text : name();
    }
}
