package per.mooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.mooc.reader.entity.Category;
import per.mooc.reader.mapper.CategoryMapper;
import per.mooc.reader.service.CategoryService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jiawen
 *
 */
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> selectAll() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByAsc("category_id");
        return categoryMapper.selectList(wrapper);
    }
}