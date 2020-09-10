package com.cmdc.infrastructure.filter;

import com.cmdc.infrastructure.common.Constant;
import com.cmdc.infrastructure.shirotoken.JwtToken;
import com.cmdc.infrastructure.util.JwtUtil;
import com.cmdc.interfaces.dto.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 这个类最主要的目的是:当请求需要校验权限，token是否具有权限时，构造出主体subject执行login()
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 执行登录认证
     * @param request ServletRequest
     * @param response ServletResponse
     * @param mappedValue mappedValue
     * @return 是否成功
     */
    @Override
    //这个方法叫做  尝试进行登录的操作,如果token存在,那么进行提交登录,如果不存在说明可能是正在进行登录或者做其它的事情 直接放过即可
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            boolean b = executeLogin(request, response);
            if (!b) {
                log.error("请登录");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 未携带token不能访问请求   开放请求在shiroconfig中配置
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String token = ((HttpServletRequest) request).getHeader("authorization");
        if (token==null || token.length()==0) {
            responseError(response,401,"尚未登录");
            return false;
        }
        String username = JwtUtil.getUserId(token);
        if (!JwtUtil.verify(token,username)) {
            responseError(response,401,"token 验证失败");
            return  false;
        }
        return true;
    }

    private void responseError(ServletResponse response,int code,String errorMsg) throws  IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");		httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpServletResponse.setContentType("application/json; charset=UTF-8");
        JsonResult baseResponse = new JsonResult(code,errorMsg);
        OutputStream os = httpServletResponse.getOutputStream();
        os.write(new ObjectMapper().writeValueAsString(baseResponse).getBytes("UTF-8"));
        os.flush();
        os.close();
    }

    /**
     * 执行登录
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        log.info("进入JwtFilter类中...");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(Constant.TOKEN_HEADER_NAME);
        log.info("获取到的token是:{}",token);
        // 判断token是否存在
        if (token == null) {
            return false;
        }
        JwtToken jwtToken = new JwtToken(token);
        try{
            log.info("提交UserModularRealmAuthenticator决定由哪个realm执行操作...");
            getSubject(request, response).login(jwtToken);
        } catch (AuthenticationException e){
            log.info("身份令牌过期, 请重新登录 !");
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}