package co.bugu.radar.goods.api;

import co.bugu.common.RespDto;
import co.bugu.common.enums.DelFlagEnum;
import co.bugu.radar.category.domain.Category;
import co.bugu.radar.category.service.CategoryService;
import co.bugu.radar.categoryGoodsX.domain.CategoryGoodsX;
import co.bugu.radar.categoryGoodsX.service.CategoryGoodsXService;
import co.bugu.radar.goods.domain.Goods;
import co.bugu.radar.goods.service.GoodsService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author daocers
 * @Date 2020/5/13:17:35
 * @Description:
 */
@RestController
@RequestMapping("/goods/api")
public class GoodsApi {

    @Autowired
    GoodsService goodsService;

    @Autowired
    CategoryGoodsXService xService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/save")
    public RespDto<Boolean> save(@RequestBody Goods goods) {
        Preconditions.checkArgument(null != goods);
        goods.setIsDel(DelFlagEnum.NO.getCode());
        goods.setCode(goodsService.genCode(goods));
        if (goods.getId() != null) {
            goodsService.updateById(goods);
        } else {
            goodsService.insert(goods);
        }

        CategoryGoodsX x = new CategoryGoodsX();
        x.setCategoryId(goods.getCategoryId());
        x.setGoodsName(goods.getGoodsName());
        x.setIsDel(DelFlagEnum.NO.getCode());
        xService.insert(x);

        return RespDto.success(true);
    }


    @RequestMapping("/findByCondition")
    public RespDto<PageInfo<Goods>> findByCategory(Goods goods, Integer pageNum, Integer pageSize) {
        PageInfo<Goods> pageInfo = goodsService.findByConditionWithPage(pageNum, pageSize, goods);
        if(CollectionUtils.isNotEmpty(pageInfo.getList())){
            CategoryGoodsX query = new CategoryGoodsX();
            for(Goods item: pageInfo.getList()){
                query.setCategoryId(item.getCategoryId());
                List<CategoryGoodsX> list =
                        xService.findByCondition(query);
                if(CollectionUtils.isNotEmpty(list)){
                    item.setGoodsName(list.get(0).getGoodsName());
                    Category category = categoryService.selectById(list.get(0).getCategoryId());
                    item.setCategoryName(category.getName());
                }
            }
        }
        return RespDto.success(pageInfo);
    }
}
