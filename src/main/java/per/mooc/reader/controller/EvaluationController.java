package per.mooc.reader.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.mooc.reader.service.EvaluationService;
import per.mooc.reader.utils.ResponseUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {
    @Resource
    private EvaluationService evaluationService;
    @GetMapping("/list")
    public ResponseUtils list(Long bookId){
        ResponseUtils resp = null;
        try{

            List<Map> evaluationList = evaluationService.selectByBookId(bookId);
            resp = new ResponseUtils().put("list",evaluationList);
        }catch (Exception e){
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        return resp;
    }

}