package Layers.BusinessLayer.NotificationBusiness;

import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.NotificationBusiness.DTO.NotificationEditDTO;
import Layers.BusinessLayer.NotificationBusiness.DTO.NotificationListDTO;

import java.sql.Connection;

public class NotificationDAO extends BaseDAO<NotificationEditDTO, NotificationListDTO> {
    public NotificationDAO(Connection conn) {
        super(conn, "Notification", true);
    }
}