package Layers.BusinessLayer.CategoryBusiness;

import Layers.BusinessLayer.Base.BaseDAO;

import java.sql.Connection;

/**
 * DAO object to access data of a category.
 */
public class CategoryDAO extends BaseDAO<CategoryEditDTO, CategoryListDTO>{
    public CategoryDAO(Connection conn) {
        super("Category", false);
    }
}