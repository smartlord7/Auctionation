public class NotificationEditDTO {

    private Long NotificationId;
    private String NotificationTitle;
    private String Description;
    private Date CreateTimestamp;
    private Date DeleteTimestamp;

    public NotificationEditDTO()
    {

    }

    public Long getNotificationId() {
        return NotificationId;
    }

    public String getNotificationTitle() {
        return NotificationTitle;
    }

    public String getDescription() {
        return Description;
    }

    public Date getCreateTimestamp() {
        return CreateTimestamp;
    }

    public Date getDeleteTimestamp() {
        return DeleteTimestamp;
    }
}
