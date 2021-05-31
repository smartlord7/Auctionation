package Layers.BusinessLayer.NotificationBusiness;

import Helpers.config.ErrorResponse;
import Layers.BusinessLayer.Base.BaseDAO;
import Layers.BusinessLayer.NotificationBusiness.DTO.CommentsNotificationEditDTO;
import Layers.BusinessLayer.NotificationBusiness.DTO.CommentsNotificationListDTO;

import java.sql.Connection;

/**
 * DAO object to access data of an comment notification.
 */
public class CommentNotificationDAO extends BaseDAO<CommentsNotificationEditDTO, CommentsNotificationListDTO> {

    public CommentNotificationDAO(Connection conn, ErrorResponse errorResponse) {
        super("CommentNotification", false);
    }
}
