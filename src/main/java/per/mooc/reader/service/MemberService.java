package per.mooc.reader.service;

import per.mooc.reader.entity.Member;
import per.mooc.reader.entity.MemberReadState;

public interface MemberService {
    public Member createMember(String username, String password, String nickname);
    public Member checkLogin(String username, String password);
    public Member selectById(Long memberId);
    public MemberReadState selectMemberReadState(Long memberId, Long bookId);
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState);
}