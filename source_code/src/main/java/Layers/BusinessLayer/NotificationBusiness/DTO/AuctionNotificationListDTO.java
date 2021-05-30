package Layers.BusinessLayer.NotificationBusiness.DTO;

import java.sql.Timestamp;

public class AuctionNotificationListDTO extends NotificationListDTO{
    public AuctionNotificationListDTO(String notificationTitle, String description, Integer notificationId, Integer userId, Timestamp createTimestamp) {
        super(notificationTitle, description, notificationId, userId, createTimestamp);
    }
}
