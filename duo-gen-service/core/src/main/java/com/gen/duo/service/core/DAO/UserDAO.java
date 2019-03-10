package com.gen.duo.service.core.DAO;

import com.gen.duo.service.common.dto.User;
import org.springframework.stereotype.Service;

@Service
public interface UserDAO {

    User findByUsername(String username);

    void saveToken(String token, String username);

    void deleteToken(String username);
}
