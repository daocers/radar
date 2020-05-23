package co.bugu.radar.categoryGoodsX.service;

import co.bugu.common.service.BaseService;
import co.bugu.radar.category.domain.Category;
import co.bugu.radar.category.service.CategoryService;
import co.bugu.radar.categoryGoodsX.dao.CategoryGoodsXDao;
import co.bugu.radar.categoryGoodsX.domain.CategoryGoodsX;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author daocers
 * @Date 2020-05-22 10:28
 * @Description:
 */
@Service
public class CategoryGoodsXService extends BaseService<CategoryGoodsX> {
    @Autowired
    CategoryGoodsXDao categoryGoodsXDao;

    @Autowired
    CategoryService categoryService;

    public List<Category> findByGoodsName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
//        name = name + "%";
        CategoryGoodsX query = new CategoryGoodsX();
        query.setGoodsName(name);
        List<CategoryGoodsX> list = findByCondition(query);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        Collections.sort(list, Comparator.comparingInt(o -> o.getGoodsName().length()));
        List<Category> res = Lists.transform(list, item -> {
            return categoryService.selectById(item.getCategoryId());
        });

        return res;
    }
}
