package com.nexus.security.component;

import cn.hutool.json.JSONUtil;

import com.nexus.common.api.ServerResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**

* @Description:    没有权限访问时

* @Author:         Nexus

* @CreateDate:     2020/9/9 21:53

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/9 21:53

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler{
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(ServerResponse.forbidden(e.getMessage())));
        response.getWriter().flush();
    }
}
