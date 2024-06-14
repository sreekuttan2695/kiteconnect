package stock.eclerne.kiteconnect2.controller;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.eclerne.kiteconnect2.model.ApiKey;
import stock.eclerne.kiteconnect2.service.ApiKeyService;
import stock.eclerne.kiteconnect2.service.KiteService;

@RestController
@RequestMapping("/api")
public class KiteController {

    @Autowired
    private KiteService kiteService;
    @Autowired
    private ApiKeyService apiKeyService;

// You can use this to get login url alone
    @GetMapping("/login-url")
    public String getLoginUrl() throws KiteException {
        ApiKey apiKey = apiKeyService.getApiKey();
        return kiteService.getLoginUrl(apiKey.getApiKey());
    }
}
