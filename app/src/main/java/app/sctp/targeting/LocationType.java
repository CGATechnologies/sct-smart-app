package app.sctp.targeting;

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

    /**
     * <p>Hierarchical level (0 being the root. The lower the number, the higher the logical level).
     * All based on the following topographic relationship:
     * <b>i.e, District &gt; TA &gt; Village Cluster &gt; Zone &gt; Village</b></p>
     */
    public final int level;

    public final String description;
    public final LocationType parent;

    public boolean isImmediateChildOf(LocationType parent) {
        return this.parent == parent;
    }
}