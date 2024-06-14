package stock.eclerne.kiteconnect2.service;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;
import com.zerodhatech.kiteconnect.KiteConnect;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KiteService {

    public String getLoginUrl(String apiKey) {
        KiteConnect kiteConnect = new KiteConnect(apiKey);
        return kiteConnect.getLoginURL();
    }


    public User generateSession(String apiKey, String apiSecret, String requestToken) throws KiteException, IOException {
        KiteConnect kiteConnect = new KiteConnect(apiKey);
        return kiteConnect.generateSession(requestToken, apiSecret);
    }
}
