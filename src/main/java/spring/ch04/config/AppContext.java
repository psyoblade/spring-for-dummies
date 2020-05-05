package spring.ch04.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MemberConfiguration.class, ServiceConfiguration.class})
public class AppContext {
}
