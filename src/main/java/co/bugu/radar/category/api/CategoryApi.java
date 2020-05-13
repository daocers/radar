package co.bugu.radar.category.api;

import co.bugu.common.RespDto;
import co.bugu.radar.category.domain.Category;
import co.bugu.radar.category.dto.TreeNodeDto;
import co.bugu.radar.category.service.CategoryService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author daocers
 * @Date 2020/5/13:16:09
 * @Description:
 */
@RestController
@RequestMapping("/category/api")
public class CategoryApi {

    @Autowired
    CategoryService categoryService;


    /**
     * 模糊查询，确认这个分类存在不存在
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/5/13 16:11
     */
    @RequestMapping("/fuzzyQuery")
    public RespDto<List<Category>> fuzzyQuery(String name, Integer pageNum, Integer pageSize) {
        Preconditions.checkArgument(StringUtils.isNoneEmpty(name), "查询参数不能为空");
        PageInfo<Category> pageInfo = categoryService.findByNameLike(name, pageNum, pageSize);
        return RespDto.success(pageInfo.getList());
    }


    /**
     * 获取树
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/5/13 16:53
     */
    @RequestMapping("/getTree")
    public RespDto<List<TreeNodeDto>> getTree() {
        List<TreeNodeDto> list = categoryService.getTree();
        return RespDto.success(list);
    }


    @RequestMapping("/save")
    public RespDto<Boolean> save(Category category) {
        Preconditions.checkArgument(null != category);
        if (null == category.getId()) {
            categoryService.insert(category);
        } else {
            category.setName(null);
            categoryService.updateById(category);
        }
        return RespDto.success(true);
    }
}
