package stock.eclerne.kiteconnect2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "\"HistoricalDataStg\"") // Explicitly specify the table name
public class HistoricalDataStg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "instrument_token")
    private String instrumentToken;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "open")
    private BigDecimal open;

    @Column(name = "high")
    private BigDecimal high;

    @Column(name = "low")
    private BigDecimal low;

    @Column(name = "close")
    private BigDecimal close;

    @Column(name = "volume")
    private long volume;

    // Getters and Setters
}
