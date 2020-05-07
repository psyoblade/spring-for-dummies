package spring.ch06.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.ch06.entities.ExternalLibrary;
import spring.ch06.entities.Greeting;

@Configuration
public class Ch06AppContext {
    private Logger logger = LoggerFactory.getLogger(Ch06AppContext.class);

    @Bean
    public Greeting greeting() {
        return new Greeting();
    }

    @Bean(initMethod = "initializeExternalBean", destroyMethod = "destroyExternalBean")
    public ExternalLibrary externalLibrary() {
        return new ExternalLibrary();
    }

}
