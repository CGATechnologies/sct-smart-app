package app.sctp.targeting.models;

public enum RelationshipToHead {
    Head(1, "Head"),
    Spouse(2, "Spouse"),
    OwnChild(3, "Own child"),
    BrotherOrSister(4, "Brother/Sister"),
    GrandChild(5, "Grandchild"),
    Parent(6, "Parent"),
    OtherRelative(7, "Other relative"),
    NotRelated(8, "Not related");

    public final int code;
    public final String description;
    public static final RelationshipToHead[] VALUES = values();

    RelationshipToHead(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
