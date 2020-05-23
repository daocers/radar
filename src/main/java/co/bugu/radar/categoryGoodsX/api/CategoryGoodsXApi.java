package co.bugu.radar.categoryGoodsX.api;

import co.bugu.common.RespDto;
import co.bugu.radar.category.domain.Category;
import co.bugu.radar.category.service.CategoryService;
import co.bugu.radar.categoryGoodsX.service.CategoryGoodsXService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author daocers
 * @Date 2020/5/23:00:04
 * @Description:
 */
@RequestMapping("/categoryGoods/api")
@RestController
public class CategoryGoodsXApi {
    @Autowired
    CategoryGoodsXService categoryGoodsXService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/findByGoodsName")
    public RespDto<Category> findByGoodsName(String goodsName) {
        List<Category> list = categoryGoodsXService.findByGoodsName(goodsName);
        if(CollectionUtils.isEmpty(list)){
            return RespDto.success(new Category());
        }
        return RespDto.success(list.get(0));
    }

}
