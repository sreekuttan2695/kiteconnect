package stock.eclerne.kiteconnect2.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.ApiKey;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class AccessTokenService {

    @Autowired
    private ApiService apiService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void generateAndSaveAccessToken(String requestToken) throws KiteException {
        ApiKey apiDetails = apiService.getApiDetails();
        KiteConnect kiteConnect = new KiteConnect(apiDetails.getApiKey());

        User user = kiteConnect.generateSession(requestToken, apiDetails.getApiSecret());
        String accessToken = user.accessToken;

        // Save access token in database
        String sql =  "UPDATE api_keys SET access_token = ? WHERE api_key = ?";
        jdbcTemplate.update(sql, accessToken, apiDetails.getApiKey());
    }
}
