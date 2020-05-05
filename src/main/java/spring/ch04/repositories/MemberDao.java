package spring.ch04.repositories;


import spring.ch04.entities.Member;
import spring.ch04.exceptions.AlreadyExistsException;
import spring.ch04.exceptions.MemberNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberDao {

    private Long global_id = 0L;
    private Map<String, Member> members = new HashMap<>();

    public Member findByEmail(String email) {
        if (!members.containsKey(email))
            return null;
        return members.get(email);
    }

    public boolean existsMemberByEmail(String email) {
        return members.containsKey(email);
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

    public List<Member> list() {
        return new ArrayList<>(members.values());
    }

    public void clear() {
        members.clear();
    }
}
