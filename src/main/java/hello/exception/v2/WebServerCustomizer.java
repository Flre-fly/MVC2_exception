package hello.exception.v2;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component
public class WebServerCustomizer implements
        WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/errorpage/404");
                ErrorPage errorPage500 = new
                ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");
        //RuntimeException하위의 예외에 대해서는 모두 아래의 에러페이지를 호출해줌
        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/errorpage/500");
                factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }
}