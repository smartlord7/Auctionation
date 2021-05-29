package BusinessLayer.CategoryBusiness;

import BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class CategoryDAO<CategoryEditDTO,CategoryListDTO> extends BaseDAO{
    public CategoryDAO(Connection conn, String tableName) {
        super(conn, tableName);
    }
}