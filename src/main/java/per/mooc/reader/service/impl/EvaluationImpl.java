package per.mooc.reader.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.mooc.reader.mapper.EvaluationMapper;
import per.mooc.reader.service.EvaluationService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class EvaluationImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Override
    public List<Map> selectByBookId(Long bookId) {
     return evaluationMapper.selectByBookId(bookId);

    }
}