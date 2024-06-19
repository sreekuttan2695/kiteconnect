package stock.eclerne.kiteconnect2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.eclerne.kiteconnect2.service.KiteWebSocketService;

@RestController
@RequestMapping("/api")
public class WebSocketController {

    @Autowired
    private KiteWebSocketService kiteWebSocketService;

    @GetMapping("/start-ticker")
    public String startTicker() {
        kiteWebSocketService.startTicker();
        return "Ticker started";
    }
}
