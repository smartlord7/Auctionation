package Layers.BusinessLayer.NotificationBusiness;

import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.NotificationBusiness.DTO.CommentsNotificationEditDTO;
import Layers.BusinessLayer.NotificationBusiness.DTO.CommentsNotificationListDTO;

import java.sql.Connection;

public class CommentNotificationDAO extends BaseDAO<CommentsNotificationEditDTO, CommentsNotificationListDTO> {
    public CommentNotificationDAO(Connection conn) {
        super("CommentNotification", false);
    }
}
