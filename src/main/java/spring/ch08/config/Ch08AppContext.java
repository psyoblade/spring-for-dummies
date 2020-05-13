package spring.ch08.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import spring.ch08.repositories.UserAccount;

@Configuration
public class Ch08AppContext {

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource datasource = new DataSource();
        datasource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // com.mysql.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://localhost/psyoblade?characterEncoding=utf8");
        datasource.setUsername("suhyuk");
        datasource.setPassword("");
        datasource.setInitialSize(2);  // 초기 커넥션 풀 크기
        datasource.setMaxActive(10);  // 최대 접속 가능 커넥션 크기
        datasource.setMaxIdle(10); // 최대 idle 커넥션 수 <= maxActive
        datasource.setTestWhileIdle(true);  // 커넥션 풀에 유휴 커넥션이 있는지 여부를 체크함 (노는 커넥션을 제거하기 위함)
        datasource.setMinEvictableIdleTimeMillis(1000 * 60 * 3);  // 최소 유휴 시간을 3분 : idle 옵션을 true 로 정하고 이 값을 정하면 3분이 지난 커넥션은 커넥션 풀에서 제거하고 idle 로 빠지게 된다
        datasource.setTimeBetweenEvictionRunsMillis(1000 * 10);  // 커넥션 풀의 커넥션 객체의 유휴 여부 체크를 10초에 한 번씩 한다
        return datasource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public UserAccount userAccount() {
        return new UserAccount(jdbcTemplate());
    }
}
