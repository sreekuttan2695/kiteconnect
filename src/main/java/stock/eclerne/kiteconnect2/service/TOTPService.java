package stock.eclerne.kiteconnect2.service;

import de.taimos.totp.TOTP;
import org.springframework.stereotype.Service;

@Service
public class TOTPService {

    public String generateTOTP(String totpKey) {
        return TOTP.getOTP(totpKey);
    }
}
