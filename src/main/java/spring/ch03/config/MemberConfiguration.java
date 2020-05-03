package spring.ch03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.ch03.member.MemberDao;

@Configuration
@Import( { ServiceConfiguration.class } ) // n 개의 configuration 를 import 할 수 있다
public class MemberConfiguration {

    @Bean
    public MemberDao getMemberDao() {
        return new MemberDao();
    }


}
