package per.mooc.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import per.mooc.reader.entity.TestTable;

/**
 * @author Jiawen
 */
public interface TestMapper extends BaseMapper<TestTable> {
    public void insertSample();
}