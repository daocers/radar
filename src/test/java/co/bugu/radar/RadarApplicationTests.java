package co.bugu.radar;

import co.bugu.common.enums.DelFlagEnum;
import co.bugu.common.util.ExcelUtil;
import co.bugu.radar.category.domain.Category;
import co.bugu.radar.category.service.CategoryService;
import com.alibaba.fastjson.JSON;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
class RadarApplicationTests {

    @Test
    void contextLoads() {
    }

    public static Logger logger = LoggerFactory.getLogger(RadarApplicationTests.class);


    @Autowired
    CategoryService categoryService;

    @Test
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

}
