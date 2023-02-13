package hello.exception.handlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserHandlerExceptionResolver implements HandlerExceptionResolver {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof UserException) {
                log.info("UserException resolver to 400");
                //accept헤더의 값을 얻어서 application/json이면 어떠한 처리하기위함
                String acceptHeader = request.getHeader("accept");
                //500에러가 아닌 내가 설정한 상태코드로 설정
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                //HTTP 요청 해더의 ACCEPT 값이 application/json 이면 JSON으로 오류를 내려주고, 그 외 경우에는
                //error/500에 있는 HTML 오류 페이지를 보여준다.
                if ("application/json".equals(acceptHeader)) {
                    //보여줄 JSON데이터를 만드는 과정
                    Map<String, Object> errorResult = new HashMap<>();
                    errorResult.put("ex", ex.getClass());
                    errorResult.put("message", ex.getMessage());
                    String result =
                            objectMapper.writeValueAsString(errorResult);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().write(result);
                    //빈 modelandview를 넘겨줘서 html을 보여주지않고 json/application으로 응답함
                    return new ModelAndView();
                }else {
                    return new ModelAndView("error/500");
                }
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }
        return null;
    }
}
