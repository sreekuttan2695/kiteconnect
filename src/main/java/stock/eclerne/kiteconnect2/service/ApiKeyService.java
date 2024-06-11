package stock.eclerne.kiteconnect2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.ApiKey;
import stock.eclerne.kiteconnect2.repository.ApiKeyRepository;

@Service
public class ApiKeyService {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    public ApiKey getApiKey() {
        return apiKeyRepository.getFirstApiKey();
    }
}
