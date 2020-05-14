package spring.ch08.repositories;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import spring.ch08.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class UserAccount {

    private JdbcTemplate jdbcTemplate;

    public UserAccount(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

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

    public void update(User user) {
        jdbcTemplate.update(
                "update user set name = ?, password = ? where email = ?",
                user.getUsername(), user.getPassword(), user.getEmail()
        );
    }

    public int insertUser(String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(
                        "insert into users (name) values (?)",
                        new String[] {"id"});
                pstmt.setString(1, name);
                return pstmt;
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey();
        assert keyValue != null;
        return keyValue.intValue();
    }

    public int lambdaInsertUser(String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update((Connection con) -> {
            PreparedStatement pstmt = con.prepareStatement("insert into users (name) values (?)", new String[] {"id"});
            pstmt.setString(1, name);
            return pstmt;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }
}
