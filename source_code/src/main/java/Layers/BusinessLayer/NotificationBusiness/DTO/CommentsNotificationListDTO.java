package Layers.BusinessLayer.NotificationBusiness.DTO;

import java.sql.Timestamp;

/**
 * DTO object to transfer data of an comment notification in listing operations.
 */
public class CommentsNotificationListDTO extends NotificationListDTO{
    public CommentsNotificationListDTO(String notificationTitle, String description, Integer notificationId, Integer userId, Timestamp createTimestamp) {
        super(notificationTitle, description, notificationId, userId, createTimestamp);
    }
}
