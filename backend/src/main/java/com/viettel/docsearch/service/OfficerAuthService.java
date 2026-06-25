package com.viettel.docsearch.service;

import com.viettel.docsearch.config.AppProperties;
import com.viettel.docsearch.domain.AccountType;
import com.viettel.docsearch.web.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Mock "cán bộ" (official) login that gates the LLTP verification page. The account is defined
 * entirely in configuration ({@link AppProperties.Officer}) — there is NO database row. On valid
 * preset credentials it returns a synthetic {@link UserDto}; the controller issues a JWT for the
 * configured id. The verify endpoints are public, so no later DB resolution of the officer is needed.
 */
@Service
public class OfficerAuthService {

    private final AppProperties props;

    public OfficerAuthService(AppProperties props) {
        this.props = props;
    }

    public UserDto authenticate(String username, String password) {
        AppProperties.Officer o = props.getOfficer();
        if (username == null || password == null
                || !o.getUsername().equals(username.trim())
                || !o.getPassword().equals(password)) {
            throw new IllegalArgumentException("Tên đăng nhập hoặc mật khẩu cán bộ không đúng");
        }
        return new UserDto(UUID.fromString(o.getId()), o.getFullName(), o.getDob(),
            o.getCitizenId(), o.getAddress(), AccountType.OFFICER);
    }
}
