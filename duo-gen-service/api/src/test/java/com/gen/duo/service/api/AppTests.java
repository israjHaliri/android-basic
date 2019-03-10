package com.gen.duo.service.api;

import com.gen.duo.service.api.utils.JwtTokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

    @Autowired
    private JwtTokenUtil jwtTokenUtils;

    @Test
    public void contextLoads() {
        String hashed = BCrypt.hashpw("12345678", BCrypt.gensalt(12));

        App.getLogger(this).debug("Bcrypt has gensalt encoded : {}", hashed);
    }

    @Test
    public void getUsernameFromToken() {
        App.getLogger(this).debug("Username from token : {} ", jwtTokenUtils.getUsernameFromToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpc3Jhai5oYWxpcmlAZ21haWwuY29tIiwiYXVkIjoiZHVvZ2VuY2xpZW50IiwiZXhwIjoxNTE5NDU1NjExfQ.oAie5-_HMNUtIM5PaqhpfRN6EkN-sm3dOONXb4Zd3X4"));
    }

    @Test
    public void hasAccess() {
        App.getLogger(this).debug("Replace /** from uri : {} ", "duo-gen/secret/**".replace("/**",""));
    }
}
