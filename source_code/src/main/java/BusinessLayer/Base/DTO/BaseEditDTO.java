package BusinessLayer.Base.DTO;

public abstract class BaseEditDTO {
    private int id;

    public BaseEditDTO() {
    }

    public BaseEditDTO(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract String getTableName();
}
