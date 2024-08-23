package stock.eclerne.kiteconnect2.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.ApiKey;
import stock.eclerne.kiteconnect2.model.OrderHistory;
import stock.eclerne.kiteconnect2.repository.OrderHistoryRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderHistoryService {

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    public void fetchAndSaveOrderHistory() throws KiteException, IOException {
        ApiKey apiKey = apiKeyService.getApiKey();
        KiteConnect kiteConnect = new KiteConnect(apiKey.getApiKey());
        kiteConnect.setAccessToken(apiKey.getAccessToken());

        // Define the start and end of the current day
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endOfDay = calendar.getTime();

        // Delete existing records for today
        orderHistoryRepository.deleteByOrderTimestampBetween(startOfDay, endOfDay);

        // Fetch orders from the Kite API
        List<Order> orders = kiteConnect.getOrders();

        // Save orders to the database
        for (Order order : orders) {
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setOrderId(order.orderId);
            orderHistory.setTradingSymbol(order.tradingSymbol);
            orderHistory.setQuantity(Integer.parseInt(order.quantity));
            orderHistory.setTransactionType(order.transactionType);
            orderHistory.setStatus(order.status);
            orderHistory.setOrderTimestamp(new Timestamp(order.orderTimestamp.getTime()));
            orderHistory.setFilledQuantity(Integer.parseInt(order.filledQuantity));
            orderHistory.setPendingQuantity(Integer.parseInt(order.pendingQuantity));
            orderHistory.setPrice(order.price);
            orderHistory.setTriggerPrice(order.triggerPrice);
            orderHistoryRepository.save(orderHistory);
        }
    }
}
