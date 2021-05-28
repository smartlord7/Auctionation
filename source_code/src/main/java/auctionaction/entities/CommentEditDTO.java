

public class CommentEditDTO {

    private Long CommentId;
    private String Text;
    private Date CreateTimestamp;
    private Date UpdateTimestamp;
    private Date DeleteTimestamp;

    public CommentEditDTO(){}

    public Long getCommentId() {
        return CommentId;
    }

    public String getText() {
        return Text;
    }

    public Date getCreateTimestamp() {
        return CreateTimestamp;
    }

    public Date getUpdateTimestamp() {
        return UpdateTimestamp;
    }

    public Date getDeleteTimestamp() {
        return DeleteTimestamp;
    }
}
