package per.mooc.reader.service.impl;

import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.mooc.reader.entity.Evaluation;
import per.mooc.reader.mapper.EvaluationMapper;
import per.mooc.reader.service.EvaluationService;

import javax.annotation.Resource;
import java.util.Date;
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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Evaluation evaluate(Long memberId, Long bookId, Integer score, String content) {
        Evaluation evaluation = new Evaluation();
        evaluation.setMemberId(memberId);
        evaluation.setBookId(bookId);
        evaluation.setScore(score);
        evaluation.setContent(content);
        evaluation.setCreateTime(new Date());
        evaluation.setState("enable");
        evaluation.setEnjoy(0);
        evaluationMapper.insert(evaluation);
        return evaluation;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Evaluation enjoy(Long evaluationId) {
       Evaluation e = evaluationMapper.selectById(evaluationId);
       e.setEnjoy(e.getEnjoy()+1);
       evaluationMapper.updateById(e);
       return e;
    }
}