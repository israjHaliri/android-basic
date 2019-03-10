package com.gen.duo.service.common.dto;

import com.gen.duo.service.common.dto.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by israjhaliri on 01/01/2018.
 */
@Getter
@Setter
@ToString
public class User {

    private String username;
    private String password;
    private Boolean enable;
    private List<Role> roleList;
    private String token;
}
