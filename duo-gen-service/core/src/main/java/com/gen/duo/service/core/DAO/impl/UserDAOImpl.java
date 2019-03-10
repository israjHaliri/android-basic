package com.gen.duo.service.core.DAO.impl;

import com.gen.duo.service.common.dto.Role;
import com.gen.duo.service.common.dto.User;
import com.gen.duo.service.core.App;
import com.gen.duo.service.core.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by israjhaliri on 01/01/2018.
 */
@org.springframework.stereotype.Service
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User findByUsername(String username) {
        User user = new User();
        List<Role> roleList = new ArrayList<>();

        String sqlUser = "SELECT * FROM users WHERE username = ?";

        try {
            user = (User) jdbcTemplate.queryForObject(sqlUser, new Object[]{username}, new BeanPropertyRowMapper(User.class));

            String sqlRole = "SELECT * FROM user_roles JOIN roles on user_roles.role_id = roles.id WHERE user_id = '" + user.getUsername() + "'";
            roleList = jdbcTemplate.query(sqlRole, new BeanPropertyRowMapper(Role.class));

            user.setRoleList(roleList);
        } catch (Exception e) {
            e.printStackTrace();
            App.getLogger(this).error(e.getMessage());
        }

        return user;
    }

    @Override
    public void saveToken(String token, String username) {
        String sql = "UPDATE users SET token =? WHERE username = ?";
        jdbcTemplate.update(sql, new Object[]{token, username});
    }

    @Override
    public void deleteToken(String username) {
        String sql = "UPDATE users SET token = '' WHERE username = ?";
        jdbcTemplate.update(sql, new Object[]{username});
    }
}
