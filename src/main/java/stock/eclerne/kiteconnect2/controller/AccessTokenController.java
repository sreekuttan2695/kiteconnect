package stock.eclerne.kiteconnect2.controller;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.eclerne.kiteconnect2.service.AccessTokenService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AccessTokenController {

    @Autowired
    private AccessTokenService accessTokenService;

    @GetMapping("/generate-token")
    public String generateAndSaveAccessToken() {
        try {
            accessTokenService.generateAndSaveAccessToken();
            return "Access token generated and saved successfully.";
        } catch (KiteException | IOException e) {
            e.printStackTrace();
            return "Failed to generate access token: " + e.getMessage();
        }
    }
}
