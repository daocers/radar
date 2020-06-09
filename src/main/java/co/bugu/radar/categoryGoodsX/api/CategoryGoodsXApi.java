package co.bugu.radar.categoryGoodsX.api;

import co.bugu.common.RespDto;
import co.bugu.common.enums.DelFlagEnum;
import co.bugu.radar.category.domain.Category;
import co.bugu.radar.category.service.CategoryService;
import co.bugu.radar.categoryGoodsX.domain.CategoryGoodsX;
import co.bugu.radar.categoryGoodsX.service.CategoryGoodsXService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(tags = "商品标签")
public class CategoryGoodsXApi {
    @Autowired
    CategoryGoodsXService categoryGoodsXService;
    @Autowired
    CategoryService categoryService;

    @ApiOperation(value = "通过标签查询分类", httpMethod = "GET")
    @RequestMapping("/findByGoodsName")
    public RespDto<Category> findByGoodsName(String goodsName) {
        List<Category> list = categoryGoodsXService.findByGoodsName(goodsName);
        if (CollectionUtils.isEmpty(list)) {
            return RespDto.success(new Category());
        }
        return RespDto.success(list.get(0));
    }


    @ApiOperation(value = "添加标签标签和分类的关系", httpMethod = "POST")
    @RequestMapping("/save")
    public RespDto<Boolean> save(@RequestBody CategoryGoodsX categoryGoodsX) {
        CategoryGoodsX query = new CategoryGoodsX();
        query.setGoodsName(categoryGoodsX.getGoodsName());
        query.setCategoryId(categoryGoodsX.getCategoryId());
        List<CategoryGoodsX> xList = categoryGoodsXService.findByCondition(query);
        if (CollectionUtils.isEmpty(xList)) {
            CategoryGoodsX x = new CategoryGoodsX();
            x.setCategoryId(categoryGoodsX.getCategoryId());
            x.setGoodsName(categoryGoodsX.getGoodsName());
            x.setIsDel(DelFlagEnum.NO.getCode());
            categoryGoodsXService.insert(x);
        }
        return RespDto.success(true);
    }

}
