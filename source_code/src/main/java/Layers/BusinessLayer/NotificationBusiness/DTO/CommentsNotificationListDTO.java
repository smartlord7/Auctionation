package Layers.BusinessLayer.NotificationBusiness.DTO;

import java.sql.Timestamp;

public class CommentsNotificationListDTO extends NotificationListDTO{
    public CommentsNotificationListDTO(String notificationTitle, String description, Integer notificationId, Integer userId, Timestamp createTimestamp) {
        super(notificationTitle, description, notificationId, userId, createTimestamp);
    }
}
