package spring.ch08.repositories;

import com.mysql.cj.core.io.BaseDecoratingValueFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.ch08.interfaces.IChangeProfile;

@Repository
public class ChangeProfileRepository implements IChangeProfile {

    private JdbcTemplate jdbcTemplate;

    public ChangeProfileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional
    public void changeUsernameAndAge(String email, String username, int age) {
        String updateUsername = "update ";
        updateUsername += "users set username = ? where email = ?";
        jdbcTemplate.update(updateUsername, username, email);

        if (age > 100)
            throw new IllegalArgumentException("나이가 100살보다 많을 수 없습니다.");

        String updateAge = "update ";
        updateAge += "users set age = ? where email = ?";
        jdbcTemplate.update(updateAge, age, email);
    }

    @Override
    public int getAge(String email) {
        String sql = String.format("select age from users where email = '%s'", email);
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public String getName(String email) {
        String sql = String.format("select username from users where email = '%s'", email);
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}
