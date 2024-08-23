package stock.eclerne.kiteconnect2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "order_history")
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "trading_symbol", nullable = false)
    private String tradingSymbol;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "order_timestamp", nullable = false)
    private Timestamp orderTimestamp;

    @Column(name = "filled_quantity")
    private int filledQuantity;

    @Column(name = "pending_quantity")
    private int pendingQuantity;

    @Column(name = "price")
    private String price;

    @Column(name = "trigger_price")
    private String triggerPrice;

    // Getters and setters
}
