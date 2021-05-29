package Layers.BusinessLayer.ExceptionBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

import java.sql.Timestamp;

public class ExceptionListDTO extends BaseListDTO {
    public String errorCode, errorState, description;
    public Timestamp errorTimestamp;
}
