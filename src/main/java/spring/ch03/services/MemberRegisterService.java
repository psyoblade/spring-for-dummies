package spring.ch03.services;

import spring.ch03.exceptions.AlreadyExistsException;
import spring.ch03.entities.Member;
import spring.ch03.repositories.MemberDao;
import spring.ch03.entities.RegisterRequest;

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
