package com.gen.duo.service.core.DAO.impl;

import com.gen.duo.service.common.dto.Item;
import com.gen.duo.service.common.dto.User;
import com.gen.duo.service.core.App;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("development")
public class ItemDAOImplTests {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private ItemDAOImpl itemDAOImpl;

    @Test
    public void contextLoads() {
        App.getLogger(this).info("Datasource pool : {} ", dataSource);
    }

    @Test
    public void getData() {
        List<Item> itemList = itemDAOImpl.getData("merchandise",10,0, null);

        App.getLogger(this).info("Item : {}", itemList.toString());

        Assert.assertNotEquals(0, itemList.size());
    }

    @Test
    public void getDataHotList() {
        List<Item> itemList = itemDAOImpl.getDataHotList();

        App.getLogger(this).info("Item : {}", itemList.toString());

        Assert.assertNotEquals(0, itemList.size());
    }

    @Test
    public void getDataRecomendList() {
        List<Item> itemList = itemDAOImpl.getDataRecomendList();

        App.getLogger(this).info("Item : {}", itemList.toString());

        Assert.assertNotEquals(0, itemList.size());
    }

    @Test
    public void saveData() {
        Item item = new Item();
        item.setTitle("merchandise");
        item.setDescription("test data from classs test");
        item.setPrice(new BigDecimal(10000));
        item.setViewed(0);
        item.setBooked(0);
        item.setImage("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");

        itemDAOImpl.saveData(item);
    }

    @Test
    public void updateViewed() {
        itemDAOImpl.updateViewed(1);
    }

    @Test
    public void updateBooked() {
        itemDAOImpl.updateBooked(1);
    }
}
