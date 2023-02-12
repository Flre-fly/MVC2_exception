package hello.exception.v2;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        log.info("{}: {}",httpRequest.getRequestURL(), httpRequest.getDispatcherType());
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        log.info("filter destroy");

    }
}
