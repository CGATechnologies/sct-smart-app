package app.sctp.targeting.models;

public enum LocationType {
    COUNTRY(0, "Country", null),
    SUBNATIONAL1(1, "District", COUNTRY),
    SUBNATIONAL2(2, "Traditional Authority", SUBNATIONAL1),
    SUBNATIONAL3(3, "Village Cluster", SUBNATIONAL2),
    SUBNATIONAL4(4, "Zone", SUBNATIONAL3),
    SUBNATIONAL5(5, "Village", SUBNATIONAL4);

    LocationType(int level, String description, LocationType parent) {
        this.level = level;
        this.description = description;
        this.parent = parent;
    }

    public static LocationType valueOf(int level) {
        for (LocationType locationType : TYPES) {
            if (locationType.level == level) {
                return locationType;
            }
        }
        throw new IllegalArgumentException("Invalid location type level " + level);
    }

    /**
     * <p>Hierarchical level (0 being the root. The lower the number, the higher the logical level).
     * All based on the following topographic relationship:
     * <b>i.e, District &gt; TA &gt; Village Cluster &gt; Zone &gt; Village</b></p>
     */
    public final int level;

    public final String description;
    public final LocationType parent;

    public static final LocationType[] TYPES = values();

    public boolean isImmediateChildOf(LocationType parent) {
        return this.parent == parent;
    }
}