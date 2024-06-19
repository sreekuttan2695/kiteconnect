package stock.eclerne.kiteconnect2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "instruments")
public class InstrumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "instrument_token")
    private Long instrumentToken;

    @Column(name = "exchange_token")
    private Long exchangeToken;

    @Column(name = "tradingsymbol")
    private String tradingSymbol;

    @Column(name = "name")
    private String name;

    @Column(name = "last_price")
    private Double lastPrice;

    @Column(name = "expiry")
    private String expiry;

    @Column(name = "strike")
    private Double strike;

    @Column(name = "tick_size")
    private Double tickSize;

    @Column(name = "lot_size")
    private Integer lotSize;

    @Column(name = "instrument_type")
    private String instrumentType;

    @Column(name = "segment")
    private String segment;

    @Column(name = "exchange")
    private String exchange;

    // Getters and setters
}
