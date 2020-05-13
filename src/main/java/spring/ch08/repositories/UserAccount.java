package spring.ch08.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserAccount {

    private JdbcTemplate jdbcTemplate;

    public UserAccount(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String selectNameByEmail(String email) {
        List<String> results = jdbcTemplate.query("select name from users where email = ?",
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String name = null;
                        while (rs.next()) {
                            name = rs.getString("name");
                            break;
                        }
                        return name;
                    }
                }, email);
        return results.isEmpty() ? null : results.get(0);
    }

    public int selectCountAll() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }
}
