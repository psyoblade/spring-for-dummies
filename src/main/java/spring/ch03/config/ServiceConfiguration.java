package spring.ch03.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.ch03.member.MemberDao;
import spring.ch03.password.ChangePasswordService;
import spring.ch03.register.MemberRegisterService;

@Configuration
public class ServiceConfiguration {

    @Autowired
    MemberDao memberDao;

    @Bean
    public MemberRegisterService getMemberRegisterService() {
        return new MemberRegisterService(memberDao);
    }

    @Bean
    public ChangePasswordService getChangePasswordService() {
        ChangePasswordService changePasswordService = new ChangePasswordService();
        changePasswordService.setMemberDao(memberDao);
        return changePasswordService;
    }
}
