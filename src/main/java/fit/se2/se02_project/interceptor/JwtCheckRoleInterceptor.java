package fit.se2.se02_project.interceptor;

import fit.se2.se02_project.model.User;
import fit.se2.se02_project.service.CommonService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class JwtCheckRoleInterceptor implements HandlerInterceptor, ApplicationContextAware {

    private CommonService commonService;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.commonService = applicationContext.getBean(CommonService.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = commonService.getCurrentUser();
        if (user.getRole().getId() != 1) {
            response.sendRedirect("/auth/login");
            return false;
        }
        return true;
    }

    // You can leave these methods empty if you don't need them
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
