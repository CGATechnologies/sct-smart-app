package app.sctp.core.model;

import java.io.Serializable;
import java.util.Objects;

public interface Diffable extends Serializable {
    Object getDiffValue();

    default boolean contentsSameAs(Diffable diffable) {
        return Objects.equals(getDiffValue(), diffable.getDiffValue());
    }
}
