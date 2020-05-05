package spring.ch04.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.ch04.entities.MemberInfoPrinter;
import spring.ch04.entities.MemberPrinter;
import spring.ch04.services.ChangePasswordService;
import spring.ch04.services.MemberRegisterService;

@Configuration
public class ServiceConfiguration {

    @Autowired
    private MemberInfoPrinter memberInfoPrinter;

    @Bean
    public MemberRegisterService getMemberRegisterService() {
        return new MemberRegisterService();
    }

    @Bean
    public ChangePasswordService getChangePasswordService() {
        return new ChangePasswordService();
    }

    @Bean
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }

    @Bean
    public MemberInfoPrinter memberInfoPrinter() {
        return new MemberInfoPrinter();
    }

}
