package stock.eclerne.kiteconnect2.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.ApiKey;

@Service
public class KiteService {

    private KiteConnect kiteConnect;

    @Autowired
    private ApiService apiService;

    public String getLoginUrl() throws KiteException {
        ApiKey apiDetails = apiService.getApiDetails();
        kiteConnect = new KiteConnect(apiDetails.getApiKey());
        kiteConnect.setUserId(apiDetails.getUserId());
        return kiteConnect.getLoginURL();
    }
}
