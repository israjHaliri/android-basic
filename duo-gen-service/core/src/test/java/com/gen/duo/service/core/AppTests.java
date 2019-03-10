package com.gen.duo.service.core;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

    @Autowired
    private DataSource dataSource;

    @Test
    public void contextLoads() {
        App.getLogger(this).info("info database pool : {} ",dataSource.getPool());
        App.getLogger(this).debug("debug database pool : {} ",dataSource.getPool());
    }
}
