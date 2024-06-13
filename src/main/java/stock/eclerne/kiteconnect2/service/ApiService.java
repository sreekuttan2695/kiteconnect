package stock.eclerne.kiteconnect2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.ApiKey;

@Service
public class ApiService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ApiKey getApiDetails() {
        String sql = "SELECT api_key, api_secret, user_id, password, totp_key FROM api_keys LIMIT 1";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ApiKey.class));
    }
}
