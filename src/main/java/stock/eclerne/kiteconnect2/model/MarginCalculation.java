package stock.eclerne.kiteconnect2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "MarginCalculation")
public class MarginCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "instrument_token", nullable = false)
    private String instrumentToken;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "required_margin", nullable = false)
    private BigDecimal requiredMargin;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "flag", nullable = false)
    private int flag;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstrumentToken() {
        return instrumentToken;
    }

    public void setInstrumentToken(String instrumentToken) {
        this.instrumentToken = instrumentToken;
    }

    public BigDecimal getRequiredMargin() {
        return requiredMargin;
    }

    public void setRequiredMargin(BigDecimal requiredMargin) {
        this.requiredMargin = requiredMargin;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserId(String userId) {
        this.userId=userId;
    }

    public void setMarginRequired(BigDecimal requiredMargin) {
        this.requiredMargin = requiredMargin;
    }

    public void setMarginFlag(int i) {
        this.flag = i;
    }
}
