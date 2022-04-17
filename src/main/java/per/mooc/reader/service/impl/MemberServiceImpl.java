package per.mooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import per.mooc.reader.entity.MemberReadState;
import per.mooc.reader.mapper.MemberMapper;
import per.mooc.reader.mapper.MemberReadstateMapper;
import per.mooc.reader.service.MemberService;

import javax.annotation.Resource;
import per.mooc.reader.entity.Member;
import per.mooc.reader.service.exception.MemberException;
import per.mooc.reader.utils.Md5Utils;
import per.mooc.reader.utils.ResponseUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberReadstateMapper memberReadstateMapper;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<per.mooc.reader.entity.Member> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        List<Member> members = memberMapper.selectList(wrapper);
        System.out.println(members.size());
        if(members.size() > 0){
            throw new MemberException("Username already existed");
        }
        Member m = new Member();
        m.setUsername(username);
        m.setNickname(nickname);
        m.setCreateTime(new Date());
        int salt = new Random().nextInt(1000) + 1000;
        m.setSalt(salt);
        String md5 = Md5Utils.md5Digest(password,salt);
        m.setPassword(md5);
        memberMapper.insert(m);
        return m;
    }
    @Override
    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Member m = memberMapper.selectOne(wrapper);
        if(m == null){
            throw new MemberException("User doesn't exist");
        }
        String md5 = Md5Utils.md5Digest(password,m.getSalt());
        if(!md5.equals(m.getPassword())){
            throw new MemberException("Wrong password");
        }
         return m;
    }
    @Override
    public Member selectById(Long memberId) {
        return memberMapper.selectById(memberId);
    }
    @Override
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId);
        wrapper.eq("book_id", bookId);
        MemberReadState mr = memberReadstateMapper.selectOne(wrapper);
        return mr;
    }
    @Override
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {
        QueryWrapper<MemberReadState> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq("book_id", bookId);

        MemberReadState mrs = memberReadstateMapper.selectOne(wrapper);
        if(mrs == null){
            mrs = new MemberReadState();
            mrs.setMemberId(memberId);
            mrs.setBookId(bookId);
            mrs.setReadState(readState);
            mrs.setCreateTime(new Date());
            memberReadstateMapper.insert(mrs);
        }else{
            mrs.setReadState(readState);
            mrs.setCreateTime(new Date());
            memberReadstateMapper.updateById(mrs);
        }

        return mrs;
    }
}