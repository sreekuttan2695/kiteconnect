package stock.eclerne.kiteconnect2.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "straddle")
public class Straddle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "strategy_code", nullable = false)
    private String strategyCode;

    @Column(name = "triggered_ltp", nullable = false)
    private BigDecimal triggeredLtp;

    @Column(name = "pe", nullable = false)
    private BigDecimal pe;

    @Column(name = "ce", nullable = false)
    private BigDecimal ce;

    @Column(name = "pe_thresh", nullable = false)
    private BigDecimal peThresh;

    @Column(name = "ce_thresh", nullable = false)
    private BigDecimal ceThresh;

    @Column(name = "pe_max", nullable = false)
    private BigDecimal peMax;

    @Column(name = "ce_max", nullable = false)
    private BigDecimal ceMax;

    @Column(name = "entry_time", nullable = false, updatable = false)
    private Timestamp entryTime;

    @Column(name = "exit_time")
    private Timestamp exitTime;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "exit_flag", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean exitFlag;

    @Column(name = "adj_flag", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean adjFlag;

    @Column(name = "update_count")
    private Integer updateCount;

    @Column(name = "last_update_on", nullable = false)
    private Timestamp lastUpdateOn;
}