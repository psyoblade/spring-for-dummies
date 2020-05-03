package spring.ch03;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainForAssemblerTest {

    private String[] generateMember(String command, String email, String name, String pass) {
        return new String[]{ command, email, name, pass, pass };
    }

    private String[] generateLegalMember(String command) {
        String commands = String.format("%s park.suhyuk@gmail.com suhyuk password password", command);
        return commands.split(" ");
    }

    private String[] createIllegalMember() {
        String commands = "create park.suhyuk@gmail.com suhyuk password not-same-password";
        return commands.split(" ");
    }

    @Test
    public void 명령어통한_정상적인_멤버추가() {
        String[] legalMember = generateLegalMember("create");
        MainForAssembler.processNewCommand(legalMember);
        assertTrue(MainForAssembler.existsMember(legalMember[1]));
    }

    // 런타임 예외가 발생하면 Catch 코드가 있어도 바로 던져지는구나..
    @Test(expected = IllegalArgumentException.class)
    public void 명령어통한_비정상적인_멤버추가() {
        String[] illegalMember = createIllegalMember();
        MainForAssembler.processNewCommand(illegalMember);
        assertTrue(!MainForAssembler.existsMember(illegalMember[1]));
    }

    @Test
    public void 명령어통한_멤버패스워드_변경() {
        String[] legalMember = generateLegalMember("update");
        MainForAssembler.processChangeCommand(legalMember);
        assertTrue(MainForAssembler.existsMember(legalMember[1]));
    }

    @Test
    public void 추가된_멤버_목록() {
        String[] m1 = generateMember("create", "a@email.com", "a", "pass");
        String[] m2 = generateMember("create", "b@email.com", "b", "pass");
        MainForAssembler.processNewCommand(m1);
        MainForAssembler.processNewCommand(m2);
        MainForAssembler.processListCommand(new String[]{ "list" });
        assertTrue(MainForAssembler.existsMember("a@email.com"));
        assertTrue(MainForAssembler.existsMember("b@email.com"));
    }

}