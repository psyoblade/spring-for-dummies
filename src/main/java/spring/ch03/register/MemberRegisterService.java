package spring.ch03.register;

import spring.ch03.exceptions.AlreadyExistsException;
import spring.ch03.member.Member;
import spring.ch03.member.MemberDao;

public class MemberRegisterService {

    private MemberDao memberDao;

    public MemberRegisterService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void regist(RegisterRequest registerRequest) throws AlreadyExistsException {
        Member foundMember = this.memberDao.findByEmail(registerRequest.getEmail());
        if (foundMember != null)
            throw new AlreadyExistsException(String.format("등록 하려는 멤버가 이미 존재합니다 - %s", foundMember));
        memberDao.insert(registerRequest.toMember());
    }
}
