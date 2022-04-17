package per.mooc.reader.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.mooc.reader.entity.Member;
import per.mooc.reader.entity.MemberReadState;
import per.mooc.reader.service.MemberService;
import per.mooc.reader.utils.ResponseUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.security.SecureRandom;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    @Resource
    private MemberService memberService;
    @PostMapping("/register")
    public ResponseUtils register(String username, String password, String nickname, String vc, HttpServletRequest request){
        ResponseUtils resp = null;
        String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");
        if(vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)){
            resp = new ResponseUtils("VerifyCodeError","Wrong verification code");
        }else{
            try{
                resp = new ResponseUtils();
                memberService.createMember(username,password,nickname);
            }catch (Exception e){
                e.printStackTrace();
                resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
            }

        }
        System.out.println(resp);
        return resp;

    }
    @PostMapping("/check_login")
    public ResponseUtils checkLogin(String username, String password, String vc, HttpServletRequest request){
        ResponseUtils resp = null;
        String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");

        if(vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)){
            resp = new ResponseUtils("VerifyCodeError","Wrong verification code");
        }else{
            try {
                Member m = memberService.checkLogin(username, password);
                /*Privacy concern*/
                m.setPassword(null);
                m.setSalt(null);
                resp = new ResponseUtils().put("member",m);
            } catch (Exception e) {
                e.printStackTrace();
                resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
            }
        }
        return resp;
    }
    @GetMapping("/select_by_id")
    public ResponseUtils selectById(Long memberId){
        ResponseUtils resp = null;
        try{
            Member m = memberService.selectById(memberId);
            resp = new ResponseUtils().put("member",m);
        }catch (Exception e){
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        return resp;
    }

    @GetMapping("/select_read_state")
    public ResponseUtils selectMemberReadState (Long memberId, Long bookId){
        ResponseUtils resp = null;
        try{
            MemberReadState mrs = memberService.selectMemberReadState(memberId,bookId);
            resp = new ResponseUtils().put("memberReadState", mrs);
        }catch (Exception e){
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        return resp;
    }
    @PostMapping("/update_read_state")
    public ResponseUtils updateReadState(Long memberId, Long bookId, Integer readState){
        ResponseUtils resp = null;
        try{
            MemberReadState mrs = memberService.updateMemberReadState(memberId,bookId,readState);
            resp = new ResponseUtils().put("memberReadState", mrs);
        }catch (Exception e){
            e.printStackTrace();
            resp = new ResponseUtils(e.getClass().getSimpleName(),e.getMessage());
        }
        return resp;
    }



}