package stock.eclerne.kiteconnect2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.eclerne.kiteconnect2.model.ApiKey;
import stock.eclerne.kiteconnect2.service.ApiKeyService;

@RestController
public class ApiKeyController {

    @Autowired
    private ApiKeyService apiKeyService;

    @GetMapping("/api-key")
    public ApiKey getApiKey() {
        return apiKeyService.getApiKey();
    }
}
