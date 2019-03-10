package com.gen.duo.service.core.DAO;

import com.gen.duo.service.common.dto.Item;
import com.gen.duo.service.common.dto.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemDAO {

    List<Item> getData(String search, Integer limit, Integer offset, String cateogory);

    List<Item> getDataHotList();

    List<Item> getDataRecomendList();

    void saveData(Item item);

    void updateViewed(Integer id);

    void updateBooked(Integer id);

    void deleteData(Integer id);
}
