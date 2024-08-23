package stock.eclerne.kiteconnect2.controller;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stock.eclerne.kiteconnect2.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public String placeOrder(@RequestParam String tradingSymbol, @RequestParam String transactionType) {
        try {
            return orderService.placeOrder(tradingSymbol, transactionType);
        } catch (Exception e) {
            return "Order placement failed: " + e.getMessage();
        } catch (KiteException e) {
            throw new RuntimeException(e);
        }
    }
}
