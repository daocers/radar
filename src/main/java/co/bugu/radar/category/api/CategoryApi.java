package co.bugu.radar.category.api;

import co.bugu.common.RespDto;
import co.bugu.common.enums.DelFlagEnum;
import co.bugu.radar.category.domain.Category;
import co.bugu.radar.category.dto.CategoryDto;
import co.bugu.radar.category.dto.TreeNodeDto;
import co.bugu.radar.category.service.CategoryService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author daocers
 * @Date 2020/5/13:16:09
 * @Description:
 */
@RestController
@RequestMapping("/category/api")
@Api(tags = "分类")
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
    @ApiIgnore
    @RequestMapping("/fuzzyQuery")
    public RespDto<List<CategoryDto>> fuzzyQuery(String name, Integer pageNum, Integer pageSize) {
        Preconditions.checkArgument(StringUtils.isNoneEmpty(name), "查询参数不能为空");
        PageInfo<Category> pageInfo = categoryService.findByNameLike(name, pageNum, pageSize);
        List<CategoryDto> res = Lists.transform(pageInfo.getList(), item -> {
            CategoryDto dto = new CategoryDto();
            dto.setId(item.getId());
            dto.setName(item.getName());
            dto.setValue(item.getName());
            return dto;
        });
        return RespDto.success(res);
    }


    /**
     * 获取树
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/5/13 16:53
     */
    @ApiOperation(value = "获取分类树", httpMethod = "GET")
    @RequestMapping("/getTree")
    public RespDto<List<TreeNodeDto>> getTree() {
        List<TreeNodeDto> list = categoryService.getTree();
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        return RespDto.success(list);
    }


    @ApiOperation(value = "添加标签", httpMethod = "POST")
    @RequestMapping("/save")
    public RespDto<Boolean> save(@RequestBody Category category) {
        Preconditions.checkArgument(null != category);
        category.setIsDel(DelFlagEnum.NO.getCode());

        if (category.getSuperiorId() == null || category.getSuperiorId() < 1) {
            category.setLevel(0);
        }
        Category superior = categoryService.selectById(category.getSuperiorId());
        if (null == superior) {
            category.setLevel(0);
        } else {
            category.setLevel(superior.getLevel() + 1);
        }
        if (null == category.getId()) {
            categoryService.insert(category);
        } else {
            category.setName(null);
            categoryService.updateById(category);
        }
        return RespDto.success(true);
    }

    @ApiIgnore
    @RequestMapping("/getFinalType")
    public RespDto<List<Category>> getFinalType() {
        List<Category> list = categoryService.getFinalType();
        return RespDto.success(list);
    }

    @ApiIgnore
    @RequestMapping("/findByCondition")
    public RespDto<List<Category>> findByCondition() {
        List<Category> list = categoryService.findByCondition(null);
        return RespDto.success(list);
    }

//    @RequestMapping("/findByName")
//    public RespDto<Category> findByName(String name) {
//        Category query = new Category();
//        query.setName(name);
//        List<Category> list = categoryService.findByCondition(1, 1, query);
//        if (CollectionUtils.isEmpty(list)) {
//            return RespDto.success(new Category());
//        }
//        return RespDto.success(list.get(0));
//    }
}
