package Layers.BusinessLayer.ExceptionBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseEditDTO;

import java.sql.Timestamp;

/**
 * DTO object to transfer data of an exception.
 */
public class ExceptionEditDTO extends BaseEditDTO {
    public Integer errorCode;
    public String errorState, description;
    public Timestamp errorTimestamp;

    public ExceptionEditDTO(Integer errorCode, String errorState, String description, Timestamp errorTimestamp) {
        this.errorCode = errorCode;
        this.errorState = errorState;
        this.description = description;
        this.errorTimestamp = errorTimestamp;
    }
}
