package Layers.BusinessLayer.ExceptionBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

import java.sql.Timestamp;

/**
 * DTO object to transfer data of an exception in listing operations.
 */
public class ExceptionListDTO extends BaseListDTO {
    public String errorCode, errorState, description;
    public Timestamp errorTimestamp;
}
