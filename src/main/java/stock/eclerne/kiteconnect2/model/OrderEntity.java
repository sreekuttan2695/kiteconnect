package stock.eclerne.kiteconnect2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "order_id", nullable = false, unique = true)
    private String orderId;

    @Column(name = "trading_symbol", nullable = false)
    private String tradingSymbol;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    // Getters and setters
}
