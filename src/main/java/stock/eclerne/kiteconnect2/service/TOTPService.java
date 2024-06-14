package stock.eclerne.kiteconnect2.service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.stereotype.Service;

@Service
public class TOTPService {

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    public String generateTOTP(String secretKey) {
        return Integer.toString(gAuth.getTotpPassword(secretKey));
    }
}
