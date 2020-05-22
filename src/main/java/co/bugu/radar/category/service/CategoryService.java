package co.bugu.radar.category.service;

import co.bugu.common.service.BaseService;
import co.bugu.radar.category.dao.CategoryDao;
import co.bugu.radar.category.domain.Category;
import co.bugu.radar.category.dto.TreeNodeDto;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @Author daocers
 * @Date 2020-05-13 16:07
 * @Description:
 */
@Service
public class CategoryService extends BaseService<Category> {

    @Autowired
    CategoryDao categoryDao;

    /**
     * 通过name模糊查询，分页查找
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/5/13 16:27
     */
    public PageInfo<Category> findByNameLike(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = categoryDao.findByNameLike("%" + name + "%");
        return PageInfo.of(list);
    }

    /**
     * 获取树
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/5/13 16:57
     */
    public List<TreeNodeDto> getTree() {
        List<Category> branches = findByCondition(null);

        if (CollectionUtils.isNotEmpty(branches)) {
            List<TreeNodeDto> dtoList = Lists.transform(branches, new Function<Category, TreeNodeDto>() {
                @Nullable
                @Override
                public TreeNodeDto apply(@Nullable Category category) {
                    TreeNodeDto dto = new TreeNodeDto();
                    BeanUtils.copyProperties(category, dto);
                    return dto;
                }
            });

            List<TreeNodeDto> rootList = new ArrayList<>();
            Map<Long, List<TreeNodeDto>> info = new HashMap<>();
            for (TreeNodeDto dto : dtoList) {
                if (!info.containsKey(dto.getSuperiorId())) {
                    info.put(dto.getSuperiorId(), new ArrayList<>());
                }
                info.get(dto.getSuperiorId()).add(dto);
                if (dto.getSuperiorId() < 1) {
                    rootList.add(dto);
                }
            }
            for (TreeNodeDto TreeNodeDto : rootList) {
                TreeNodeDto.setChildren(getChildren(TreeNodeDto.getId(), info));
            }

            return rootList;

        }
        return null;
    }

    /**
     * 递归获取下级
     *
     * @param id
     * @param info
     * @return
     */
    private List<TreeNodeDto> getChildren(Long id, Map<Long, List<TreeNodeDto>> info) {
        List<TreeNodeDto> children = info.get(id);
        if (CollectionUtils.isEmpty(children)) {
            return null;
        } else {
            for (TreeNodeDto child : children) {
                child.setChildren(getChildren(child.getId(), info));
            }
        }
        return children;
    }

    public List<Category> getFinalType() {
        List<Category> res = new ArrayList<>();
        Set<Long> set = getNoneLeafNode();

        int pageNum = 1;
        int pageSize = 100;
        int count = 0;
        do {
            List<Category> list = findByCondition(pageNum, pageSize, null);
            if (CollectionUtils.isEmpty(list)) {
                break;
            }
            count = list.size();
            pageNum++;
            for (Category item : list) {
                if (!set.contains(item.getId())) {
                    item.setValue(item.getName());
                    res.add(item);
                }
            }
        } while (count == pageSize);
        return res;
    }

    /**
     * 获取非叶子节点
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/5/16 13:07
     */
    public Set<Long> getNoneLeafNode() {
        int pageNum = 1;
        int pageSize = 100;
        int count = 0;
        Set<Long> superiorSet = new HashSet<>();
        do {
            List<Category> list = findByCondition(pageNum, pageSize, null);
            if (CollectionUtils.isEmpty(list)) {
                break;
            }
            count = list.size();
            pageNum++;
            for (Category item : list) {
                long superiorId = item.getSuperiorId();
                superiorSet.add(superiorId);
            }
        } while (count == pageSize);
        return superiorSet;
    }
}
