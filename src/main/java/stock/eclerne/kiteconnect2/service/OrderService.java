package stock.eclerne.kiteconnect2.service;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.OrderParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.ApiKey;
import stock.eclerne.kiteconnect2.model.OrderEntity;
import stock.eclerne.kiteconnect2.repository.OrderRepository;

import java.io.IOException;
import java.sql.Timestamp;

@Service
public class OrderService {

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private OrderRepository orderRepository;

    public String placeOrder(String tradingSymbol, String transactionType) throws KiteException, IOException {
        ApiKey apiKey = apiKeyService.getApiKey();
        KiteConnect kiteConnect = new KiteConnect(apiKey.getApiKey());
        kiteConnect.setAccessToken(apiKey.getAccessToken());

        // Create the order parameters
        OrderParams orderParams = new OrderParams();
        orderParams.tradingsymbol = tradingSymbol;
        orderParams.exchange = "NSE";
        orderParams.orderType = "MARKET";
        orderParams.quantity = 1;
        orderParams.transactionType = transactionType;
        // Add other default parameters as necessary

        // Place the order
        Order order = kiteConnect.placeOrder(orderParams, "regular");
        String orderId = order.orderId;

        // Save the order details to the database
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(apiKey.getUserId());
        orderEntity.setOrderId(orderId);
        orderEntity.setTradingSymbol(tradingSymbol);
        orderEntity.setQuantity(orderParams.quantity);
        orderEntity.setTransactionType(transactionType);
        orderEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));

        orderRepository.save(orderEntity);

        return orderId;
    }
}
