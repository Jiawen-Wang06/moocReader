package per.mooc.reader.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import per.mooc.reader.entity.Member;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MemberServiceTestor {
    @Resource
    private MemberService memberService;
    @Test
    public void createMember1() {
        memberService.createMember("imooc_1","123","imooc_1");
    }

    @Test
    public void createMember2() {
        memberService.createMember("Kiki","123","Kiki");
    }
    @Test
    public void checkLogin() {
        Member m = memberService.checkLogin("imooc_1","123456");
        System.out.println(m);
    }
    @Test
    public void checkLogin2() {
        Member m = memberService.checkLogin("imooc_1dd","123456");
        System.out.println(m);
    }
}