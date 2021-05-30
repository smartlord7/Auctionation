package Layers.DataLayer.Enums;

public enum AuctionEnum {
    RUNNING(0),
    INTERRUPTED(1),
    FINISHED(2);

    private final int value;

    AuctionEnum(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
