package spring.ch04.entities;

public class MemberPrinter {

    public void print(Member member) {
        System.out.println(String.format("Member -> Email: %s, Name: %s", member.getEmail(), member.getName()));
    }
}
