package spring.ch03.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.ch03.member.MemberDao;
import spring.ch03.password.ChangePasswordService;
import spring.ch03.register.MemberRegisterService;

@Configuration
public class MemberConfiguration {

    @Bean
    public MemberDao getMemberDao() {
        return new MemberDao();
    }


    @Bean
    public MemberRegisterService getMemberRegisterService() {
        return new MemberRegisterService(getMemberDao());
    }
    @Bean
    public ChangePasswordService getChangePasswordService() {
        ChangePasswordService changePasswordService = new ChangePasswordService();
        changePasswordService.setMemberDao(getMemberDao());
        return changePasswordService;
    }

}
