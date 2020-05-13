package co.bugu.radar.category.dao;

import co.bugu.common.dao.BaseDao;
import co.bugu.radar.category.domain.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryDao extends BaseDao<Category>{

    List<Category> findByNameLike(@Param("name") String name);
}
