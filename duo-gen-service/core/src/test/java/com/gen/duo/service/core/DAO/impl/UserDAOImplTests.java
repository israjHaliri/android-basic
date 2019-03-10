package com.gen.duo.service.core.DAO.impl;

import com.gen.duo.service.core.App;
import com.gen.duo.service.common.dto.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("development")
public class UserDAOImplTests {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDAOImpl userDAOImpl;

    @Test
    public void contextLoads() {
        App.getLogger(this).debug("Datasource pool : {} ", dataSource);
    }

    @Test
    public void findByUsername() {
        User user = userDAOImpl.findByUsername("israj.haliri@gmail.com");

        App.getLogger(this).debug("User : {}", user.toString());

        Assert.assertNotEquals(null, user.getUsername());
    }

    @Test
    public void saveToken() {
        try {
            userDAOImpl.saveToken("12345678", "israj.haliri@gmail.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteToken() {
        try {
            userDAOImpl.deleteToken("israj.haliri@gmail.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
