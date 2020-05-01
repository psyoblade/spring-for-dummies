package spring.ch03;

import spring.ch03.assembler.Assembler;
import spring.ch03.member.Member;
import spring.ch03.register.RegisterRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainForAssembler {
    private static Assembler assembler = new Assembler();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("명령어를 입력하세요");
            String command = br.readLine();
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("종료합니다");
                break;
            }
            try {
                if (command.startsWith("create"))
                    processNewCommand(command.split(" "));
                else if (command.startsWith("update"))
                    processChangeCommand(command.split(" "));
                else
                    printHelp();
            } catch(RuntimeException e) {
                e.printStackTrace();
                printHelp();
            }
        }
    }

    public static boolean existsMember(String email) {
        Member member = assembler.getMemberDao().findByEmail(email);
        return member != null;
    }

    // 입력된 명령어를 통해 RegisterRequest 생성 후 registerService 통해 등록
    // 0:command, 1:email, 2:username, 3:password, 4:password
    public static void processNewCommand(String[] commands) {
        validateLength(commands);
        validatePasswordMatch(commands);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(commands[1]);
        registerRequest.setName(commands[2]);
        registerRequest.setPassword(commands[3]);
        assembler.getMemberRegisterService().regist(registerRequest);
    }

    public static void processChangeCommand(String[] commands) {
        validateLength(commands);
        RegisterRequest registerRequest = new RegisterRequest();
        String email = commands[1];
        String oldPass = commands[3];
        String newPass = commands[4];
        assembler.getChangePasswordService().changePassword(email, oldPass, newPass);
    }

    private static void validateLength(String[] commands) {
        if (commands.length != 5)
            throw new IllegalArgumentException(String.format("명령어의 개수는 반드시 5개여야 합니다 - %d", commands.length));
    }

    private static void validatePasswordMatch(String[] commands) {
        if (!commands[3].equals(commands[4]))
            throw new IllegalArgumentException(String.format("패스워드가 일치하지 않습니다 - %s != %s", commands[3], commands[4]));
    }

    private static void printHelp() {
        System.out.println("create email username password password");
        System.out.println("update email old-password new-password");
    }
}
