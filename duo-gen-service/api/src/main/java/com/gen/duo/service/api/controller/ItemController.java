package com.gen.duo.service.api.controller;

import com.gen.duo.service.common.dto.Item;
import com.gen.duo.service.core.DAO.ItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by israjhaliri on 10/16/17.
 */
@RestController
public class ItemController {

    @Autowired
    private ItemDAO itemDAO;

    @RequestMapping(value = "/secret/get/item", method = RequestMethod.GET)
    public Object getTestimonial(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "offset", defaultValue = "") Integer offset,
            @RequestParam(value = "limit", defaultValue = "") Integer limit,
            @RequestParam(value = "category", required = false) String category
    ) {
        return itemDAO.getData(search, limit, offset, category);
    }

    @RequestMapping(value = "/secret/get/item/hot", method = RequestMethod.GET)
    public Object getDataHotList() {
        return itemDAO.getDataHotList();
    }

    @RequestMapping(value = "/secret/get/item/recomend", method = RequestMethod.GET)
    public Object getDataRecomendList() {
        return itemDAO.getDataRecomendList();
    }

    @RequestMapping(value = "/secret/save/item", method = RequestMethod.POST)
    public Object saveData(@RequestBody Item item) {
        Map<String, Object> status = new HashMap<>();
        try {
            itemDAO.saveData(item);

            status.put("status", "success");
            status.put("data", item);
            return status;
        } catch (Exception e) {
            e.printStackTrace();

            status.put("status", "error");
            status.put("data", e.getMessage());
            return status;
        }
    }

    @RequestMapping(value = "/secret/update/item/viewed", method = RequestMethod.PUT)
    public Object updateViewed(@RequestParam(value = "id", required = true) Integer id) {
        Map<String, Object> status = new HashMap<>();
        try {
            itemDAO.updateViewed(id);

            status.put("status", "success");
            status.put("data", null);
            return status;
        } catch (Exception e) {
            e.printStackTrace();

            status.put("status", "error");
            status.put("data", e.getMessage());
            return status;
        }
    }

    @RequestMapping(value = "/secret/update/item/booked", method = RequestMethod.PUT)
    public Object updateBooked(@RequestParam(value = "id", required = true) Integer id) {
        Map<String, Object> status = new HashMap<>();
        try {
            itemDAO.updateBooked(id);

            status.put("status", "success");
            status.put("data", null);
            return status;
        } catch (Exception e) {
            e.printStackTrace();

            status.put("status", "error");
            status.put("data", e.getMessage());
            return status;
        }
    }
}
