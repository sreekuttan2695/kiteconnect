package stock.eclerne.kiteconnect2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.eclerne.kiteconnect2.model.TickerData;

import java.util.Optional;

@Repository
public interface TickerDataRepository extends JpaRepository<TickerData, Integer> {
    Optional<TickerData> findTopByOrderByTimestampDesc();
}
