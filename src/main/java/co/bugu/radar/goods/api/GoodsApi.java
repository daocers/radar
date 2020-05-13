package co.bugu.radar.goods.api;

import co.bugu.common.RespDto;
import co.bugu.radar.goods.domain.Goods;
import co.bugu.radar.goods.service.GoodsService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/save")
    public RespDto<Boolean> save(Goods goods) {
        Preconditions.checkArgument(null != goods);
        if (goods.getId() != null) {
            goodsService.updateById(goods);
        } else {
            goodsService.insert(goods);
        }
        return RespDto.success(true);
    }


    @RequestMapping("/findByCategory")
    public RespDto<PageInfo<Goods>> findByCategory(Long categoryId, Integer pageNum, Integer pageSize) {
        Goods goods = new Goods();
        goods.setCategoryId(categoryId);
        PageInfo<Goods> pageInfo = goodsService.findByConditionWithPage(pageNum, pageSize, goods);

        return RespDto.success(pageInfo);
    }
}
