package stock.eclerne.kiteconnect2.controller;

import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.eclerne.kiteconnect2.service.OrderHistoryService;

@RestController
@RequestMapping("/api/order-history")
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @GetMapping("/fetch")
    public String fetchOrderHistory() {
        try {
            orderHistoryService.fetchAndSaveOrderHistory();
            return "Order history fetched and saved successfully.";
        } catch (Exception e) {
            return "Failed to fetch order history: " + e.getMessage();
        } catch (KiteException e) {
            throw new RuntimeException(e);
        }
    }
}
