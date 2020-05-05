package spring.ch04.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.ch04.entities.NoUseBean;
import spring.ch04.repositories.MemberDao;

@Configuration
public class MemberConfiguration {

    @Bean
    public MemberDao getMemberDao() {
        return new MemberDao();
    }

    @Bean
    public NoUseBean getNoUseBean() {
        return new NoUseBean();
    }

}
