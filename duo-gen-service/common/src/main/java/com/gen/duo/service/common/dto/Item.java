package com.gen.duo.service.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by israjhaliri on 01/01/2018.
 */
@Getter
@Setter
@ToString
public class Item {

    private Integer totalCount;
    private Integer id;
    private String title;
    private String description;
    private BigDecimal price;
    private Date createDate;
    private Integer viewed;
    private String image;
    private Integer booked;
    private String category;
}
