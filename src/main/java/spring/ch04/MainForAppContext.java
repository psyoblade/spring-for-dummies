package spring.ch04;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ch04.config.AppContext;
import spring.ch04.entities.Member;
import spring.ch04.entities.MemberInfoPrinter;
import spring.ch04.entities.MemberPrinter;
import spring.ch04.entities.RegisterRequest;
import spring.ch04.repositories.MemberDao;
import spring.ch04.services.ChangePasswordService;
import spring.ch04.services.MemberRegisterService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainForAppContext {

    private MemberDao memberDao;
    private MemberRegisterService memberRegisterService;
    private ChangePasswordService changePasswordService;
    private MemberPrinter memberPrinter;
    private MemberInfoPrinter memberInfoPrinter;

    public MainForAppContext() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
        memberDao = ctx.getBean(MemberDao.class);
        memberRegisterService = ctx.getBean(MemberRegisterService.class);
        changePasswordService = ctx.getBean(ChangePasswordService.class);
        memberPrinter = ctx.getBean("memberPrinter", MemberPrinter.class); // 명확하게 하나의 클래스가 아니라면 이름으로 구분되어야만 한다
        memberInfoPrinter = ctx.getBean("memberInfoPrinter", MemberInfoPrinter.class);
    }

    public static void main(String[] args) throws IOException {
        MainForAppContext app = new MainForAppContext();
        app.run();
    }

    public void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("명령어를 입력하세요");
            String line = br.readLine();
            if (line.equalsIgnoreCase("exit")) {
                System.out.println("종료합니다");
                break;
            }
            try {
                String[] commands = line.split(" ");
                if (commands.length == 0)
                    throw new IllegalArgumentException(String.format("입력 값은 최소 1개 이상의 키워드로 구성되어야 합니다. length = %d", commands.length));
                String command = commands[0];
                if (command.startsWith("create"))
                    processNewCommand(commands);
                else if (command.startsWith("update"))
                    processChangeCommand(commands);
                else if (command.startsWith("list"))
                    processListCommand(commands);
                else
                    printHelp();
            } catch(RuntimeException e) {
                e.printStackTrace();
                printHelp();
            }
        }
    }

    public boolean existsMember(String email) {
        Member member = memberDao.findByEmail(email);
        return member != null;
    }

    // 입력된 명령어를 통해 RegisterRequest 생성 후 registerService 통해 등록
    // 0:command, 1:email, 2:username, 3:password, 4:password
    public void processNewCommand(String[] commands) {
        validateLength(commands, 5);
        validatePasswordMatch(commands);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(commands[1]);
        registerRequest.setName(commands[2]);
        registerRequest.setPassword(commands[3]);
        memberRegisterService.regist(registerRequest);
    }

    public void processChangeCommand(String[] commands) {
        validateLength(commands, 4);
        String email = commands[1];
        String oldPass = commands[3];
        String newPass = commands[4];
        changePasswordService.changePassword(email, oldPass, newPass);
    }

    public void processListCommand(String[] commands) {
        validateLength(commands, 1);
        printMembers(memberDao.list());
    }

    private void printMembers(List<Member> list) {
        list.forEach(memberInfoPrinter::printMemberInfo);
    }

    private void validateLength(String[] commands, int length) {
        if (commands.length != length)
            throw new IllegalArgumentException(String.format("명령어의 개수는 반드시 5개여야 합니다 - %d", commands.length));
    }

    private void validatePasswordMatch(String[] commands) {
        if (!commands[3].equals(commands[4]))
            throw new IllegalArgumentException(String.format("패스워드가 일치하지 않습니다 - %s != %s", commands[3], commands[4]));
    }

    private void printHelp() {
        System.out.println("create email username password password");
        System.out.println("update email old-password new-password");
    }
}
