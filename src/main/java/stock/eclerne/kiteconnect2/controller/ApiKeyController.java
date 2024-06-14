package stock.eclerne.kiteconnect2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.eclerne.kiteconnect2.model.ApiKey;
import stock.eclerne.kiteconnect2.service.ApiKeyService;

//This class is created for testing weather the details are getting fetched from MySQL DB.

@RestController
public class ApiKeyController {

    @Autowired
    private ApiKeyService apiKeyService;

    @GetMapping("/api-key")
    public ApiKey getApiKey() {
        return apiKeyService.getApiKey();
    }
}
