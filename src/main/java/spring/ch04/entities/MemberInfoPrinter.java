package spring.ch04.entities;

import org.springframework.beans.factory.annotation.Autowired;
import spring.ch04.exceptions.MemberNotFoundException;
import spring.ch04.repositories.MemberDao;

public class MemberInfoPrinter extends MemberPrinter {

    private MemberPrinter memberPrinter;
    private MemberDao memberDao;

    public void printMemberInfo(Member member) {
        if (!memberDao.existsMemberByEmail(member.getEmail())) {
            throw new MemberNotFoundException(member.getEmail() + " - 멤버가 없습니다");
        }
        memberPrinter.print(member);
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Autowired
    public void setMemberInfoPrinter(MemberPrinter memberPrinter) {
        this.memberPrinter = memberPrinter;
    }
}
