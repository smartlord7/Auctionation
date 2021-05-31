package Layers.BusinessLayer.ExceptionBusiness;

import Helpers.config.ErrorResponse;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.ExceptionBusiness.DTO.ExceptionEditDTO;
import Layers.BusinessLayer.ExceptionBusiness.DTO.ExceptionListDTO;

import java.sql.Connection;

public class ExceptionDAO extends BaseDAO<ExceptionEditDTO, ExceptionListDTO> {

    public ExceptionDAO(Connection conn) {
        super("Exception", false);
    }
}
