package spring.ch03.member;

import java.util.HashMap;
import java.util.Map;
import spring.ch03.exceptions.*;

public class MemberDao {

    private Long global_id = 0L;
    private Map<String, Member> members = new HashMap<>();

    public Member findByEmail(String email) {
        if (!members.containsKey(email))
            return null;
        return members.get(email);
    }

    public void insert(Member member) {
        String email = member.getEmail();
        if (members.containsKey(email))
            throw new AlreadyExistsException(String.format("입력하려는 email %s 이 이미 존재합니다", email));
        member.setId(++global_id);
        members.put(member.getEmail(), member);
    }

    public void update(Member member) {
        String email = member.getEmail();
        if (!members.containsKey(email))
            throw new MemberNotFoundException(String.format("업데이트할 email %s 이 존재하지 않습니다", email));
        members.put(member.getEmail(), member);
    }

    public void clear() {
        members.clear();
    }
}
