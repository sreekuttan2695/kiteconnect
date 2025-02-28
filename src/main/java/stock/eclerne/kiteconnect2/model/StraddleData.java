package stock.eclerne.kiteconnect2.model;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class StraddleData {

    private BigDecimal triggeredLtp;
    private BigDecimal pe;
    private BigDecimal ce;
    private BigDecimal peThresh;
    private BigDecimal ceThresh;
    private BigDecimal peMax;
    private BigDecimal ceMax;
    private Timestamp entryTime;
    private Timestamp exitTime;
    private boolean isActive;
    private boolean exitFlag;
    private boolean adjFlag;
    private Integer updateCount;
    private Timestamp lastUpdateOn;

    public StraddleData(Straddle straddle) {
        this.triggeredLtp = straddle.getTriggeredLtp();
        this.pe = straddle.getPe();
        this.ce = straddle.getCe();
        this.peThresh = straddle.getPeThresh();
        this.ceThresh = straddle.getCeThresh();
        this.peMax = straddle.getPeMax();
        this.ceMax = straddle.getCeMax();
        this.entryTime = straddle.getEntryTime();
        this.exitTime = straddle.getExitTime();
        this.isActive = straddle.isActive();
        this.exitFlag = straddle.isExitFlag();
        this.adjFlag = straddle.isAdjFlag();
        this.updateCount = straddle.getUpdateCount();
        this.lastUpdateOn = straddle.getLastUpdateOn();
    }
}
