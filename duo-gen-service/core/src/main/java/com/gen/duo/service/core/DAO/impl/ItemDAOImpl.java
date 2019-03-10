package com.gen.duo.service.core.DAO.impl;

import com.gen.duo.service.common.dto.Item;
import com.gen.duo.service.common.dto.Role;
import com.gen.duo.service.common.dto.User;
import com.gen.duo.service.core.App;
import com.gen.duo.service.core.DAO.ItemDAO;
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
public class ItemDAOImpl implements ItemDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Item> getData(String search, Integer limit, Integer offset, String category) {
        String sql = "SELECT t.*\n" +
                "FROM\n" +
                "   (SELECT ROW_NUMBER() OVER() AS rn,t.*\n" +
                "       FROM\n" +
                "           (SELECT t.*\n" +
                "               FROM\n" +
                "                   (SELECT COUNT(id) OVER() total_count,id,title,description,price,create_date,image,viewed,booked,category\n" +
                "                    FROM item \n" +
                "                    WHERE title LIKE  '%" + search + "%' \n";

        if (category != null) {
            sql += "                    AND category = '" + category + "'";
        }

        sql += "                    ORDER BY item.id ASC) " +
                "             t)\n" +
                "     t)\n" +
                "t\n" +
                "OFFSET " + offset + "::INTEGER LIMIT " + limit + "::INTEGER";

        List<Item> itemList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Item.class));

        return itemList;
    }

    @Override
    public List<Item> getDataHotList() {
        String sql = "SELECT * FROM item ORDER BY viewed DESC LIMIT 10";

        List<Item> itemList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Item.class));

        return itemList;
    }

    @Override
    public List<Item> getDataRecomendList() {
        String sql = "SELECT * FROM item ORDER BY booked DESC LIMIT 10";

        List<Item> itemList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Item.class));

        return itemList;
    }

    @Override
    public void saveData(Item item) {
        String sql = "INSERT INTO item (title, description, price, create_date, image, viewed, booked, category) VALUES (?, ?, ?, CURRENT_DATE, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, new Object[]{item.getTitle(), item.getDescription(), item.getPrice(), item.getImage(), item.getViewed(), item.getBooked(), item.getCategory()});
    }

    @Override
    public void updateViewed(Integer id) {
        String sql = "UPDATE item SET viewed = (viewed + 1), create_date = CURRENT_DATE WHERE id = ?";
        jdbcTemplate.update(sql, new Object[]{id});
    }

    @Override
    public void updateBooked(Integer id) {
        String sql = "UPDATE item SET booked = (booked + 1), create_date = CURRENT_DATE WHERE id = ?";
        jdbcTemplate.update(sql, new Object[]{id});
    }

    @Override
    public void deleteData(Integer id) {
        String sql = "DELETE FROM item WHERE id = ?";
        jdbcTemplate.update(sql, new Object[]{id});
    }
}
