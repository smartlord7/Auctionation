package Layers.BusinessLayer.BidBusiness.DTO;

import java.sql.Timestamp;

public class BidListDTO {
    public Integer bidId, userId;
    public String description;
    public Float amount;
    public Timestamp createTimestamp;
}
