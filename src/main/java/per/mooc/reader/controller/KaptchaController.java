package per.mooc.reader.controller;

import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import per.mooc.reader.utils.ResponseUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Resource
    private Producer kaptchaProducer;
    private static Logger logger = LoggerFactory.getLogger(KaptchaController.class);
    @GetMapping("/api/verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        //响应立即过期

        response.setDateHeader("Expires",0);
        //不缓存任何图片数据
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/png");
        //生成验证码图片
        String text = kaptchaProducer.createText();
        logger.info("kaptcha text: "+text);
        request.getSession().setAttribute("kaptchaVerifyCode",text);
        if(request.getSession() == null){
            logger.info("Session is null");
        }else{
            logger.info("Kaptcha session: "+request.getSession());
        }

        if(kaptchaProducer == null){
            logger.info("kaptchaProducer is null");
        }else{
            logger.info(String.valueOf(kaptchaProducer));
        }
        BufferedImage image = kaptchaProducer.createImage(text);
        logger.info("kaptcha image "+image);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}