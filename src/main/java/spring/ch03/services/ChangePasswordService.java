package spring.ch03.services;

import spring.ch03.exceptions.MemberNotFoundException;
import spring.ch03.exceptions.WrongIdPasswordException;
import spring.ch03.entities.Member;
import spring.ch03.repositories.MemberDao;

public class ChangePasswordService {

    private MemberDao memberDao;

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        Member foundMember = memberDao.findByEmail(email);
        if (foundMember == null)
            throw new MemberNotFoundException(String.format("변경할 패스워드 이용자가 존재하지 않습니다 - %s", email));
        if (!foundMember.getPassword().equals(oldPassword))
            throw new WrongIdPasswordException(String.format("입력된 패스워드와 실제 패스워드가 다릅니다 - %s", foundMember));

    }
}
