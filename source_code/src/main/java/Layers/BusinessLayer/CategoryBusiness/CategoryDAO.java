package Layers.BusinessLayer.CategoryBusiness;

import Layers.BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

public class CategoryDAO extends BaseDAO<CategoryEditDTO, CategoryListDTO>{
    public CategoryDAO(Connection conn) {
        super("Category", false);
    }
}