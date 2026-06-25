package com.viettel.docsearch.web.mapper;

import com.viettel.docsearch.domain.User;
import com.viettel.docsearch.web.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User u) {
        return new UserDto(u.getId(), u.getFullName(), u.getDob(), u.getCitizenId(), u.getAddress(), u.getAccountType());
    }
}
