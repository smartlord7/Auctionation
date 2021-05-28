

public class BidEditDTO {

    private long BidId;
    private String Description;
    private Float Amount;
    private Date CreateTimestamp;

    public BidEditDTO(){}

    public long getBidId() {
        return BidId;
    }

    public String getDescription() {
        return Description;
    }

    public Float getAmount() {
        return Amount;
    }

    public Date getCreateTimestamp() {
        return CreateTimestamp;
    }
}
