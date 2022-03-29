package app.sctp.targeting;

import java.math.BigInteger;

public class GeoLocation {
    private Long id;
    private String name;
    private BigInteger code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getCode() {
        return code;
    }

    public void setCode(BigInteger code) {
        this.code = code;
    }
}
