package spring.ch04.services;

import org.springframework.beans.factory.annotation.Autowired;
import spring.ch04.entities.Member;
import spring.ch04.entities.RegisterRequest;
import spring.ch04.exceptions.AlreadyExistsException;
import spring.ch04.repositories.MemberDao;

public class MemberRegisterService {

    @Autowired
    private MemberDao memberDao;

    public void regist(RegisterRequest registerRequest) throws AlreadyExistsException {
        Member foundMember = memberDao.findByEmail(registerRequest.getEmail());
        if (foundMember != null)
            throw new AlreadyExistsException(String.format("등록 하려는 멤버가 이미 존재합니다 - %s", foundMember));
        memberDao.insert(registerRequest.toMember());
    }
}
