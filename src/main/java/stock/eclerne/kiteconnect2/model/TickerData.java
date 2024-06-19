package stock.eclerne.kiteconnect2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ticker_data")
public class TickerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "instrument_token", nullable = false)
    private String instrumentToken;

    @Column(name = "last_trade_price")
    private double lastTradePrice;

    @Column(name = "open")
    private double open;

    @Column(name = "high")
    private double high;

    @Column(name = "low")
    private double low;

    @Column(name = "close")
    private double close;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

//    public TickerData() {
//        this.timestamp = LocalDateTime.now();
//    }

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}
