package Layers.BusinessLayer.BidBusiness.DTO;

import java.sql.Timestamp;

/**
 * DTO object to transfer data about a bid in listing operations.
 */
public class BidListDTO {
    public Integer bidId;
    public Long userId;
    public String description;
    public Double amount;
    public Timestamp createTimestamp;

}
