package Layers.BusinessLayer.NotificationBusiness.DTO;

import Layers.BusinessLayer.Base.DTO.BaseListDTO;

import java.sql.Timestamp;

public class NotificationListDTO extends BaseListDTO {
    public String notificationTitle, description;
    public Integer notificationId, userId;
    public Timestamp createTimestamp;

    public NotificationListDTO(String notificationTitle, String description, Integer notificationId, Integer userId, Timestamp createTimestamp) {
        this.notificationTitle = notificationTitle;
        this.description = description;
        this.notificationId = notificationId;
        this.userId = userId;
        this.createTimestamp = createTimestamp;
    }
}
