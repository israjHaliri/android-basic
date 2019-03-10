package com.gen.duo.service.api.handler;

import com.gen.duo.service.core.DAO.UserDAO;
import com.gen.duo.service.common.dto.User;
import com.gen.duo.service.api.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by israjhaliri on 8/30/17.
 */
@Component
public class LogOutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;


    @Override
    public void onLogoutSuccess(HttpServletRequest httpRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String authToken = httpRequest.getHeader(this.tokenHeader);

        if (StringUtils.hasText(authToken) && authToken.startsWith("Bearer "))
            authToken = authToken.substring(7);

        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        User user = userDAO.findByUsername(username);
        if(username != null && user.getToken().equals(authToken)){
            try (PrintWriter writer = response.getWriter()) {
                userDAO.deleteToken(username);
                writer.write("{\"status\":\"success"
                        + "\", \"data\":\"Logged Out\"}");
                writer.flush();
                writer.close();
            }
        }else{
            try (PrintWriter writer = response.getWriter()) {
                writer.write("{\"status\":\"error"
                        + "\", \"data\":\"Your not logged in yet !}");
                writer.flush();
                writer.close();
            }
        }
    }
}
