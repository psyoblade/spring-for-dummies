package spring.ch05.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import spring.ch05.annotations.CustomBean;
import spring.ch05.services.Calculator;

@Configuration
@ComponentScan(
        basePackages = "spring.ch05",
        includeFilters = @Filter(type = FilterType.REGEX, pattern = "spring\\..*Calculator"),
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = CustomBean.class)
)
public class Ch5Context {
    // 이미 calculator 라는 singleton 빈은 생성되어 있을텐데 다시 override 될까?
    // 그리고 기존에 생성되었던 getBean 을 통한 객체와 같을까?
    @Bean
    public Calculator calculator() {
        return new Calculator();
    }

    @Bean
    public Calculator getCalculator() {
        return new Calculator();
    }
}
