package spring.ch03;

import org.junit.*;
import spring.ch03.exceptions.MemberNotFoundException;
import spring.ch03.member.Member;
import spring.ch03.member.MemberDao;
import spring.ch03.password.ChangePasswordService;
import spring.ch03.register.RegisterRequest;
import spring.ch03.register.MemberRegisterService;

import static junit.framework.TestCase.assertEquals;

public class AppTest {
    private static MemberDao memberDao;

    @BeforeClass
    public static void setUp() {
        memberDao = new MemberDao();
    }

    @After
    public void cleanUp() {
        memberDao.clear();
    }

    @AfterClass
    public static void tearDown() {
    }

    private Member newMember() {
        Member member = new Member();
        member.setEmail("park.suhyuk@gmail.com");
        member.setName("박수혁");
        member.setPassword("password");
        return member;
    }

    @Test
    public void 멤버등록() {
        Member member = newMember();
        memberDao.insert(member);
        Member foundMember = memberDao.findByEmail(member.getEmail());
        assertEquals(member.toString(), foundMember.toString());
    }

    private RegisterRequest newRegisterRequest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("park.suhyuk@gmail.com");
        registerRequest.setPassword("password");
        registerRequest.setName("박수혁");
        return registerRequest;
    }

    @Test
    public void 회원가입() {
        RegisterRequest registerRequest = newRegisterRequest();
        MemberRegisterService memberRegisterService = new MemberRegisterService(memberDao);
        memberRegisterService.regist(registerRequest);
        Member registeredMember = memberDao.findByEmail(registerRequest.getEmail());
        assertEquals(registerRequest.getEmail(), registeredMember.getEmail());
    }

    @Test(expected = MemberNotFoundException.class)
    public void 패스워드변경() {
        ChangePasswordService changePasswordService = new ChangePasswordService();
        changePasswordService.setMemberDao(memberDao);
        changePasswordService.changePassword("park.suhyuk@gmail.com", "password", "drowssap");
    }
}
