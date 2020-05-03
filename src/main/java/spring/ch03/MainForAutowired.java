package spring.ch03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import spring.ch03.member.Member;
import spring.ch03.member.MemberDao;
import spring.ch03.password.ChangePasswordService;
import spring.ch03.register.MemberRegisterService;
import spring.ch03.register.RegisterRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

// 아래와 같은 구현은 의도한대로 동작하지 않는데 이는 ApplicationContext 에서 매개변수로 들어가지 않았기 때문에 Configuration 을 찾을 수 없기 때문이다
// SpringBoot 에서는 ComponentScan 이라는 기법을 통해서 자동으로 찾아주기 때문에 동작하는 것이다
// 제대로 동작하게 하기 위해서는 SpringBoot 의 ComponentScan 즉, SpringBootApplication 으로 구현하여 자동으로 찾게 만들고 ApplicationRunner 를 이용해서 구현하면 된다
public class MainForAutowired {
    // 아래는 동작하지 않는 코드
    @Autowired private MemberDao memberDao;
    @Autowired private MemberRegisterService memberRegisterService;
    @Autowired private ChangePasswordService changePasswordService;

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

    public static void main(String[] args) throws IOException {
        MainForAutowired main = new MainForAutowired();
        main.run();
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
        validateLength(commands, 5);
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
        list.forEach(System.out::println);
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
