package stock.eclerne.kiteconnect2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.StraddleData;
import stock.eclerne.kiteconnect2.repository.TickerDataRepository;
import stock.eclerne.kiteconnect2.model.TickerData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class StrategyExecutionService {

    @Autowired
    private StrategyCacheService strategyCacheService;

    @Autowired
    private TickerDataRepository tickerDataRepository;

    private static final int X = 10; // Define X value for thresholds
    private static final int Y = 20; // Define Y value for max range

    /**
     * Case 1: Execute business logic if LTP crosses threshold.
     */
    public void processLTP(String strategyCode, BigDecimal lastTradePrice) {
        StraddleData strategy = strategyCacheService.getStrategy(strategyCode);

        if (strategy == null || !strategy.isActive()) {
            return; // No active strategy, exit.
        }

        // Compare LTP with thresholds
        if (lastTradePrice.compareTo(strategy.getCeThresh()) > 0 || lastTradePrice.compareTo(strategy.getPeThresh()) < 0) {
            //executes business logic
            executeTrade(strategy.getCe(), strategy.getPe());
        }
    }

    /**
     * Case 2: Start a new strategy.
     */
    public void startStrategy(String strategyCode) {
        // Hardcoded strategy codes for validation
        List<String> allowedStrategies = List.of("STRATEGY_A", "STRATEGY_B", "STRATEGY_C", "STRATEGY_D", "STRATEGY_E");

        if (!allowedStrategies.contains(strategyCode)) {
            throw new IllegalArgumentException("Invalid strategy code");
        }

        // Check if strategy is already active
        if (strategyCacheService.isStrategyActive(strategyCode)) {
            return; // Already active, exit.
        }

        // Fetch latest LTP from ticker_data table
        Optional<TickerData> latestTicker = tickerDataRepository.findTopByOrderByTimestampDesc();

        if (latestTicker.isEmpty()) {
            throw new IllegalStateException("No LTP data available.");
        }

        BigDecimal ltp = BigDecimal.valueOf(latestTicker.get().getLastTradePrice());

        // Calculate PE, CE, peThresh, ceThresh, peMax, ceMax
        BigDecimal pe = BigDecimal.valueOf(Math.floor(ltp.doubleValue() / 50) * 50);
        BigDecimal ce = BigDecimal.valueOf(Math.ceil(ltp.doubleValue() / 50) * 50);
        BigDecimal peThresh = pe.subtract(BigDecimal.valueOf(X));
        BigDecimal ceThresh = ce.add(BigDecimal.valueOf(X));
        BigDecimal peMax = pe.subtract(BigDecimal.valueOf(Y));
        BigDecimal ceMax = ce.add(BigDecimal.valueOf(Y));

        // Execute business logic
        executeTrade(pe, ce);

        // Update StrategyCacheService with new strategy
        StraddleData newStrategy = new StraddleData();
        newStrategy.setTriggeredLtp(ltp);
        newStrategy.setPe(pe);
        newStrategy.setCe(ce);
        newStrategy.setPeThresh(peThresh);
        newStrategy.setCeThresh(ceThresh);
        newStrategy.setPeMax(peMax);
        newStrategy.setCeMax(ceMax);
        newStrategy.setActive(true);
        newStrategy.setExitFlag(false);
        newStrategy.setAdjFlag(false);

        strategyCacheService.updateStrategy(strategyCode, newStrategy);
    }

    /**
     * Placeholder method for executing trade logic.
     */
    private void executeTrade(BigDecimal pe, BigDecimal ce) {
        System.out.println("Executing trade with PE: " + pe + " and CE: " + ce);
        // TODO: Implement trade execution logic
    }
}
