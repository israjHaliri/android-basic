package com.gen.duo.service.api.filter;

import com.gen.duo.service.api.App;
import com.gen.duo.service.core.DAO.UserDAO;
import com.gen.duo.service.common.dto.User;
import com.gen.duo.service.api.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by israjhaliri on 8/30/17.
 */
public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserDAO userDAO;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        App.getLogger(this).debug("Request starts with : {}",httpRequest.getRequestURI().toString());

        if (httpRequest.getRequestURI().startsWith("/duo-gen/secret")) {
            String authToken = httpRequest.getHeader(this.tokenHeader);

            if (StringUtils.hasText(authToken) && authToken.startsWith("Bearer ")){
                authToken = authToken.substring(7);
            }

            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            try {
                User user = userDAO.findByUsername(username);
                if (user.getUsername() != null  && user.getToken().equals(authToken) && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                    if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        //final String token = jwtTokenUtil.generateToken(username);

                        //userDAO.saveToken(token,username);
                    }
                }
            }catch (Exception e){
                App.getLogger(this).error("Error user detail from token : {}", e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
}
