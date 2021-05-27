public class ItemEditDTO {
    private Long ItemId;
    private String EAN;
    private String ItemName;
    private String Description;
    private int Quantity;
    private String Origin;
    private Date CreateTimestamp;
    private Date UpdateTimestamp;
    private Date DeleteTimestamp;

    public ItemEditDTO(){}

    public java.lang.Long getItemId() {
        return ItemId;
    }

    public java.lang.String getEAN() {
        return EAN;
    }

    public java.lang.String getItemName() {
        return ItemName;
    }

    public java.lang.String getDescription() {
        return Description;
    }

    public int getQuantity() {
        return Quantity;
    }

    public java.lang.String getOrigin() {
        return Origin;
    }

    public Date getCreateTimestamp() {
        return CreateTimestamp;
    }

    public Date getUpdateTimestamp() {
        return UpdateTimestamp;
    }

    public Date getDeleteTimestamp() {
        return DeleteTimestamp;
    }
}
