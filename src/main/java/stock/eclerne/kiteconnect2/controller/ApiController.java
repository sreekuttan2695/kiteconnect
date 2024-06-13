package stock.eclerne.kiteconnect2.controller;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stock.eclerne.kiteconnect2.service.AccessTokenService;
import stock.eclerne.kiteconnect2.service.SeleniumService;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private SeleniumService seleniumService;

    @Autowired
    private AccessTokenService accessTokenService;

    @GetMapping("/generate-access-token")
    public ResponseEntity<String> generateAccessToken() {
        try {
            String requestToken = seleniumService.automateLoginAndGetTempToken();
            accessTokenService.generateAndSaveAccessToken(requestToken);
            return ResponseEntity.ok("Access token generated and saved successfully.");
        } catch (Exception | KiteException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

