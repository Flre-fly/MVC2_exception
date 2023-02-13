package hello.exception.v2;

import hello.exception.handlerExceptionResolver.MyHandlerExceptionResolver;
import hello.exception.handlerExceptionResolver.UserHandlerExceptionResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    //configureHandlerExceptionResolvers 가아니라 extendHandlerExceptionResolvers을 사용하는이유
    //전자를 사용하면 스프링이 기본으로 제공하는 ExceptionResolver 가 제거됨(주의)
    //왜 스프링이 제공하는 ExceptionResolver가 없어지면안되는거지?
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver>
                                                        resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        resolvers.add(new UserHandlerExceptionResolver());
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/css/**", "/*.ico"
                        , "/error", "/error-page/**" //오류 페이지 경로
                );
    }

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
