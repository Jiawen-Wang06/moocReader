package per.mooc.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import per.mooc.reader.entity.Evaluation;

import java.util.List;
import java.util.Map;

public interface EvaluationMapper extends BaseMapper<Evaluation> {
    public List<Map> selectByBookId(Long bookId);//关联查询
}