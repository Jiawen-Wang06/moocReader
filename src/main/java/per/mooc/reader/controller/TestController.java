package per.mooc.reader.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {
    @PostMapping("/t/test1")
    @ResponseBody
    public Map<String, String> test1(String content){
        Map<String, String> result = new HashMap<String, String>();
        result.put("test","ceshi"+content);
        return result;
    }
}