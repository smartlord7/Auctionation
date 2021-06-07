package Layers.BusinessLayer.NotificationBusiness.DTO;

import java.sql.Timestamp;

/**
 * DTO object to transfer data of an auction notification in listing operations.
 */
public class AuctionNotificationListDTO extends NotificationListDTO{
    public AuctionNotificationListDTO(String notificationTitle, String description, Integer notificationId, Integer userId, Timestamp createTimestamp) {
        super(notificationTitle, description, notificationId, userId, createTimestamp);
    }
}
