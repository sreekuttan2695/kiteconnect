package stock.eclerne.kiteconnect2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.eclerne.kiteconnect2.model.Strategy;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepository extends JpaRepository<Strategy, Integer> {
    Strategy findByStrategyCode(String strategyCode);
}
