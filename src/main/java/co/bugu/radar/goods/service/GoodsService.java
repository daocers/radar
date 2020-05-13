package co.bugu.radar.goods.service;

import co.bugu.common.service.BaseService;
import co.bugu.radar.goods.dao.GoodsDao;
import co.bugu.radar.goods.domain.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author daocers
 * @Date 2020-05-13 17:46
 * @Description:
 */
@Service
public class GoodsService extends BaseService<Goods> {
    @Autowired
    GoodsDao goodsDao;

}
