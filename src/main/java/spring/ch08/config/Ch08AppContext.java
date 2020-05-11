package spring.ch08.config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;

public class Ch08AppContext {

    @Bean
    public DataSource dataSource() {
        DataSource datasource = new DataSource();
        datasource.setDriverClassName("com.mysql.jdbc.Driver");
        datasource.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8");
        datasource.setUsername("spring5");
        datasource.setPassword("spring5");
        datasource.setInitialSize(2);
        datasource.setMaxActive(10);
        return datasource;
    }
}
