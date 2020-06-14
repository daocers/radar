package co.bugu.radar;

import co.bugu.common.enums.DelFlagEnum;
import co.bugu.common.util.ExcelUtil;
import co.bugu.radar.category.domain.Category;
import co.bugu.radar.category.service.CategoryService;
import co.bugu.radar.goods.domain.Goods;
import co.bugu.radar.goods.service.GoodsService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class RadarApplicationTests {

    @Test
    void contextLoads() {
    }

    public static Logger logger = LoggerFactory.getLogger(RadarApplicationTests.class);


    @Autowired
    CategoryService categoryService;

    @Autowired
    GoodsService goodsService;

//    @Test
    public void test() throws IOException, InvalidFormatException {
        File file = new File("d:/123.xlsx");
        List<List<String>> data = ExcelUtil.getData(file);
        logger.info("data:{}", JSON.toJSONString(data, true));


        List<String> superiors = new ArrayList<>();
        Map<String, Long> nameIdMap = new HashMap<>();
        for (List<String> line : data) {
            String name = "";
            long superiorId = -1;
            if (!line.get(1).equals("-")) {
                name = line.get(1);
                superiors.clear();
                superiors.add(name);


            } else if (!line.get(3).equals("-")) {
                name = line.get(3);
                superiors = superiors.subList(0, 1);
                superiors.add(name);
                superiorId = nameIdMap.get(superiors.get(0));
            } else  if(!line.get(5).equals("-")){
            	name = line.get(5);
				superiors = superiors.subList(0, 2);
            	superiors.add(name);
            	superiorId = nameIdMap.get(superiors.get(1));
			}else if(line.size() > 7 && !line.get(7).equals("-")){
            	name = line.get(7);
				superiors = superiors.subList(0, 3);
            	superiorId = nameIdMap.get(superiors.get(2));
			}


            Category category = new Category();
            category.setName(name);
            category.setLevel(superiors.size());
            category.setIsDel(DelFlagEnum.NO.getCode());
            category.setSuperiorId(superiorId);
            categoryService.insert(category);
            nameIdMap.put(name, category.getId());
        }
    }


    @Test
    public void initGoods() throws IOException, InvalidFormatException {
        File file = new File("d:/goods.xlsx");
        List<List<String>> data = ExcelUtil.getData(file);
        for(List<String> line: data ){
            String name = line.get(1);
            Goods goods = new Goods();
            goods.setName(name);
            goods.setIsDel(DelFlagEnum.NO.getCode());
            goodsService.insert(goods);
        }

    }

    @Test
    public void processCategory(){
        List<Category> categoryList = categoryService.findByCondition(null);
        Map<String, Long> map = new HashMap<>();
        Set<String> set = new HashSet<>();
        for(Category category: categoryList){
            map.put(category.getName(), category.getId());
            set.add(category.getName());
        }

        int pageNum = 1;
        int pageSize = 100;
        int count = 0;

        do{
            List<Goods> list = goodsService.findByCondition(pageNum, pageSize, null);
            if(CollectionUtils.isEmpty(list)){
                break;
            }
            pageNum++;
            count = list.size();

            for(Goods goods: list){
                String name = goods.getName();
                for(String categoryName: set){
                    if(StringUtils.isEmpty(categoryName)){
                        continue;
                    }
                    if(name.endsWith(categoryName)){
                        Long categoryId = map.get(categoryName);
                        goods.setCategoryId(categoryId);
                        goodsService.updateById(goods);
                        break;
                    }
                }

            }

        }while (count == pageSize);
    }

}
