package BusinessLayer.BidBusiness.DTO;

import BusinessLayer.Base.DTO.BaseEditDTO;

public class BidEditDTO extends BaseEditDTO {
    private String Description;
    private Float Amount;

    @Override
    public String getTableName() {
        return "Bid";
    }

    public String getDescription() {
        return Description;
    }

    public Float getAmount() {
        return Amount;
    }
}
