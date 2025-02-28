package stock.eclerne.kiteconnect2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.eclerne.kiteconnect2.model.Straddle;
import stock.eclerne.kiteconnect2.model.StraddleData;
import stock.eclerne.kiteconnect2.repository.StraddleRepository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StrategyCacheService {

    @Autowired
    private StraddleRepository straddleRepository;

    private final ConcurrentHashMap<String, StraddleData> strategyCache = new ConcurrentHashMap<>();

    // Load cache at startup
    public void loadCache() {
        List<Straddle> activeStraddles = straddleRepository.findAll();
        for (Straddle straddle : activeStraddles) {
            strategyCache.put(straddle.getStrategyCode(), new StraddleData(straddle));
        }
    }

    // Get strategy data
    public StraddleData getStrategy(String strategyCode) {
        return strategyCache.get(strategyCode);
    }

    // Update cache when a strategy is modified
    public void updateStrategy(String strategyCode) {
        List<Straddle> straddles = straddleRepository.findByStrategyCode(strategyCode);
        if (!straddles.isEmpty()) {
            strategyCache.put(strategyCode, new StraddleData(straddles.get(0)));
        }
    }

    // Remove strategy from cache when it's deleted
    public void removeStrategy(String strategyCode) {
        strategyCache.remove(strategyCode);
    }

    // Check if a strategy exists in cache
    public boolean isStrategyActive(String strategyCode) {
        return strategyCache.containsKey(strategyCode);
    }
}
