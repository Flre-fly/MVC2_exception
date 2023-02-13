package hello.exception.v2;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new
                FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        //값안넣어주면 기본값이 request여서 클라이언트가 요청할 경우에만 필터가 호출됨
        //request + error의 경우 필터가 호출되도록 함
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST,
                DispatcherType.ERROR);
        return filterRegistrationBean;
    }
}
