package spring.ch04.services;

import org.springframework.beans.factory.annotation.Autowired;
import spring.ch04.entities.Member;
import spring.ch04.exceptions.MemberNotFoundException;
import spring.ch04.exceptions.WrongIdPasswordException;
import spring.ch04.repositories.MemberDao;

public class ChangePasswordService {

    @Autowired
    private MemberDao memberDao;

    public void changePassword(String email, String oldPassword, String newPassword) {
        Member foundMember = memberDao.findByEmail(email);
        if (foundMember == null)
            throw new MemberNotFoundException(String.format("변경할 패스워드 이용자가 존재하지 않습니다 - %s", email));
        if (!foundMember.getPassword().equals(oldPassword))
            throw new WrongIdPasswordException(String.format("입력된 패스워드와 실제 패스워드가 다릅니다 - %s", foundMember));
        foundMember.setPassword(newPassword);
        memberDao.update(foundMember);
    }
}
