package spring.ch07.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import spring.ch07.aspects.CalculateElapsedTime;
import spring.ch07.entities.NormalCalculator;
import spring.ch07.entities.RecursionCalculator;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class Ch07AppContext {

    @Bean
    public NormalCalculator normalCalculator() {
        return new NormalCalculator();
    }

    @Bean
    public RecursionCalculator recursionCalculator() {
        return new RecursionCalculator();
    }

    @Bean
    public CalculateElapsedTime calculateElapsedTime() {
        return new CalculateElapsedTime();
    }
}
