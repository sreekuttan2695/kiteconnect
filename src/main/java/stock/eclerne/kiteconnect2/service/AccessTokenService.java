package stock.eclerne.kiteconnect2.service;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.ApiKey;

import java.io.IOException;

@Service
public class AccessTokenService {

    @Autowired
    private KiteService kiteService;

    @Autowired
    private TOTPService totpService;

    @Autowired
    private SeleniumService seleniumService;

    @Autowired
    private ApiKeyService apiKeyService;

    public void updateAccessToken(String userId, String accessToken) {
        ApiKey apiKey = apiKeyService.getApiKeyDetailsByUserId(userId);
        if (apiKey != null) {
            apiKey.setAccessToken(accessToken);
            apiKeyService.saveApiKey(apiKey);
        }
    }
    //As it is a single user app, we are not passing user id as an arg to generate a session

    public void generateAndSaveAccessToken() throws KiteException, IOException {
        ApiKey apiKey = apiKeyService.getApiKey(); //Gets data from DB
        if (apiKey.getApiKey() != null && apiKey.getApiSecret() != null) {
            String loginUrl = kiteService.getLoginUrl(apiKey.getApiKey());
            String otp = totpService.generateTOTP(apiKey.getTotpKey());
            String requestToken = seleniumService.getRequestToken(loginUrl, apiKey.getUserId(), apiKey.getPassword(), otp);
            User user = kiteService.generateSession(apiKey.getApiKey(), apiKey.getApiSecret(), requestToken);
            if (user != null && user.accessToken != null) {
                updateAccessToken(apiKey.getUserId(), user.accessToken);
            }
        }
    }
}
