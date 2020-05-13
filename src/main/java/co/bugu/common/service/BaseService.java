package co.bugu.common.service;

import co.bugu.common.dao.BaseDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author daocers
 * @Date 2020/4/7:11:10
 * @Description:
 */
public abstract class BaseService<T> {

    BaseDao<T> baseDao;

    /**
     * 通过主键删除
     *
     * @param id
     * @return
     */
    public int deleteById(Long id) {
        Preconditions.checkArgument(null != id);
        return baseDao.deleteById(id);
    }

    /**
     * insert，如果字段为空，略去空字段
     *
     * @param record
     * @return
     */
    public int insert(T record) {
        Preconditions.checkArgument(null != record);
        return baseDao.insert(record);
    }

    /**
     * 通过主键查询
     *
     * @param id
     * @return
     */
    public T selectById(Long id) {
        return baseDao.selectById(id);
    }

    /**
     * 通过主键更新单条数据
     *
     * @param record
     * @return
     */
    public int updateById(T record) {
        return baseDao.updateById(record);
    }

    /**
     * 通过条件查询
     *
     * @param record
     * @return
     */
    public List<T> findByCondition(T record) {
        return baseDao.findByObject(record);
    }


    /**
     * 分页查询，按照id 倒序排序
     *
     * @param
     * @return
     * @auther daocers
     * @date 2019-12-18 14:48
     */
    public List<T> findByCondition(Integer pageNum, Integer pageSize, T query) {
        return findByCondition(pageNum, pageSize, query, "");
    }

    /**
     * 分页查询 按照指定排序
     *
     * @param
     * @return
     * @auther daocers
     * @date 2019-12-18 14:48
     */
    public List<T> findByCondition(Integer pageNum, Integer pageSize, T query, String orderBy) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<T> result = baseDao.findByObject(query);
        return result;
    }


    /**
     * 条件查询，按照id 倒序排序
     *
     * @param
     * @return
     * @auther daocers
     * @date 2019-12-18 14:48
     */
    public PageInfo<T> findByConditionWithPage(Integer pageNum, Integer pageSize, T query) {
        return findByConditionWithPage(pageNum, pageSize, query, "");
    }

    /**
     * 分页查询， 分会页码信息
     * 按照指定分页
     *
     * @param
     * @return
     * @auther daocers
     * @date 2019-12-18 14:48
     */
    public PageInfo<T> findByConditionWithPage(Integer pageNum, Integer pageSize, T query, String orderBy) {
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<T> result = baseDao.findByObject(query);
        return new PageInfo<>(result);
    }

}
