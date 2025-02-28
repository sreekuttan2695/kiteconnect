package stock.eclerne.kiteconnect2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stock.eclerne.kiteconnect2.model.Straddle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StraddleRepository extends JpaRepository<Straddle, Integer> {
    List<Straddle> findByStrategyCode(String strategyCode);
}
